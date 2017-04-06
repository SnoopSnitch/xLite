package zz.snsn.xlite.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import zz.snsn.xlite.BuildConfig;
import zz.snsn.xlite.EncryptedFileWriterError;
import zz.snsn.xlite.qdmon.MsdService;
import zz.snsn.xlite.qdmon.MsdServiceHelper;

public class MsdLog {

	private static final String TAG = "SNSN";
	private static final String mTAG = "MsdLog";

	// We should use .getApplicationContext() when something points to a context
	//private static MsdServiceHelper msdServiceHelper;
	private static MsdServiceHelper msdServiceHelper;
	private static MsdService msd;

	public static String getTime(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ", Locale.US);
		return format.format(c.getTime());
	}
	
	private static String getTimePrefix(){
		return getTime() + " ";
	}

	public static void i(String tag, String msg) {
		Log.i(tag,msg);
		printlnToLog(getTimePrefix() + tag + ": INFO: " + msg);
	}
	public static void e(String tag, String msg) {
		Log.e(tag,msg);
		printlnToLog(getTimePrefix() + tag + ": ERROR: " + msg);
	}
	public static void v(String tag, String msg) {
		Log.v(tag,msg);
		printlnToLog(getTimePrefix() + tag + ": VERBOSE: " + msg);
	}
	public static void d(String tag, String msg) {
		Log.d(tag,msg);
		printlnToLog(getTimePrefix() + tag + ": DEBUG: " + msg);
	}
	public static void d(String tag, String msg, Throwable tr) {
		Log.d(tag,msg,tr);
		printlnToLog(getTimePrefix() + tag + ": DEBUG: " + msg + "\n" + Log.getStackTraceString(tr));
	}
	public static void e(String tag, String msg, Throwable tr) {
		Log.e(tag,msg,tr);
		printlnToLog(getTimePrefix() + tag + ": ERROR: " + msg + "\n" + Log.getStackTraceString(tr));
	}
	public static void w(String tag, String msg, Throwable tr) {
		Log.w(tag,msg,tr);
		printlnToLog(getTimePrefix() + tag + ": WARNING: " + msg + "\n" + Log.getStackTraceString(tr));
	}
	public static void w(String tag, String msg) {
		Log.w(tag,msg);
		printlnToLog(getTimePrefix() + tag + ": WARNING: " + msg);
	}

	public static void init(MsdServiceHelper msdServiceHelper) {
		MsdLog.msdServiceHelper = msdServiceHelper;
	}
	public static void init(MsdService msd) {
		MsdLog.msd = msd;
	}

	private static void printlnToLog(String line){
		if(msdServiceHelper != null){
			msdServiceHelper.writeLog(line + "\n");
		} else if(msd != null){
			try {
				msd.writeLog(line + "\n");
			} catch (EncryptedFileWriterError e) {
				throw new IllegalStateException("Error writing to log file: " + e.getMessage());
			}
		} else{
			throw new IllegalStateException("Please use MsdLog.init(context) before logging anything");
		}
	}


    private static boolean isBlank(String string) {
        return string == null || string.trim().length() == 0;
    }

	/**
	 * Getting system properties using OS command getprop, instead of using reflection.
	 *
	 * Reflection would require:
	 *    import com.android.internal.telephony.TelephonyProperties;
	 *    import android.os.SystemProperties;
     *
	 * 	//public static final String USER = Settings.System.getString("ro.build.user");
     *
     * 	Note: System.getProperty(key) doesn't work the way you think. It's different from
     * 	      the properties you get from the shell "getprop" command.
     *
	 *
	 * @param key
	 * @return property
     */
	public static String osgetprop(String key) {
		Process process;
		String property;
		try {
            //System.getProperty(key);
            process = Runtime.getRuntime().exec("/system/bin/getprop" + " " + key);
			//process = new ProcessBuilder().command("/system/bin/getprop" + " " + key).redirectErrorStream(true).start();
			BufferedReader bis = new BufferedReader(new InputStreamReader(process.getInputStream()));
			property = bis.readLine();
			process.destroy();
            if (isBlank(property)) {
                return "<n/a>";
            }
		} catch (IOException | NullPointerException ee) {
			Log.e(TAG, mTAG + ": osgetprop(): Error executing getprop: " + ee.toString());
            return "error";
		}
		return property;
	}

    /**
     * This gets some detailed information about phone model, hardware and Android version etc.
     * We also need to know the Kernel Version (as diag-helper build is dependent on that.)
     *
     *  For example:
     *
     * 	[gsm.version.baseband]: [T815XXU1AOH1]		x	android.os.SystemProperties.PROPERTY_BASEBAND_VERSION
     * 	[gsm.version.ril-impl]: [Samsung RIL v3.0]	x	android.os.SystemProperties.PROPERTY_RIL_IMPL
     * 	[ril.hw_ver]:           [MP 0.500]
     * 	[ril.modem.board]:      [SHANNON333]
     * 	[ro.arch]:              [exynos5433]
     * 	[ro.baseband]:          [unknown]
     * 	[ro.board.platform]:    [exynos5]
     *
	 * Collecting HW specific properties for global use/re-use without having to run
	 * shell command every time.
     *
     *  WARNING:  Each of these call the shell...this can take time and is inefficient.
     *           Instead, all this should be put in some static parcel somewhere...
	 *
     *  ToDo:   NOTE:
     *          We should probably make this as an array for easier grep when checking
     *          particular properties. In addition we need to check if if/when the
     *          global values gets zeroed by the garbage collector when app is put
     *          in background.

	 * @return prop
     */
	public static String getDeviceProps() {
		String prop;
		try {
            // ToDo: consider also adding the info from:
            // (1) Utils.checkDiag()
            prop =    "AOS version:               " + Build.VERSION.RELEASE + "\n"
                    + "Kernel version:            " + System.getProperty("os.version") + "\n"
                    + "Manufacturer:              " + Build.MANUFACTURER + "\n"
                    + "Brand:                     " + Build.BRAND + "\n"
                    + "Product (Model):           " + Build.PRODUCT + " (" + Build.MODEL  + ")" + "\n"
                    + "gsm_version_baseband:      " + osgetprop("gsm.version.baseband") + "\n"
                    + "gsm_version_ril-impl:      " + osgetprop("gsm.version.ril-impl") + "\n"
                    + "ro_confg_hw_chipidversion: " + osgetprop("ro.confg.hw_chipidversion") + "\n" // Huawei?
                    + "ril_hw_ver:                " + osgetprop("ril.hw_ver") + "\n"                // Samsung?
                    + "ril_modem_board:           " + osgetprop("ril.modem.board") + "\n"           // Samsung?
                    + "ro_arch:                   " + osgetprop("ro.arch") + "\n"                   // Samsung?
                    + "ro_product_cpu_abi:        " + osgetprop("ro.product.cpu.abi") + "\n"
                    + "ro_dual_sim_phone:         " + osgetprop("ro.dual.sim.phone") + "\n"
                    + "ro_board_platform:         " + osgetprop("ro.board.platform") + "\n";
        } catch (Exception ee) {
            Log.e(TAG, mTAG + "Exception in getDeviceProps(): Unable to retrieve system properties: " + ee);
            return "";
        }
		return prop;
	}

	/**
     * This adds some system-info to the header of the "debug_<timestamp>" files.
     *
     * WARNING:  Each of these call the shell...this can take time and is inefficient.
     *           Instead, all this should be put in some static parcel somewhere...
     *
     * NOTE: AS complain about context not being used, but it is in MsdService: "newDebugLogWriter"
     *
	 */
	public static String getLogStartInfo(Context context) {
		StringBuffer result = new StringBuffer();
        result.append("\n\n");
		result.append("Log opened:           " + Utils.formatTimestamp(System.currentTimeMillis()) + "\n");
		result.append("SnoopSnitch Version:  " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")" + "\n");
		result.append("Android version:      " + Build.VERSION.RELEASE + "\n");
		result.append("Kernel version:       " + System.getProperty("os.version") + "\n");
		result.append("Manufacturer:         " + Build.MANUFACTURER + "\n");
		result.append("Board:                " + Build.BOARD + "\n");
		result.append("Brand:                " + Build.BRAND + "\n");
		result.append("Product:              " + Build.PRODUCT + "\n");
		result.append("Model:                " + Build.MODEL + "\n");
		result.append("Baseband:             " + Build.getRadioVersion() + "\n");
        result.append("----------------------------------------------------\n");
		result.append(getDeviceProps());
		result.append("----------------------------------------------------\n");
        result.append("/dev/diag info: \n" + Utils.checkDiag() +"\n");
        result.append("----------------------------------------------------\n");
		return result.toString();
	}
}
