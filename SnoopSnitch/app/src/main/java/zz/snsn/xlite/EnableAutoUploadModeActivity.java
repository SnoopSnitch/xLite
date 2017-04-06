package zz.snsn.xlite;

import java.util.Vector;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import zz.snsn.xlite.analysis.Event;
import zz.snsn.xlite.analysis.ImsiCatcher;
import zz.snsn.xlite.qdmon.MsdSQLiteOpenHelper;
import zz.snsn.xlite.upload.FileState;
import zz.snsn.xlite.util.MsdDatabaseManager;
import zz.snsn.xlite.util.MsdDialog;


public class EnableAutoUploadModeActivity extends BaseActivity
{
	public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
	private int notificationId;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		MsdDatabaseManager.initializeInstance(new MsdSQLiteOpenHelper(EnableAutoUploadModeActivity.this));
		Bundle extras = getIntent().getExtras();
		notificationId = extras.getInt(NOTIFICATION_ID);
		String msg = getString(R.string.notification_enable_auto_upload_mode_confirm);
		MsdDialog.makeConfirmationDialog(this, msg, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enableAutoUploadMode();
				uploadAllUploadableEvents();
				cancelNotification();
				finish();
			}
		}, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				// Don't delete notification when pressing the back button
				finish();
			}
		}, "Yes", "No", true).show();

	}

	private void enableAutoUploadMode() {
		// ToDo: Fix deprecated use: See MsdConfig.java
		//SharedPreferences sharedPrefs = this.getSharedPreferences("zz.snsn.xlite_preferences", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);;
		SharedPreferences sharedPrefs = this.getSharedPreferences("zz.snsn.xlite_preferences", Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putBoolean("settings_auto_upload_mode", true);
		editor.commit();
	}

	private void uploadAllUploadableEvents(){
		Vector<Event> events = getMsdServiceHelperCreator().getMsdServiceHelper().getData().getEvent(0,System.currentTimeMillis());
		for(Event event:events){
			if(event.getUploadState() == FileState.STATE_AVAILABLE)
				try{
					event.upload();
				} catch(Exception e){}
		}
		Vector<ImsiCatcher> imsiCatchers = getMsdServiceHelperCreator().getMsdServiceHelper().getData().getImsiCatchers(0,System.currentTimeMillis());
		for(ImsiCatcher imsi:imsiCatchers){
			if(imsi.getUploadState() == FileState.STATE_AVAILABLE)
				try {
					imsi.upload();
				} catch (Exception e){
					// nothing
				}
		}
		getMsdServiceHelperCreator().getMsdServiceHelper().triggerUploading();
	}

	private void cancelNotification(){
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(notificationId);
	}

}
