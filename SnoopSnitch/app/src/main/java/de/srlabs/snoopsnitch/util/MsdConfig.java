package de.srlabs.snoopsnitch.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.preference.PreferenceManager;


/**
 * This class contains a set of static methods for accessing the App configuration.
 * 
 */
public class MsdConfig {

	private static final String TAG = "SNSN";
	private static final String mTAG = "MsdConfig :";

	private static SharedPreferences sharedPrefs(Context context) {
        // ToDo: Need better multi-process fix here. Consider:
        //       https://github.com/DozenWang/DPreference
        //       https://github.com/grandcentrix/tray
        //       https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
		//Deprecated: return context.getSharedPreferences("de.srlabs.snoopsnitch_preferences", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        return context.getSharedPreferences("de.srlabs.snoopsnitch_preferences", Context.MODE_PRIVATE);
	}

	// ========================================================================
	// Device Compatibility
	// ========================================================================

	public static boolean getDeviceCompatibleDetected(Context context) {
		return sharedPrefs(context).getBoolean("device_compatible_detected", false);
	}

	public static void setDeviceCompatibleDetected(Context context, boolean compatible) {
		Editor editor = sharedPrefs(context).edit();
		editor.putBoolean("device_compatible_detected", compatible);
		editor.commit();
	}

	// ToDo: Author should add a redmine issue request with the specific functionality intended or remove. (Emi: 2017-01-09)
	// The version suffix should be counted up when there is a solution for the
	// "No baseband messages" problem (so that phones detected to be
	// incompatible with a previous version can used again)
	private static final String DEVICE_INCOMPATIBLE_DETECTED_FLAG = "device_incompatible_detected_2";

	public static boolean getDeviceIncompatible(Context context) {
		return sharedPrefs(context).getBoolean(DEVICE_INCOMPATIBLE_DETECTED_FLAG, false);
	}

	public static void setDeviceIncompatible(Context context, boolean incompatible) {
		Editor editor = sharedPrefs(context).edit();
		editor.putBoolean(DEVICE_INCOMPATIBLE_DETECTED_FLAG, incompatible);
		editor.commit();
	}

	// ========================================================================
	// Settings: Logfiles & data cleanup interval
	// ========================================================================
	public static int getBasebandLogKeepDurationHours(Context context) {
		return 24*Integer.parseInt(sharedPrefs(context).getString("settings_basebandLogKeepDuration", "1"));
	}

	public static int getDebugLogKeepDurationHours(Context context)	{
		return 24*Integer.parseInt(sharedPrefs(context).getString("settings_debugLogKeepDuration", "1"));
	}

	public static int getMetadataKeepDurationHours(Context context) {
		return 24*Integer.parseInt(sharedPrefs(context).getString("settings_basebandMetadataKeepDuration", "1"));
	}
	
	public static int getLocationLogKeepDurationHours(Context context) {
		return 24*Integer.parseInt(sharedPrefs(context).getString("settings_locationLogKeepDuration", "1"));
	}

	public static int getAnalysisInfoKeepDurationHours(Context context) {
		return 24*Integer.parseInt(sharedPrefs(context).getString("settings_analysisInfoKeepDuration", "30"));
	}

	// ========================================================================
	// Settings: privacy
	// ========================================================================
	public static boolean gpsRecordingEnabled(Context context) {
        return sharedPrefs(context).getBoolean("settings_gpsRecording", false);
	}

	public static boolean networkLocationRecordingEnabled(Context context) {
		return sharedPrefs(context).getBoolean("settings_networkLocationRecording", true);
	}

	public static boolean recordUnencryptedLogfiles(Context context) {
		return sharedPrefs(context).getBoolean("settings_recordUnencryptedLogfiles", false);
	}

	public static boolean recordUnencryptedDumpfiles(Context context) {
		return sharedPrefs(context).getBoolean("settings_recordUnencryptedDumpfiles", false);
	}

	public static boolean dumpUnencryptedEvents(Context context) {
		return sharedPrefs(context).getBoolean("settings_dumpUnencryptedEvents", false);
	}
	
	public static String getAppId(Context context) {
        return sharedPrefs(context).getString("settings_appId", "");
	}

	public static void setAppId(Context context, String appID) {
		Editor editor = sharedPrefs(context).edit();
		editor.putString("settings_appId", appID);
		editor.commit();
	}

	public static boolean getActiveTestForceOffline(Context context) {
		return sharedPrefs(context).getBoolean("settings_active_test_force_offline", false);
	}


	// ========================================================================
	// Own phone number Sanity check
	// ========================================================================
	/*
	 * We can check these:
	 *   1. [ ] That the number starts with a valid country code (e.g. "+49" )
	 *   2. [ ] We can attempt to get own number from various hacks, but not SIM nor API
	 *
	 *   For (2) we can cross check SIM country iso with one of the following:
	 *   	[ ] asset CSV file  					- less coding
	 *   	[x] asset JSON file 					- more coding but better portability (?)
	 *      [ ] our own DB table "mcc" in msd.db 	- may break multi-threading!
	 */
	/**
	 *
	 * Maybe this should be a JSON file reader instead?
	 * @param db
	 * @param countryIso
     */
	public static String getCountryCode(SQLiteDatabase db, String countryIso) {
		String sql;
		String country_code = null;
		//String simIso = tm.getSimCountryIso();
		try {
			/*
			db.beginTransaction();
			sql = "SELECT UNIQUE call_code FROM mcc WHERE iso = " + simIso + ";";
			country_code = db.execSQL(sql);
			db.setTransactionSuccessful();
			*/
		} catch (Exception ee) {
			Log.e(TAG, mTAG + "getOwnNumber: Exeception from SQL execution:", ee);
		}finally {
			//db.endTransaction();
		}
		return country_code;
	}

	/**
	 * Get own phone number or if not possible, at least the dialling country code
	 *
	 * NOTE: The AOS API can't provide for getting the number as it is not something
	 * 		 stored on the SIM, nor on the phone. The best we can do is getting the
	 * 		 country ISO code from the API and then cross reference it with either
	 * 		 a provided CSV, JSON or from our SQL table "mcc".
	 *
	 * @param context
	 * @return
     */
	public static String getOwnNumber(Context context) {
		// ToDo: Need smarter way to get number here!
		//
		String phone_number = sharedPrefs(context).getString("own_number", "");
		if (phone_number.equals(null)) {

			// Try to get MSISDN etc
			/**
			 *  try to use:     getSimCountryIsoForPhone
			 *  				getSimOperatorNumeric
			 *                  getLine1Number
			 * 					getLine1NumberForSubscriber
			 *					getMsisdn
			 *
			 */

			//TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			//phone_number = tm.getLine1Number();
			// still nothing?
			/*if (phone_number.equals(null)) {
				//
			}*/
			// Toast?
		}
		return phone_number;
	}

	public static void setOwnNumber(Context context, String ownNumber) {
		sharedPrefs(context).edit().putString("own_number", ownNumber).commit();
	}

	// ========================================================================

	public static boolean getActiveTestSMSMODisabled(Context context) {
		return sharedPrefs(context).getBoolean("settings_active_test_sms_mo_disabled", false);
	}

	public static String getActiveTestSMSMONumber(Context context) {
		return sharedPrefs(context).getString("settings_active_test_sms_mo_number", "*4*");
	}

	public static long getDataJSLastCheckTime(Context context) {
			return sharedPrefs(context).getLong("data_js_last_check_time", 0);
	}

	public static void setDataJSLastCheckTime(Context context, Long timeInMillis) {
		Editor editor = sharedPrefs(context).edit();
		editor.putLong("data_js_last_check_time", timeInMillis);
		editor.commit();
	}

	public static String getDataJSLastModifiedHeader(Context context) {
		return sharedPrefs(context).getString("data_js_last_modified_header", null);
	}

	public static void setDataJSLastModifiedHeader(Context context, String header) {
		Editor editor = sharedPrefs(context).edit();
		editor.putString("data_js_last_modified_header", header);
		editor.commit();
	}

	public static int getActiveTestNumIterations(Context context) {
		return Integer.parseInt(sharedPrefs(context).getString("settings_active_test_num_iterations", "3"));
	}
	
	public static boolean getParserLogging(Context context) {
        return sharedPrefs(context).getBoolean("settings_parser_logging", false);
	}

	public static boolean getDumpAnalysisStackTraces(Context context) {
		return sharedPrefs(context).getBoolean("settings_debugging_dump_analysis_stacktraces", false);
	}

	public static boolean getActiveTestDisableUpload(Context context) {
		return sharedPrefs(context).getBoolean("settings_active_test_disable_upload", false);
	}

	public static boolean getCrash(Context context)	{
		return sharedPrefs(context).getBoolean("settings_crash", false);
	}

	public static void setCrash(Context context, boolean crash) {
		Editor edit = sharedPrefs(context).edit();
		edit.putBoolean("settings_crash", crash);
		edit.commit();
	}

	public static long getLastCleanupTime(Context context) {
		return sharedPrefs(context).getLong("last_cleanup_time", 0);
	}

	public static void setLastCleanupTime(SharedPreferences pref, long time) {
		Editor edit = pref.edit();
		edit.putLong("last_cleanup_time", time);
		edit.commit();
	}

	public static void setLastCleanupTime(Context context, long time) {
		setLastCleanupTime(sharedPrefs(context), time);
	}

	public static boolean getFirstRun(Context context) {
		return sharedPrefs(context).getBoolean("app_first_run", true);
	}

	public static void setFirstRun(Context context, boolean firstRun) {
		Editor edit = sharedPrefs(context).edit();
		edit.putBoolean("app_first_run", firstRun);
		edit.commit();
	}

	public static boolean getStartOnBoot(Context context) {
        return sharedPrefs(context).getBoolean("settings_start_on_boot", true);
	}

	public static void setStartOnBoot(Context context, boolean startOnBoot) {
		Editor edit = sharedPrefs(context).edit();
		edit.putBoolean("settings_start_on_boot", startOnBoot);
		edit.commit();
	}

    public static boolean getAutoUploadMode(Context context){
		return sharedPrefs(context).getBoolean("settings_auto_upload_mode", false);
	}

    public static boolean getUploadDailyPing(Context context){
		return sharedPrefs(context).getBoolean("settings_upload_daily_ping", false);
	}

    public static boolean getPcapRecordingEnabled(Context context){
		return sharedPrefs(context).getBoolean("settings_enable_pcap_recording", false);
	}

	/**
	 * This is used in MsdService.launchParser() as:
	 *
	 * 		String pcapBaseFileName = MsdConfig.getPcapFilenamePrefix(this);
	 * .	String filename = pcapBaseFileName + "_" + String.format(Locale.US, ....) + ".pcap";
	 *
	 * 		The default storage location is/was: 		/sdcard/snoopsnitch/
	 * 		The default storage location should be: 	/.../snoopsnitch_pcap/
	 * 		The default strings for this are in: 		strings.xml
	 *  	The default preference for this are in: 	preferences.xml
	 *
	 * @param context
	 * @return
     */
    public static String getPcapFilenamePrefix(Context context) {
		// Was:
		//		return sharedPrefs(context).getString("settings_pcap_filename_prefix", "/sdcard/snoopsnitch");
		//
		// New options:
        // getExternalStorageDirectory : requires WRITE_EXTERNAL_STORAGE permission
        // getExternalFilesDir(String) : requires no permissions
        // getExternalCacheDir()       : requires no permissions
        // getExternalMediaDirs        : requires no permissions

		// getDataDirectory ?

		//return sharedPrefs(context).getString("settings_pcap_filename_prefix",
		//		//Environment.getExternalStorageDirectory().getPath() + "/sdcard/snoopsnitch");
		//		Environment.getExternalStorageDirectory().getPath() + "snoopsnitch");
        return sharedPrefs(context).getString("settings_pcap_filename_prefix", "snoopsnitch");
	}

	public static String getPcapFilenamePath(Context context) {
		//String prefPath = sharedPrefs(context).getString("settings_pcap_file_path",
		//		Environment.getDataDirectory().getPath() + "/snoopsnitch_pcap/");
				//Environment.getExternalFilesDir().getPath() + "/snoopsnitch/");
				//Environment.getExternalStorageDirectory().getPath() + "/snoopsnitch");
		//return 	Environment.getDataDirectory().getPath() + "/snoopsnitch_pcap/";
		//return Environment.getExternalMediaDirs() + "/snoopsnitch_pcap/"; // API 21+
		return Environment.getExternalStorageDirectory().getPath() + "/snoopsnitch_pcap/"; //
	}
}
