package frameWork.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

import frameWork.core.fileSystem.Base;

public class BaseTest {
	static boolean flg;
	
	@Test
	public void exists() {
		assertTrue(Base.Path.exists());
	}
	
	@Test
	public void isDirectory() {
		assertTrue(Base.Path.isDirectory());
	}
	
	@Test
	public void isNotFile() {
		assertFalse(Base.Path.isFile());
	}
	
}
