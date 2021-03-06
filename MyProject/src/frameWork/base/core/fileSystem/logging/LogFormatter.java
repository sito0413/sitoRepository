package frameWork.base.core.fileSystem.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class LogFormatter extends Formatter {
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Override
	public synchronized String format(final LogRecord record) {
		final StringBuilder message = new StringBuilder(dateFormat.format(new Date(record.getMillis()))).append(' ');
		final Throwable throwable = record.getThrown();
		if (throwable == null) {
			message.append(record.getMessage()).append('\r').append('\n');
		}
		else {
			message.append(throwable.getClass().getCanonicalName()).append(" : ").append(record.getMessage())
			        .append('\r').append('\n');
			for (final StackTraceElement trace : throwable.getStackTrace()) {
				message.append('\t').append(trace).append('\r').append('\n');
			}
		}
		return message.toString();
	}
}