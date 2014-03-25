package frameWork.compressor;

public interface Utils {
	static final int ML_BITS = 4;
	static final int MIN_MATCH = 4;
	static final int RUN_BITS = 8 - ML_BITS;
	static final int RUN_MASK = (1 << RUN_BITS) - 1;
	static final int COPY_LENGTH = 8;
	static final int ML_MASK = (1 << ML_BITS) - 1;
}
