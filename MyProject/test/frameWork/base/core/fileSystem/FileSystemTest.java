package frameWork.base.core.fileSystem;

import static org.junit.Assert.*;

import org.junit.Test;

import frameWork.base.core.fileSystem.FileSystem;

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
