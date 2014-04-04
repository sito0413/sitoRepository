package frameWork.core.state;

import javax.servlet.ServletResponse;

public class Response {
	private final ServletResponse response;
	
	public Response(final ServletResponse response) {
		this.response = response;
	}
	
	public void setContentLength(final int i) {
		response.setContentLength(i);
	}
	
	public void setContentType(final String contentType) {
		response.setContentType(contentType);
	}
}
