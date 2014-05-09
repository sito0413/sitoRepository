package frameWork.base.core.viewCompiler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import frameWork.base.core.fileSystem.FileSystem;

public class ViewerWriter {
	private byte[] buffer;
	private int size;
	
	ViewerWriter() {
		buffer = new byte[FileSystem.Config.VIEWER_WRITER];
	}
	
	int size() {
		return size;
	}
	
	byte[] getBuffer() {
		return buffer;
	}
	
	void writeTo(final OutputStream out) throws IOException {
		out.write(buffer, 0, size);
	}
	
	public void write() {
	}
	
	public void write(final int c) throws IOException {
		write(Integer.toString(c));
	}
	
	public void write(final double c) throws IOException {
		write(Double.toString(c));
	}
	
	public void write(final long c) throws IOException {
		write(Long.toString(c));
	}
	
	public void write(final String str) throws IOException {
		byte[] bs;
		try {
			bs = str.getBytes(FileSystem.Config.VIEW_CHAREET);
		}
		catch (final UnsupportedEncodingException e) {
			bs = str.getBytes("UTF-8");
		}
		if ((size + bs.length) > buffer.length) {
			
			final byte[] buf = new byte[(size + bs.length) + FileSystem.Config.VIEWER_WRITER];
			System.arraycopy(buffer, 0, buf, 0, size);
			buffer = buf;
		}
		for (final byte b : bs) {
			buffer[size++] = b;
		}
	}
}
