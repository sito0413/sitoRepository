package frameWork.base.core.state;

import java.io.OutputStream;

import javax.servlet.ServletResponse;

public class Response {
	private final ServletResponse response;
	private final OutputStream outputStream;
	
	public Response(final ServletResponse response, final OutputStream outputStream) {
		this.response = response;
		this.outputStream = outputStream;
	}
	
	public void setContentLength(final int i) {
		response.setContentLength(i);
	}
	
	public void setContentType(final String contentType) {
		response.setContentType(contentType);
	}
	
	public OutputStream getOutputStream() {
		return outputStream;
	}
}
