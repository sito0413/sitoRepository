package frameWork.base.core.fileSystem;

import java.io.File;

public class Resource extends FileElement {
	public Resource(final File root, final String name) {
		super(root, name);
	}
	
	public File getResource(final String target) {
		return new File(this, target);
	}
}