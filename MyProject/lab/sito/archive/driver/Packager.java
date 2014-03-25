package sito.archive.driver;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

import sito.archive.Archive;
import sito.archive.StoreException;
import sito.archive.driver.packager.AbstractPackager;


class Packager extends AbstractPackager {
	private final File packagFile;
	private final File baseDirectory;
	
	Packager(final File baseDirectory, final File dir, final String fileName) {
		this.baseDirectory = baseDirectory;
		this.packagFile = new File(dir, fileName + Archive.ARCHIVE_EXTENSION);
	}
	
	void packing() throws StoreException {
		try (final FileChannel dest = FileChannel.open(packagFile.toPath(), Archive.WRITE)) {
			packing(dest, baseDirectory);
		}
		catch (final IOException e) {
			throw new StoreException("Can Not Packing", e);
		}
	}
	
	void unpacking() throws StoreException {
		if (packagFile.exists()) {
			try (final FileChannel src = FileChannel.open(packagFile.toPath(), Archive.READ)) {
				unpacking(baseDirectory, src);
			}
			catch (final Exception e) {
				throw new StoreException("Can Not Unpacking", e);
			}
			packagFile.delete();
		}
	}
}