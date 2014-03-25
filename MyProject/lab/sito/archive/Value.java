package sito.archive;

import java.io.File;

public abstract class Value {
	
	public abstract void open(final File dirPath) throws StoreException;
	
	public abstract void exe() throws StoreException;
}