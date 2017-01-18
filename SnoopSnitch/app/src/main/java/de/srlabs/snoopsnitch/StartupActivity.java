package de.srlabs.snoopsnitch;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.srlabs.snoopsnitch.qdmon.MsdSQLiteOpenHelper;
import de.srlabs.snoopsnitch.util.DeviceCompatibilityChecker;
import de.srlabs.snoopsnitch.util.MsdConfig;
import de.srlabs.snoopsnitch.util.MsdDialog;
import de.srlabs.snoopsnitch.util.Utils;

//import permissions.dispatcher.NeedsPermission;
//import permissions.dispatcher.RuntimePermissions;

/**
 * This class is launched when starting the App. It will display a dialog if the
 * device is not compatible or if it is the first run of the App (so that the
 * user has to confirm to continue). If the device is compatible and the user
 * has already confirmed the first run dialog, it will directly switch over to
 * DashboardActivity.
 * 
 */
//@RuntimePermissions
public class StartupActivity extends Activity {

    private static final String TAG = "SNSN";
    private static final String mTAG = "StartupActivity";

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private MsdSQLiteOpenHelper helper;
	private boolean alreadyClicked = false;
	private ProgressDialog progressDialog;


    /**
     ======================================================================
     We are using 4 groups of Dangerous permissions:
     -----------------------------------------------------------------------
     Group		Permission (we need)
     -----------------------------------------------------------------------
     LOCATION	ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
     PHONE		READ_PHONE_STATE, CALL_PHONE, USE_SIP, PROCESS_OUTGOING_CALLS
     SMS		SEND_SMS, RECEIVE_SMS, READ_SMS, RECEIVE_WAP_PUSH, RECEIVE_MMS
     STORAGE	READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
     -----------------------------------------------------------------------

     Please see detailed notes in:  AndroidManifest.xml
     ======================================================================
     */

    // LOCATION
    private static final int SNSN_REQUEST_ACCESS_FINE_LOCATION = 11;  // *
    private static final int SNSN_REQUEST_ACCESS_COARSE_LOCATION = 12;
    // PHONE
    private static final int SNSN_REQUEST_READ_PHONE_STATE = 21;  // *
    private static final int SNSN_REQUEST_CALL_PHONE = 22;
    private static final int SNSN_REQUEST_USE_SIP = 23;
    private static final int SNSN_REQUEST_PROCESS_OUTGOING_CALLS = 24;
    // SMS
    private static final int SNSN_REQUEST_SEND_SMS = 31;     // *
    private static final int SNSN_REQUEST_RECEIVE_SMS = 32;
    private static final int SNSN_REQUEST_READ_SMS = 33;
    private static final int SNSN_REQUEST_RECEIVE_WAP_PUSH = 34;
    private static final int SNSN_REQUEST_RECEIVE_MMS = 35;
    // STORAGE
    private static final int SNSN_REQUEST_WRITE_STORAGE = 41;  // *
    private static final int SNSN_REQUEST_READ_STORAGE = 42;
    private static final int SNSN_REQUEST_WRITE_STORAGE_ASSET = 43;

    //@NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            Log.i(TAG, mTAG + ":PERMISSIONS: Using API >= 23 -- We need user permission checks!");
            if (checkAndRequestPermissions()) { // checkPermission()
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else { //requestPermission
                return;
                //requestPermission(); // Code for permission
            }
        } else {
            // For (API < 23) there is nothing to do, as it's handled in AndroidManifest.xml
            Log.i(TAG, mTAG + ":PERMISSIONS: Using API<23 -- OK.");
        }

        /*
        //make sure we have WRITE_EXTERNAL_STORAGE ACCESS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SNSN_REQUEST_WRITE_STORAGE_ASSET);
            Log.e(TAG, mTAG + ": Permission FAILURE: WRITE_EXTERNAL_STORAGE not granted.");
            Toast.makeText(this, "Permission Failure! Please enable all required permissions.", Toast.LENGTH_LONG).show();
        } else {
            Log.i(TAG, ": permission granted: WRITE_EXTERNAL_STORAGE");
            // No need to Toast proper behaviour
            //Toast.makeText(this, "Permission Granted: WRITE_EXTERNAL_STORAGE", Toast.LENGTH_LONG).show();
        }
        */

    	String incompatibilityReason = DeviceCompatibilityChecker.checkDeviceCompatibility(this.getApplicationContext());
        if(incompatibilityReason == null){
        	if(MsdConfig.getFirstRun(this)){
        		showFirstRunDialog();
        	} else{
        		createDatabaseAndStartDashboard();
        	}
        } else{
        	showDeviceIncompatibleDialog(incompatibilityReason);
        }
    }

    private void showDeviceIncompatibleDialog(String incompatibilityReason) {
    	Utils.showDeviceIncompatibleDialog(this, incompatibilityReason, new Runnable() {
			@Override
			public void run() {
				quitApplication();
			}
		});
    }
    
	private void showFirstRunDialog() {
		MsdDialog.makeConfirmationDialog(this, getResources().getString(R.string.alert_first_app_start_message),
				new OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						if(alreadyClicked)
							return;
						alreadyClicked = true;
					    // record the fact that the app has been started at least once
					    MsdConfig.setFirstRun(StartupActivity.this, false);
						createDatabaseAndStartDashboard();
					}
				},
				new OnClickListener() 
				{	
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						if(alreadyClicked)
							return;
						quitApplication();
					}
				},
				new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if(alreadyClicked)
							return;
						quitApplication();
					}
				}, false
				).show();
	}

	protected void quitApplication() {
		finish();
		System.exit(0);
	}

    //@NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void createDatabaseAndStartDashboard() {
		progressDialog = ProgressDialog.show(this, "Initializing database", "Please wait...", true);
		progressDialog.show();
		final Handler handler = new Handler();
		Thread t = new Thread(){
			@Override
			public void run() {
				helper = new MsdSQLiteOpenHelper(StartupActivity.this);
				SQLiteDatabase db = helper.getReadableDatabase();
				db.rawQuery("SELECT * FROM config", null).close();
				db.close();
				helper.close();
				handler.post(new Runnable() {
					@Override
					public void run() {
						progressDialog.dismiss();
						startDashboard();
					}
				});
			}
		};
		t.start();
	}

	private void startDashboard() {
        Intent i = new Intent(StartupActivity.this, DashboardActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        StartupActivity.this.startActivity(i);
        finish();
	}

    // ========================================================================
    // START: permissions handlers
    // ========================================================================

    /**
     ======================================================================
     We are using 4 groups of Dangerous permissions:
     -----------------------------------------------------------------------
     Group		Permission (we need)
     -----------------------------------------------------------------------
     LOCATION	ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
     PHONE		READ_PHONE_STATE, CALL_PHONE, USE_SIP, PROCESS_OUTGOING_CALLS
     SMS		SEND_SMS, RECEIVE_SMS, READ_SMS, RECEIVE_WAP_PUSH, RECEIVE_MMS
     STORAGE	READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
     -----------------------------------------------------------------------

     Please see detailed notes in:  AndroidManifest.xml
     ======================================================================
     */

    /**
     * These use the following imports:
     *      import android.support.v4.app.ActivityCompat;
     *      import android.support.v4.content.ContextCompat;
     *      import android.support.v7.app.AlertDialog;
     *      import android.util.Log;
     *      import android.widget.Toast;
     *      import java.util.ArrayList;
     *      import java.util.HashMap;
     *      import java.util.List;
     *      import java.util.Map;
     *
     * @return
     */
    //@Override
    private boolean checkAndRequestPermissions() {

        // We only ask one permission frome each group:
        int pLOCATION = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        int pPHONE    = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int pSMS      = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int pSTORAGE  = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (pLOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (pPHONE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (pSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (pSTORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    /**
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        Log.i(TAG, mTAG + ".onRequestPermissionsResult() called.");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);

                    // Check for all 4 permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        Log.i(TAG, mTAG + ": PERMISSIONS: ALL granted.");
                        // process the normal flow
                        return;
                        /*Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(i);
                        finish();*/
                        //else any one or both the permissions are not granted
                    } else {
                        Log.e(TAG, mTAG + ": Permission FAILURE: some permissions are not granted. Asking agin.");
                        Toast.makeText(this, "Permission Failure! Please enable ALL required permissions.", Toast.LENGTH_LONG).show();

                        // permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // Quit app if user do not want to accept permissions
                                                    quitApplication();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            explain("You have to accept the required permissions to continue. Do you want to go to app settings?");
                            // Quit app if user deny the permissions again
                            quitApplication();
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg){
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:de.srlabs.snoopsnitch")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }
    // ========================================================================
    // END: permissions handlers
    // ========================================================================

}
