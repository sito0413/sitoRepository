package sito.archive;

import java.io.File;

import sito.archive.driver.AbstractDriver;

public class Driver extends AbstractDriver {
	
	public Driver(final File rootDirectory) throws StoreException {
		super(rootDirectory, Archive.class.getSimpleName());
	}
	
	public synchronized Connection connect(final String domain) throws StoreException {
		super.chkConnect(domain);
		return new Connection(getTempDir(), new File(getBaseDirectory(), domain + Archive.DRIVER_EXTENSION));
	}
	
	@Override
	public synchronized void close() throws StoreException {
		super.close();
	}
}