package zz.snsn.xlite.analysis;

import java.util.Vector;

import android.database.sqlite.SQLiteDatabase;
import zz.snsn.xlite.upload.DumpFile;
import zz.snsn.xlite.upload.FileState;

public interface AnalysisEvent {
	public static final int STATE_AVAILABLE = 0;
	public static final int STATE_UPLOADED = 1;
	public static final int STATE_DELETED = 2;
	public FileState getUploadState();
	public int getUploadState(SQLiteDatabase db);
	public Vector<DumpFile> getFiles(SQLiteDatabase db);
	public void markForUpload(SQLiteDatabase db);
}
