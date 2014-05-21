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
	
	public static class PrintDir extends FileElement {
		public static class PrintTempDir extends FileElement {
			public static final String ImageDir = null;
			
			public PrintTempDir(final File root, final String dir) {
				super(root, dir);
			}
			
			public File getImagefile(final String fileName) throws IOException {
				return File.createTempFile("print", "." + fileName, this);
			}
		}
		
		public final PrintTempDir ImageDir;
		public final PrintTempDir PdfDir;
		
		public PrintDir(final File root) {
			super(root, "_printDir");
			ImageDir = new PrintTempDir(this, "_imageDir");
			PdfDir = new PrintTempDir(this, "_pdfDir");
		}
	}
	
	public final Temp.PrintDir PrintDir;
	public final Temp.UploadDir UploadDir;
	
	public Temp(final File root) {
		super(root, "temp");
		UploadDir = new UploadDir(this);
		PrintDir = new PrintDir(this);
	}
}