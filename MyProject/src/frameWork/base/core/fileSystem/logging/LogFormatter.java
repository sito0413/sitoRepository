package frameWork.base.core.fileSystem.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class LogFormatter extends Formatter {
	@Override
	public synchronized String format(final LogRecord record) {
		final StringBuilder message = new StringBuilder(String.format("%<tT.%<tL", record.getMillis())).append(' ')
		        .append(record.getMessage()).append('\n');
		final Throwable throwable = record.getThrown();
		if (throwable != null) {
			for (final StackTraceElement trace : throwable.getStackTrace()) {
				message.append('\t').append(trace).append('\n');
			}
		}
		return message.toString();
	}
}