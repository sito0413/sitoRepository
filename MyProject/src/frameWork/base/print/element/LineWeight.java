package frameWork.base.print.element;

/**
 * 線の太さ<br>
 */
public enum LineWeight {
	
	/** 極細 */
	HAIRLINE(0.5f),
	
	/** 細 */
	THIN(1),
	
	/** 中太 */
	MEDIUM(2),
	
	/** 太 */
	THICK(3);
	
	/** 線の太さ */
	private float weight;
	
	private LineWeight(final float weight) {
		this.weight = weight;
	}
	
	/**
	 * float の値を取得します<br>
	 */
	public float toFloat() {
		return weight;
	}
}
