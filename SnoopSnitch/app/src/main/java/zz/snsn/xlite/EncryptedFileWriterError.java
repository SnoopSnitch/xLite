package zz.snsn.xlite;

public class EncryptedFileWriterError extends Exception {
	/**
	 * ToDo: get rid of this!
	 * See: EncryptedFileWriter.OpensslErrorThread()
	 */
	private static final long serialVersionUID = 1L;
	private Throwable e = null;

	public EncryptedFileWriterError (String message, Throwable e) {
		super(message);
		this.e = e;
	}

	public EncryptedFileWriterError (Throwable e)
	{
		this.e = e;
	}

	public EncryptedFileWriterError (String message)
	{
		super(message);
	}
	
	public Throwable getE() {
		return e;
	}

}