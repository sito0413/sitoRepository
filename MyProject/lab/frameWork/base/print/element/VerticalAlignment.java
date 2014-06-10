package frameWork.base.print.element;

import javax.swing.SwingConstants;

public enum VerticalAlignment {
	TOP, CENTER, BOTTOM;
	public static VerticalAlignment DefaultValue = TOP;
	
	public static VerticalAlignment parse(final int verticalAlignment) {
		switch ( verticalAlignment ) {
			case SwingConstants.TOP :
				return TOP;
			case SwingConstants.CENTER :
				return CENTER;
			case SwingConstants.BOTTOM :
				return BOTTOM;
			default :
				break;
		}
		return null;
	}
}
