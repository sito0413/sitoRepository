package frameWork.base.core.fileSystem;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import frameWork.architect.Literal;

public class FileSystemTest {
	@AfterClass
	public static void afterClass() {
		new File(Literal.info_xml).delete();
	}
	
	@BeforeClass
	public static void beforeClass() {
		new File(Literal.info_xml).delete();
	}
	
	@Test
	public void case1() {
		assertTrue(FileSystem.Root.exists());
		assertTrue(FileSystem.Root.isDirectory());
		assertFalse(FileSystem.Root.isFile());
	}
	
	@Test
	public void case2() {
		final File file = new File(Literal.info_xml);
		final Properties properties = new Properties();
		try (FileOutputStream os = new FileOutputStream(file)) {
			properties.storeToXML(os, "");
			FileSystem.loadFromXML(file.toURI().toURL());
			file.delete();
			FileSystem.loadFromXML(file.toURI().toURL());
			FileSystem.loadFromXML(null);
		}
		catch (final IOException e1) {
			fail(e1.getMessage());
		}
	}
	
	@Test
	public void case3() {
		try {
			{
				final File file = new File(Literal.info_xml);
				file.createNewFile();
				assertEquals(FileSystem.loadPathDir(file.getAbsolutePath()).getAbsolutePath(),
				        new File(Literal.Root).getAbsolutePath());
				file.delete();
			}
			{
				final File dir = new File("FileSystemTest");
				dir.mkdirs();
				assertEquals(FileSystem.loadPathDir(dir.getAbsolutePath()).getAbsolutePath(), dir.getAbsolutePath());
				dir.delete();
			}
			{
				final File dir = new File("FileSystemTest");
				dir.delete();
				assertEquals(FileSystem.loadPathDir(dir.getAbsolutePath()).getAbsolutePath(), dir.getAbsolutePath());
				dir.delete();
			}
			{
				final File dir = new File("test");
				assertEquals(FileSystem.loadPathDir(dir.getAbsolutePath()).getAbsolutePath(), dir.getAbsolutePath());
			}
			{
				assertEquals(FileSystem.loadPathDir(null).getAbsolutePath(), new File(Literal.Root).getAbsolutePath());
			}
		}
		catch (final IOException e1) {
			fail(e1.getMessage());
		}
	}
	
	@Test
	public void case4() {
		assertEquals(FileSystem.loadSystemID("test"), "test");
		assertEquals(FileSystem.loadSystemID("true"), "true");
		assertEquals(FileSystem.loadSystemID(null), Literal.Temp);
	}
	
	@Test
	public void case5() {
		try {
			{
				{
					final File file = new File(Literal.info_xml);
					file.createNewFile();
					assertEquals(FileSystem.loadRootDir(file, "test").getAbsolutePath(), new File(Literal.Root,
					        Literal.Temp).getAbsolutePath());
					file.delete();
				}
				{
					final File dir = new File("FileSystemTest");
					dir.mkdirs();
					assertEquals(FileSystem.loadRootDir(dir, "test").getAbsolutePath(),
					        new File(dir, "test").getAbsolutePath());
					new File(dir, "test").delete();
					dir.delete();
				}
				{
					final File dir = new File("FileSystemTest");
					dir.delete();
					assertEquals(FileSystem.loadRootDir(dir, "test").getAbsolutePath(),
					        new File(dir, "test").getAbsolutePath());
					new File(dir, "test").delete();
					dir.delete();
				}
				{
					final File dir = new File("test");
					assertEquals(FileSystem.loadRootDir(dir, "test").getAbsolutePath(),
					        new File(dir, "test").getAbsolutePath());
					new File(dir, "test").delete();
				}
				{
					assertEquals(FileSystem.loadRootDir(null, "test").getAbsolutePath(), new File(Literal.Root,
					        Literal.Temp).getAbsolutePath());
				}
			}
			{
				{
					final File file = new File(Literal.info_xml);
					file.createNewFile();
					assertEquals(FileSystem.loadRootDir(file, Literal.Temp).getAbsolutePath(), new File(Literal.Root,
					        Literal.Temp).getAbsolutePath());
					file.delete();
				}
				{
					final File dir = new File("FileSystemTest");
					dir.mkdirs();
					assertEquals(FileSystem.loadRootDir(dir, Literal.Temp).getAbsolutePath(), new File(dir,
					        Literal.Temp).getAbsolutePath());
					new File(dir, Literal.Temp).delete();
					dir.delete();
				}
				{
					final File dir = new File("FileSystemTest");
					dir.delete();
					assertEquals(FileSystem.loadRootDir(dir, Literal.Temp).getAbsolutePath(), new File(dir,
					        Literal.Temp).getAbsolutePath());
					new File(dir, Literal.Temp).delete();
					dir.delete();
				}
				{
					final File dir = new File("test");
					assertEquals(FileSystem.loadRootDir(dir, Literal.Temp).getAbsolutePath(), new File(dir,
					        Literal.Temp).getAbsolutePath());
					new File(dir, Literal.Temp).delete();
				}
				{
					assertEquals(FileSystem.loadRootDir(null, Literal.Temp).getAbsolutePath(), new File(Literal.Root,
					        Literal.Temp).getAbsolutePath());
				}
			}
			{
				{
					final File file = new File(Literal.info_xml);
					file.createNewFile();
					assertEquals(FileSystem.loadRootDir(file, null).getAbsolutePath(), new File(Literal.Root,
					        Literal.Temp).getAbsolutePath());
					file.delete();
				}
				{
					final File dir = new File("FileSystemTest");
					dir.mkdirs();
					assertEquals(FileSystem.loadRootDir(dir, null).getAbsolutePath(),
					        new File(dir, Literal.Temp).getAbsolutePath());
					new File(dir, Literal.Temp).delete();
					dir.delete();
				}
				{
					final File dir = new File("FileSystemTest");
					dir.delete();
					assertEquals(FileSystem.loadRootDir(dir, null).getAbsolutePath(),
					        new File(dir, Literal.Temp).getAbsolutePath());
					new File(dir, Literal.Temp).delete();
					dir.delete();
				}
				{
					final File dir = new File("test");
					assertEquals(FileSystem.loadRootDir(dir, null).getAbsolutePath(),
					        new File(dir, Literal.Temp).getAbsolutePath());
					new File(dir, Literal.Temp).delete();
				}
				{
					assertEquals(FileSystem.loadRootDir(null, null).getAbsolutePath(), new File(Literal.Root,
					        Literal.Temp).getAbsolutePath());
				}
			}
			
			{
				final File dir = new File("test/frameWork/base/core/fileSystem");
				assertEquals(FileSystem.loadRootDir(dir, "FileSystemTest.java").getAbsolutePath(), new File(dir,
				        Literal.Temp).getAbsolutePath());
				new File(dir, Literal.Temp).delete();
			}
			
		}
		catch (final IOException e1) {
			fail(e1.getMessage());
			
		}
	}
	
	@Test
	public void case6() {
		try {
			assertFalse(FileSystem.Resource.getResource("test").exists());
			FileSystem.Temp.PrintDir.ImageDir.getImagefile("test").delete();
			FileSystem.Temp.PrintDir.PdfDir.getImagefile("test").delete();
			FileSystem.Temp.UploadDir.getUploadfile("test").delete();
			FileSystem.Log.logging("test");
			FileSystem.Log.logging(new Exception("test"));
		}
		catch (final IOException e) {
			fail(e.getMessage());
		}
	}
}
