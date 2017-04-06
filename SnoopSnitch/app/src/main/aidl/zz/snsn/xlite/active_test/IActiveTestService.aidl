package zz.snsn.xlite.active_test;

import zz.snsn.xlite.active_test.IActiveTestCallback;

interface IActiveTestService {
	void registerCallback(IActiveTestCallback callback);
	boolean startTest(String ownNumber);
	void stopTest();
	void clearResults();
	void clearCurrentResults();
	void clearCurrentFails();
	boolean isTestRunning();
	void setUploadDisabled(boolean uploadDisabled);
	void applySettings();
}
