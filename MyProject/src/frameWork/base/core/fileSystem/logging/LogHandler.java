package frameWork.base.core.fileSystem.logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import frameWork.base.core.fileSystem.FileSystem;

public class LogHandler extends FileHandler {
	public LogHandler(final File logDir, final String name) throws SecurityException, IOException {
		super(new File(logDir, name + FileSystem.Config.LOGGING_EXTENSION).getAbsolutePath(), true);
		setFormatter(new LogFormatter());
		setLevel(Level.ALL);
	}
}
