package frameWork.base.core.fileSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileSystemTest {
	@Test
	public void exists() {
		assertTrue(FileSystem.Root.exists());
	}
	
	@Test
	public void isDirectory() {
		assertTrue(FileSystem.Root.isDirectory());
	}
	
	@Test
	public void isNotFile() {
		assertFalse(FileSystem.Root.isFile());
	}
	
}
