package frameWork.base.core.fileSystem;

import java.io.File;
import java.io.IOException;

public class Temp extends FileElement {
	public static class UploadDir extends FileElement {
		public UploadDir(final File root) {
			super(root, "_uploadDir");
		}
		
		public File getUploadfile(final String fileName) throws IOException {
			return File.createTempFile("upload", "." + fileName, this);
		}
	}
	
	public final Temp.UploadDir UploadDir;
	
	public Temp(final File root) {
		super(root, "temp");
		UploadDir = new UploadDir(this);
	}
}