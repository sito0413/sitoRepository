package sito.archive;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

import javax.swing.JOptionPane;

public class Archive {
	public static final Charset CHARSET = Charset.forName("utf8");
	public static final StandardOpenOption[] WRITE = {
	        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE
	};
	public static final StandardOpenOption[] READ = {
		StandardOpenOption.READ
	};
	public static final String ARCHIVE_EXTENSION = ".arc";
	public static final String DRIVER_EXTENSION = ".drv";
	public static final String CONNECTION_EXTENSION = ".cnt";
	public static final String LOCK_EXTENSION = ".lck";
	public static final String BUFFER_EXTENSION = ".buf";
	public static final String BUFFER_PREFIX = "arc";
	public static final String TEMP_EXTENSION = ".lck";
	
	public void ignition(final File dir) throws StoreException {
		JOptionPane.showMessageDialog(null, "start");
		long s = System.nanoTime();
		try (Driver driver = new Driver(dir)) {
			final Connection connection = driver.connect("newwave._system");
			connection.change("version", "0.0.1");
			connection.change("entryPoint", "test");
			connection.change("useJava", "jre7");
			{
				final File file = new File("lib");
				if (file.listFiles() != null) {
					for (final File lib : file.listFiles()) {
						connection.change("lib." + lib.getName(), lib);
					}
				}
			}
			{
				final File file = new File("java");
				if (file.listFiles() != null) {
					for (final File java : file.listFiles()) {
						connection.change("java." + java.getName(), java);
					}
				}
			}
		}
		System.out.println((((int) ((1681007593 * 10000.0) / ((System.nanoTime() - s)))) / 100.0) + "%");
		s = System.nanoTime();
		try (Driver driver = new Driver(dir)) {
			final Connection connection = driver.connect("newwave._system");
			final Collection<Value> c = connection.read("java");
			System.out.println(c);
			connection.change("2version2", "0.0.1");
			System.out.println(connection.read("2version2"));
			System.out.println(connection.read("2version2"));
			connection.change("2version2", null);
			System.out.println(connection.read("2version2"));
			System.out.println(connection.read("2version2"));
			connection.change("2version2", "0.0.1");
			System.out.println(connection.read("2version2"));
			for (final Value value : c) {
				System.out.println("@" + value + "@");
				value.open(new File(dir, "sample1"));
			}
		}
		System.out.println(System.nanoTime() - s);
		try (Driver driver = new Driver(dir)) {
			final Connection connection = driver.connect("newwave._system2");
			final Collection<Value> c = connection.read("java");
			System.out.println(c);
			System.out.println(connection.read("2version2"));
			connection.change("2version2", "0.0.1");
			System.out.println(connection.read("2version2"));
			connection.change("2version2", null);
			System.out.println(connection.read("2version2"));
			System.out.println(connection.read("2version2"));
			connection.change("2version2", "0.0.4");
			System.out.println(connection.read("2version2"));
			for (final Value value : c) {
				System.out.println("@" + value + "@");
				value.open(new File(dir, "sample1"));
			}
			System.out.println(connection.read("2versio24"));
			connection.change("2versio24", "テストだよ");
			System.out.println(connection.read("2versio24"));
			connection.change("2version5", "0.0.5");
			System.out.println(connection.read("2version5"));
		}
	}
}
