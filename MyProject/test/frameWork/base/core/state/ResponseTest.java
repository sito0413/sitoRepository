package frameWork.base.core.state;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import org.junit.Test;

public class ResponseTest {
	
	String contentType;
	int contentLength;
	
	@Test
	public void test() {
		final Response response = new Response(new ServletResponse() {
			@Override
			public void setLocale(final Locale arg0) {
			}
			
			@Override
			public void setContentType(final String arg0) {
				contentType = arg0;
			}
			
			@Override
			public void setContentLengthLong(final long arg0) {
			}
			
			@Override
			public void setContentLength(final int arg0) {
				contentLength = arg0;
			}
			
			@Override
			public void setCharacterEncoding(final String arg0) {
			}
			
			@Override
			public void setBufferSize(final int arg0) {
			}
			
			@Override
			public void resetBuffer() {
			}
			
			@Override
			public void reset() {
			}
			
			@Override
			public boolean isCommitted() {
				return false;
			}
			
			@Override
			public PrintWriter getWriter() throws IOException {
				return null;
			}
			
			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return null;
			}
			
			@Override
			public Locale getLocale() {
				return null;
			}
			
			@Override
			public String getContentType() {
				return null;
			}
			
			@Override
			public String getCharacterEncoding() {
				return null;
			}
			
			@Override
			public int getBufferSize() {
				return 0;
			}
			
			@Override
			public void flushBuffer() throws IOException {
				
			}
		}, null);
		response.setContentLength(9999);
		response.setContentType("ResponseTest");
		assertEquals(contentLength, 9999);
		assertEquals(contentType, "ResponseTest");
		assertNull(response.getOutputStream());
	}
	
}
