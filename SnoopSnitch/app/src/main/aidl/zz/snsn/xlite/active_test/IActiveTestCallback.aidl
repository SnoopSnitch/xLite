package zz.snsn.xlite.active_test;

interface IActiveTestCallback {
	void testResultsChanged(in Bundle b);
	void testStateChanged();
	void deviceIncompatibleDetected();
}