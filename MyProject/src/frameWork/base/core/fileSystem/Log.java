package frameWork.base.core.fileSystem;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import frameWork.base.core.fileSystem.logging.LogHandler;

public class Log extends FileElement {
	private String logdate = "";
	
	public Log(final File root) {
		super(root, "log");
		remakeLogfile();
	}
	
	public void logging(final Throwable exp) {
		remakeLogfile();
		Logger.getLogger(logdate + "-exception").log(Level.WARNING, exp.getMessage(), exp);
		Logger.getLogger(logdate + "-logging").log(Level.WARNING, exp.getMessage(), exp);
	}
	
	public void logging(final String message) {
		remakeLogfile();
		Logger.getLogger(logdate + "-message").log(Level.INFO, message);
		Logger.getLogger(logdate + "-logging").log(Level.INFO, message);
	}
	
	private synchronized void remakeLogfile() {
		final String nowString = String.format("%tF", System.currentTimeMillis());
		if (!logdate.equals(nowString)) {
			logdate = nowString;
			final File logDir = new File(this, logdate);
			logDir.mkdirs();
			remakeLogfile(logDir, FileSystem.Config.LOGGING_EXCEPTION_FILE_NAME);
			remakeLogfile(logDir, FileSystem.Config.LOGGING_MESSAGE_FILE_NAME);
			remakeLogfile(logDir, FileSystem.Config.LOGGING_LOGGING_FILE_NAME);
		}
	}
	
	private void remakeLogfile(final File logDir, final String fileName) {
		try {
			Logger.getLogger(logdate + "-" + fileName).addHandler(new LogHandler(logDir, fileName));
			Logger.getLogger(logdate + "-" + fileName).setLevel(Level.ALL);
		}
		catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
}