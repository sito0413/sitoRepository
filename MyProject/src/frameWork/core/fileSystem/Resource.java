package frameWork.core.fileSystem;

import java.io.File;

public class Resource extends FileElement {
	
	public Resource(final File root) {
		super(root, "resource");
	}
	
	public File getResource(final String target) {
		return new File(this, target);
	}
	
}
