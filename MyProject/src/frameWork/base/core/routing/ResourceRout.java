package frameWork.base.core.routing;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import frameWork.base.core.authority.Authority;
import frameWork.base.core.authority.Role;
import frameWork.base.core.state.Response;
import frameWork.base.core.state.State;

@Authority(allowRole = Role.ANONYMOUS)
class ResourceRout extends Rout implements RoutingHandler {
	
	public ResourceRout(final String method, final String resource) throws NoSuchMethodException, SecurityException {
		super(ResourceRout.class, ResourceRout.class.getMethod(method, State.class), resource);
	}
	
	@Authority(allowRole = Role.ANONYMOUS)
	@Override
	public void get(final State state) {
		// NOOP
	}
	
	@Authority(allowRole = Role.ANONYMOUS)
	@Override
	public void post(final State state) {
		// NOOP
	}
	
	@Override
	void invoke(final State state, final Response response) throws Exception {
		final OutputStream outputStream = response.getOutputStream();
		final File resource = new File(page);
		try (InputStream inputStream = new FileInputStream(resource)) {
			int i = -1;
			final byte[] b = new byte[256];
			while ((i = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, i);
			}
			outputStream.flush();
			outputStream.close();
		}
	}
}
