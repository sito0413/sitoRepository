package frameWork.core.fileSystem.config;

import java.io.File;

import frameWork.core.fileSystem.FileElement;

public class Config extends FileElement {
	
	public Config(final File root) {
		super(root, "config");
	}
	
	public String getString(final String key, final String defaultValue) {
		return defaultValue;
	}
	
}
