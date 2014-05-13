package framework2.base.report.util;

/**
 * 帳票例外クラス
 * 
 * @author t-tsuda
 * @version 1.0.0
 * @作成日 2009/07/05
 */
public class ReportException extends Exception {

	/**
	 * コンストラクタ
	 * 
	 * @param objExp
	 */
	public ReportException(final Exception objExp) {
		this(ExceptionType.NULL, objExp);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param strMessage
	 */
	public ReportException(final String strMessage) {
		super(strMessage);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param strMessage
	 * @param objExp
	 */
	public ReportException(final String strMessage, final Exception objExp) {
		super(strMessage, objExp);
	}

	public ReportException(final ExceptionType not_reader_type) {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public ReportException(final ExceptionType not_reader_type, final Object reader) {
		// TODO 自動生成されたコンストラクター・スタブ
	}

}
