package frameWork.base.print;


/**
 * 帳票例外クラス
 */
public class ReportException extends Exception {
	public ReportException() {
		super("内部値に不整合が発生しました（NULL）");
	}
	
	public ReportException(final String message, final Exception exp) {
		super(message, exp);
	}
	
	public ReportException(final String message) {
		super(message);
	}
	
	public ReportException(final Exception e) {
		super(e);
	}
}
