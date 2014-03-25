package frameWork.compressor;

public class LZ4Exception extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	LZ4Exception(final String msg) {
		super(msg);
	}
	
	LZ4Exception() {
		super();
	}
	
}
