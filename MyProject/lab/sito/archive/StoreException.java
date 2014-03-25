package sito.archive;

public class StoreException extends Exception {
	public StoreException(final String message) {
		super(message);
	}
	
	public StoreException(final String message, final Exception e) {
		super(message, e);
	}
}