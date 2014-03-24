package frameWork.core.wrap;

import javax.servlet.ServletResponse;

import frameWork.utility.Response;

public class WrapResponse implements Response {
	private final ServletResponse response;
	
	public WrapResponse(final ServletResponse response) {
		this.response = response;
	}
	
	@Override
	public void setContentLength(final int i) {
		response.setContentLength(i);
	}
	
	@Override
	public void setContentType(final String contentType) {
		response.setContentType(contentType);
	}
}
