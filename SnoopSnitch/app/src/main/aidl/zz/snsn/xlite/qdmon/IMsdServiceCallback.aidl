package zz.snsn.xlite.qdmon;

interface IMsdServiceCallback {
	void stateChanged(String reason);
	void internalError();
}
