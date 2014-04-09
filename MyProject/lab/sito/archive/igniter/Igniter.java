package sito.archive.igniter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sito.archive.Archive;
import sito.archive.StoreException;

public class Igniter {
	public static void main(final String[] args) throws StoreException {
		try {
			File dir;
			if (System.getProperty("user.dir") == null) {
				dir = new File(new File("").getAbsolutePath(), "test");
			}
			else {
				dir = new File(System.getProperty("user.dir"), "test");
			}
			dir.mkdirs();
			final String fileName = Archive.class.getSimpleName() + Archive.ARCHIVE_EXTENSION;
			final File file = new File(dir, fileName);
			if (!file.exists()) {
				long count = 0;
				try (InputStream in = Igniter.class.getResourceAsStream("/archive/" + fileName)) {
					try (final FileOutputStream out = new FileOutputStream(file)) {
						int i = -1;
						while ((i = in.read()) != -1) {
							out.write(i);
							out.flush();
							count++;
						}
					}
				}
				System.out.println(count);
			}
			
			new Archive().ignition(dir);
		}
		catch (final IOException e) {
			throw new StoreException("CAN NOT IGNITION", e);
		}
	}
}
