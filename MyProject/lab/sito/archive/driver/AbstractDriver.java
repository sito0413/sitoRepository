package sito.archive.driver;

import java.io.File;

import sito.archive.StoreException;

public abstract class AbstractDriver implements AutoCloseable {
	
	private final File baseDirectory;
	private final Lock lock;
	private final File tempDir;
	private final Packager packager;
	
	protected AbstractDriver(final File rootDirectory, final String fileName) throws StoreException {
		rootDirectory.mkdirs();
		this.baseDirectory = new File(rootDirectory, fileName + ".dat");
		this.baseDirectory.mkdirs();
		this.lock = new Lock(rootDirectory, fileName);
		this.tempDir = new File(rootDirectory, "temp");
		this.tempDir.mkdirs();
		this.packager = new Packager(this.baseDirectory, rootDirectory, fileName);
		this.packager.unpacking();
	}
	
	protected File getTempDir() {
		return tempDir;
	}
	
	protected File getBaseDirectory() {
		return baseDirectory;
	}
	
	protected void chkConnect(final String domain) throws StoreException {
		if (!lock.isLock()) {
			throw new StoreException("Can Not Operation");
		}
		if ((domain == null) || domain.isEmpty()) {
			throw new StoreException("Can Not Connection");
		}
	}
	
	@Override
	public void close() throws StoreException {
		if (lock.isLock()) {
			packager.packing();
			final File[] files = tempDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
			tempDir.delete();
			lock.unlock();
		}
	}
}