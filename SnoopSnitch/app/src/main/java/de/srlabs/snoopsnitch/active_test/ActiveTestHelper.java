package de.srlabs.snoopsnitch.active_test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import de.srlabs.snoopsnitch.R;
import de.srlabs.snoopsnitch.qdmon.MsdService;
import de.srlabs.snoopsnitch.util.Constants;
import de.srlabs.snoopsnitch.util.MsdConfig;
import de.srlabs.snoopsnitch.util.MsdDialog;
import de.srlabs.snoopsnitch.util.MsdLog;

public class ActiveTestHelper{

	//private static String TAG = "msd-active-test-helper";
	private static final String TAG = "SNSN";
	private static final String mTAG = "ActiveTestHelper";

	private ActiveTestCallback callback;
	private Activity context;
	private Dialog confirmDialog = null;
	private IActiveTestService mIActiveTestService;
	private MyActiveTestCallback myActiveTestCallback = new MyActiveTestCallback();
	private MyServiceConnection serviceConnection = new MyServiceConnection();
	private boolean activeTestRunning;
	private boolean dummy;
	public boolean connected;

	class MyServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MsdLog.i(MsdService.TAG,"MsdServiceHelper.MyServiceConnection.onServiceConnected()");
			mIActiveTestService = IActiveTestService.Stub.asInterface(service);
			try {
				mIActiveTestService.registerCallback(myActiveTestCallback);
				boolean testRunning = mIActiveTestService.isTestRunning();
				MsdLog.i(TAG,"Initial recording = " + testRunning);
			} catch (RemoteException e) {
				handleFatalError("RemoteException while calling mIMsdService.registerCallback(msdCallback) or mIMsdService.isRecording() in MsdServiceHelper.MyServiceConnection.onServiceConnected()", e);
			}
			connected = true;
			callback.testStateChanged();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			MsdLog.i(TAG,"MsdServiceHelper.MyServiceConnection.onServiceDisconnected() called");
			context.unbindService(this);
			mIActiveTestService = null;
			connected = false;
			startService();
			// Service connection was lost, so let's call recordingStopped
			callback.testStateChanged();
		}
	}

	class  MyActiveTestCallback extends IActiveTestCallback.Stub{
		@Override
		public void testResultsChanged(Bundle b) throws RemoteException {
			try{
				ActiveTestResults results = (ActiveTestResults) b.getSerializable("results");
				// MsdLog.i(TAG,"testResultsChanged:" + results.formatTextTable());
				callback.handleTestResults(results);
			} catch(Exception e){
				MsdLog.e(TAG,"Exception in ActiveTestHelper.MyActiveTestCallback.testResultsChanged():\n", e);
			}
		}
		@Override
		public void testStateChanged() throws RemoteException {
			isActiveTestRunning();
			callback.testStateChanged();
		}
		@Override
		public void deviceIncompatibleDetected() throws RemoteException {
			callback.deviceIncompatibleDetected();
		}
	}

	public ActiveTestHelper(Activity activity, ActiveTestCallback callback){
		this.context = activity;
		this.callback = callback;
		startService();
	}

	private void startService() {
		context.startService(new Intent(context, ActiveTestService.class));
		context.bindService(new Intent(context, ActiveTestService.class), this.serviceConnection, Context.BIND_AUTO_CREATE);
	}

	public boolean startActiveTest(String ownNumber){
		try {
			return mIActiveTestService.startTest(ownNumber);
		} catch (Exception e) {
			handleFatalError("Exception while running mIActiveTestService.startTest(ownNumber)", e);
			return false;
		}
	}

	public void stopActiveTest(){
		try {
			mIActiveTestService.stopTest();
		} catch (Exception e) {
			handleFatalError("Exception while running mIActiveTestService.stopTest()", e);
		}
	}

	public boolean isConnected(){
		return connected;
	}

	/**
	 * This checks the current status of AirplaneMode
	 * Log:		Emi	2016-12-22
	 * Note:	We directly suppress warnings here as this code is now valid for all API's.
	 *
	 * [1] http://stackoverflow.com/questions/37135913/how-to-know-if-airplane-mode-is-turned-on-or-turned-off-in-broadcastreceiver
	 * [2] http://stackoverflow.com/questions/34904567/app-to-turn-off-airplane-mode-on-android
	 *
	 * ToDo: (1) [x] Check for Airplane Mode
	 * ToDo: (2) [ ] Take user to Settings and ask to disable
	 * ToDo: (3) [ ] Disable AirPlane Mode
	 * ToDO: (4) [ ] Send AirPlaneMode change intent to OS
     * ToDO: (5) [ ] Fix funny Logcat Error:
     *      "Setting airplane_mode_on has moved from android.provider.Settings.System to
     *      android.provider.Settings.Global, returning read-only value"
     *
     * /
    /*
             ----------------------------
             Name            AOS     API
             ----------------------------
             Nougat          7.1     25     N_MR1
             Nougat          7.0     24
             Marshmallow     6.0     23
             Lollipop        5.1     22     LOLLIPOP_MR1
             Lollipop        5.0     21
             KitKat          4.4.x   19
             Jelly Bean      4.3.x   18     JELLY_BEAN_MR2
             Jelly Bean      4.2.x   17     JELLY_BEAN_MR1
             Jelly Bean      4.1.x   16
             ----------------------------
     */

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static boolean isAirplaneModeOn(Context context) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			return Settings.System.getInt(context.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 0) != 0;
		} else {
			return Settings.Global.getInt(context.getContentResolver(),
					Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
		}
	}

	// Take user to Airplane Mode settings
	public static boolean toAirplaneModeSettings(Context context) {
		if (android.os.Build.VERSION.SDK_INT < 17) {
			try {
				Intent iAPM1 = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
				iAPM1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(iAPM1);
				return true;
			} catch (ActivityNotFoundException ae) {
				Log.e(TAG, mTAG + ":toAirplaneModeSettings(): Error opening the AirPlaneMode settings activity:\n" + ae);
			}
		} else { // API >16 (AOS 4.1?)
			try {
				Intent iAPM2 = new Intent("android.settings.WIRELESS_SETTINGS");
				iAPM2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(iAPM2);
				return true;
			} catch (ActivityNotFoundException ae) {
				Log.e(TAG, mTAG + ":toAirplaneModeSettings(): Error opening the AirPlaneMode settings activity:\n" + ae);
			}
		}
		return false;
	}
	//===================================================================================

	public boolean isActiveTestRunning(){
		MsdLog.i(TAG,"ActiveTestHelper.isActiveTestRunning() called");
		if(!connected)
			return false;
		activeTestRunning = false;
		try {
			activeTestRunning = mIActiveTestService.isTestRunning();
		} catch (RemoteException e) {
			handleFatalError("RemoteException while calling mIActiveTestService.isTestRunning() in MsdServiceHelper.startRecording()", e);
		}
		MsdLog.i(TAG,"mIActiveTestService.isTestRunning() returns " + activeTestRunning);
		return activeTestRunning;
	}

	private void handleFatalError(String msg, Exception e){
		// Unless this is used and referenced elsewhere, the TAGs should be local
		MsdLog.e(TAG, msg, e);
		//MsdLog.e(TAG, mTAG + msg, e);
		callback.internalError(msg);
	}

	public void clearCurrentFails() {
		try {
			mIActiveTestService.clearCurrentFails();
		} catch (Exception e) {
			handleFatalError("Exception in ActiveTestHelper.clearCurrentFails()",e);
		}
	}

	public void clearCurrentResults() {
		try {
			mIActiveTestService.clearCurrentResults();
		} catch (Exception e) {
			handleFatalError("Exception in ActiveTestHelper.clearCurrentResults()",e);
		}
	}
	
	public void clearResults() {
		try {
			mIActiveTestService.clearResults();
		} catch (Exception e) {
			handleFatalError("Exception in ActiveTestHelper.clearCurrentResults()",e);
		}
	}

	public void queryPhoneNumberAndStart(){
		queryPhoneNumberAndStart(null);
	}

	private void queryPhoneNumberAndStart(String msg){

		final String lastConfirmedOwnNumber = MsdConfig.getOwnNumber(context);
		final EditText editText = new EditText(context);
		editText.setHint("international notation using '+'");
		editText.setInputType(InputType.TYPE_CLASS_PHONE);

        String cc = MsdConfig.getCountryCode(context);
        if (cc != null) {
            Log.i(TAG, ": getCountryCode() received: " + cc);
            cc = "+" + cc;
        } else {
            cc = "+";
        }

		final String text = lastConfirmedOwnNumber.isEmpty() ? cc : lastConfirmedOwnNumber;
		editText.setText(text);
		editText.setSelection(text.length());

		final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Confirm your phone number");
		if(msg != null)
			dialog.setMessage(msg);
		dialog.setView(editText);
		dialog.setPositiveButton("Run", new DialogInterface.OnClickListener() {
			private boolean alreadyClicked = false;
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				if(alreadyClicked)
					return;
				alreadyClicked = true;
				String confirmedOwnNumber = editText.getText().toString().trim();

                // Check for international prefix: + || 00
				if(!( confirmedOwnNumber.startsWith("+") || confirmedOwnNumber.startsWith("00"))){
					queryPhoneNumberAndStart("Please enter an international number (with '+' or '00' in the beginning)");
					return;
				}

                // Check min number length:  +1 800 555-5555 = 11 digits, but we're nice...
                // Shortest numbers are fixed (non mobile) numbers in Solomon Islands: 
                // See: https://en.wikipedia.org/wiki/Telephone_numbers_in_the_Solomon_Islands
                if(confirmedOwnNumber.length() < 9) {
                    queryPhoneNumberAndStart("Your number is too short");
                    return;
                }

                // Check number for garbage
				String tmp = "";
				for(int i=0;i<confirmedOwnNumber.length();i++){
					char c = confirmedOwnNumber.charAt(i);
					if(i == 0 && c == '+')
						tmp += c;
					else if(c == ' ' || c == '/')
						continue;
					else if(Character.isDigit(c))
						tmp += c;
					else{
						queryPhoneNumberAndStart("Invalid character in phone number");
						return;
					}
				}

                // Check if number is empty
				confirmedOwnNumber = tmp;
				if(confirmedOwnNumber.isEmpty())
					return;

				MsdConfig.setOwnNumber(context, confirmedOwnNumber);
				// Not sure this is the best place to check for this.
				// Also a dialog would be useful...
				if(isAirplaneModeOn(context)) {
					Toast.makeText(context, "Please turn OFF AirplaneMode! ", Toast.LENGTH_LONG).show();
					// ToDo: Send user to AirplaneMode/Networks settings
					toAirplaneModeSettings(context);
					return;
				}
				startActiveTest(confirmedOwnNumber);
			}
		});
		dialog.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callback.testStateChanged();
			}
		});
		dialog.show();
	}

	public void showConfirmDialogAndStart(final boolean clearResults){
		if(confirmDialog != null && confirmDialog.isShowing())
			return;

		final boolean uploadDisabled = MsdConfig.getActiveTestDisableUpload(context);

		String positiveButtonText;
		String networkTestMessage;
		if(uploadDisabled) {
			positiveButtonText = context.getResources().getString(R.string.alert_button_ok);
			networkTestMessage =
				context.getResources().getString(R.string.alert_networktest_message);
		} else {
			positiveButtonText = context.getString(R.string.test_and_upload);
			networkTestMessage =
				context.getResources().getString(R.string.alert_networktest_message) + "\n" +
				context.getResources().getString(R.string.alert_networktest_privacy_disclaimer);
		}

		confirmDialog = MsdDialog.makeConfirmationDialog(
			context, networkTestMessage, new OnClickListener() {
				private boolean alreadyClicked = false;
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(alreadyClicked)
						return;
					alreadyClicked = true;
					if(clearResults)
						clearResults();
					try {
						mIActiveTestService.setUploadDisabled(uploadDisabled);
					} catch (Exception e) {
						handleFatalError("Exception in ActiveTestHelper.showConfirmDialogAndStart()",e);
					}
					queryPhoneNumberAndStart();
				}
			}, null,null, positiveButtonText, context.getString(R.string.alert_button_cancel), false
		);
		confirmDialog.show();
	}

	public void applySettings() {
		try {
			if(mIActiveTestService != null)
				mIActiveTestService.applySettings();
            else {
                Log.i(TAG, mTAG + "applySettings(): mIActiveTestService is null. Cannot apply settings.");
            }
		} catch (Exception e) {
            Log.e(TAG, mTAG + "applySettings(): Exception: ", e);
			handleFatalError("Exception in ActiveTestHelper.applySettings()",e);
		}
	}
}
