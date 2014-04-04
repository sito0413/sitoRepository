package frameWork.core.fileSystem;

import java.io.File;

public class FileElement extends File {
	public FileElement(final File root, final String name) {
		super(root, name);
		mkdirs();
	}
}