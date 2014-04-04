package frameWork.core.viewCompiler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class ViewerWriter extends Writer {
	private byte[] _buf;
	private int _size;
	
	ViewerWriter() {
		_buf = new byte[2048];
	}
	
	int size() {
		return _size;
	}
	
	void writeTo(final OutputStream out) throws IOException {
		out.write(_buf, 0, _size);
	}
	
	public void write() {
	}
	
	@Override
	public void write(final int c) throws IOException {
		write(Integer.toString(c));
	}
	
	public void write(final double c) throws IOException {
		write(Double.toString(c));
	}
	
	public void write(final long c) throws IOException {
		write(Long.toString(c));
	}
	
	@Override
	public void write(final char[] cbuf, final int off, final int len) throws IOException {
		final byte[] bs = new String(cbuf, off, len).getBytes("UTF-8");
		if ((_size + bs.length) > _buf.length) {
			final byte[] buf = new byte[((_buf.length + bs.length) * 4) / 3];
			System.arraycopy(_buf, 0, buf, 0, _size);
			_buf = buf;
		}
		for (final byte b : bs) {
			_buf[_size++] = b;
		}
	}
	
	@Override
	public void flush() {
	}
	
	@Override
	public void close() {
	}
}
