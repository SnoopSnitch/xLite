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

    // We want this in order to reference package methods outside
    // of activities and fragments.
    public static String PACKAGE_NAME;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private MsdSQLiteOpenHelper helper;
	private boolean alreadyClicked = false;
	private ProgressDialog progressDialog;

    // In case we want to use PermissionDispatcher:
    //@NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        //MyApp = getApplicationContext().getPackageName();
        PACKAGE_NAME = BuildConfig.APPLICATION_ID;      // Defined in build.gradle
        Log.i(TAG, "STARTING: " + PACKAGE_NAME);

        // ToDo: perhaps move this into DeviceCompatibilityChecker
        // Check if device is Qualcomm MSM based
        /*if (!isDeviceMSM()) {
            // isDeviceMSM();
            Log.e(TAG, "Incompatible: Device is not a Qualcomm MSM! Quitting installation." );
            Toast.makeText(this, "Incompatible: Device is not a Qualcomm MSM!", Toast.LENGTH_LONG).show();
            quitApplication();
        }*/


        if (Build.VERSION.SDK_INT >= 23) {
            Log.i(TAG, mTAG + ":PERMISSIONS: Using API >= 23 -- We need user permission checks!");
            if (checkAndRequestPermissions()) {
                // checkPermission()
            } else {
                // requestPermission
                quitApplication();
                //return;
            }
        } else {
            // For (API < 23) there is nothing to do, as it's handled in AndroidManifest.xml
            Log.i(TAG, mTAG + ":PERMISSIONS: Using API<23 -- OK.");
        }

    	String incompatibilityReason = DeviceCompatibilityChecker.checkDeviceCompatibility(this.getApplicationContext());
        if(incompatibilityReason == null){
        	if(MsdConfig.getFirstRun(this)) {
        		showFirstRunDialog();
        	} else {
        		createDatabaseAndStartDashboard();
        	}
        } else {
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

        // We only ask one permission from each group:
        int pLOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
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

                        Log.i(TAG, "PERMISSIONS: ALL granted!");
                        Toast.makeText(this, "All permissions granted!", Toast.LENGTH_LONG).show();
                        // continue normal flow
                        //break;
                    } else {
                        // else if any one or more of the permissions are not granted
                        Log.e(TAG, mTAG + ": Permission FAILURE: some permissions are not granted. Asking again.");
                        Toast.makeText(this, "Permission Failure! Please enable ALL required permissions.", Toast.LENGTH_LONG).show();

                        // permission is denied (this is the first time, when "never ask again" is not checked)
                        // so ask again explaining the usage of permission
                        if (    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            showDialogOK("ALL permissions are required for this app",
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
                        } else {
                            // IF permission is denied (and "never ask again" is checked)
                            Log.e(TAG, mTAG + ": Permission FAILURE: some permissions are not granted. Asking again.");
                            explain("You have to accept ALL required permissions to continue. Do you want to go to app settings?");
                            // Quit app if user deny the permissions again
                            //quitApplication();
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
                        quitApplication();
                    }
                });
        dialog.show();
    }
    // ========================================================================
    // END: permissions handlers
    // ========================================================================

    // ToDo:
    // ========================================================================
    // START: A cleaner permissions handler
    // ========================================================================

    // See:   http://stackoverflow.com/a/41221852/1147688
    // NOTE:  We only list "dangerous" permissions here, as "normal" ones
    //        in the AndroidManifest are (still) automatically granted.

    /*
    String[] permissions = new String[]{
            // LOCATION
            Manifest.permission.ACCESS_FINE_LOCATION,
            //Manifest.permission.ACCESS_COARSE_LOCATION,
            // PHONE
            Manifest.permission.READ_PHONE_STATE,
            //Manifest.permission.CALL_PHONE,
            //Manifest.permission.USE_SIP,
            //Manifest.permission.PROCESS_OUTGOING_CALLS,
            // SMS
            Manifest.permission.SEND_SMS,
            //Manifest.permission.RECEIVE_SMS,
            //Manifest.permission.READ_SMS,
            //Manifest.permission.RECEIVE_WAP_PUSH,
            //Manifest.permission.RECEIVE_MMS,
            //STORAGE
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private static final int REQ_MULTI_PERMS = 100; // Request Multiple Permissions (callback) ID

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQ_MULTI_PERMS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQ_MULTI_PERMS) {
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }
    */
    // ========================================================================
    // END: permissions handlers
    // ========================================================================

}
