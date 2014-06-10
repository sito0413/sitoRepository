package frameWork.base.core.routing;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import frameWork.base.core.state.Response;

public class ResourceRoutTest {
	
	@Test
	public void test() {
		final File file = new File("test.txt");
		final String text = "@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test@test-test";
		
		try {
			final ResourceRout resourceRout = new ResourceRout("get", "test.txt");
			try (final FileOutputStream outputStream = new FileOutputStream(file)) {
				outputStream.write(text.getBytes());
			}
			try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				resourceRout.invoke(null, new Response(null, outputStream));
				assertEquals(new String(outputStream.toByteArray()), text);
			}
			resourceRout.get(null);
			resourceRout.post(null);
			file.delete();
			try {
				resourceRout.invoke(null, new Response(null, System.out));
				fail("error");
			}
			catch (final Exception e) {
				//NOP
			}
		}
		catch (final Exception e) {
			fail(e.getMessage());
		}
	}
}
