package framework2.base.report;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("nls")
public class ReportCell {
	/** エンコードマップ */
	private static final Map<Character, String> encodingMap;
	static {
		encodingMap = new ConcurrentHashMap<>();
		ReportCell.encodingMap.put('\n', "<br>"); // 復帰コード.
		ReportCell.encodingMap.put('\r', ""); // 改行コード.
		ReportCell.encodingMap.put('<', "&lt;"); // 不等号(より小).
		ReportCell.encodingMap.put('>', "&gt;"); // 不等号(より大).
		ReportCell.encodingMap.put('&', "&amp;"); // 復帰コード.
		ReportCell.encodingMap.put('\"', "&quot;"); // 二重引用符.
	}

	private static String convertToHTMLFormat(final String str,
			final int horizontalAlignment) {
		// HTML形式に変換が必要なのは、改行が含まれる場合のみ.
		if ((str == null)
				|| ((str.indexOf('\n') < 0) && (str.indexOf('\r') < 0))) {
			return str;
		}

		// HTML形式文字列生成.
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		switch (horizontalAlignment) {
		case SwingConstants.RIGHT:
			html.append("<p align='right'>");
			break;
		case SwingConstants.CENTER:
			html.append("<p align='center'>");
			break;
		default:
			html.append("<p align='left'>");
		}
		// エンコード.
		for (int i = 0; i < str.length(); i++) {
			if (ReportCell.encodingMap.containsKey(str.charAt(i))) {
				// エンコード対象文字.
				html.append(ReportCell.encodingMap.get(str.charAt(i)));
			}
			else {
				// その他の文字.
				html.append(str.charAt(i));
			}
		}
		//
		html.append("</p>");
		html.append("</html>");
		return html.toString();
	}

	private final ReportTable table;
	private String text;
	private String printText;
	private boolean visible;
	private Rectangle bounds;
	private Rectangle labelBounds;
	private Font font;
	private Color background;
	private Color foreground;
	private int horizontalAlignment;
	private int verticalAlignment;
	private final ReportBorder border;
	private final int rowIndex;
	private final int columnIndex;

	ReportCell(final ReportTable table, final int columnIndex,
			final int rowIndex) {
		// 行列設定.
		this.table = table;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.foreground = Color.BLACK;
		this.visible = true;
		this.bounds = new Rectangle(60 * columnIndex, 25 * rowIndex, 60, 25);
		this.labelBounds = bounds;
		this.horizontalAlignment = SwingConstants.LEADING;
		this.verticalAlignment = SwingConstants.CENTER;
		this.text = "";
		this.printText = "";
		this.background = null;
		this.font = null;
		this.border = new ReportBorder(this);
	}

	// --------------------------------------------------

	public int getRowIndex() {
		return this.rowIndex;
	}

	public int getColumnIndex() {
		return this.columnIndex;
	}

	// --------------------------------------------------
	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
		this.printText = convertToHTMLFormat(text, horizontalAlignment);
		this.table.revalidate();
	}

	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(final int horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		this.printText = convertToHTMLFormat(text, horizontalAlignment);
		this.table.revalidate();
	}

	public int getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(final int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		this.table.revalidate();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(final Font font) {
		this.font = font;
		this.table.revalidate();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(final boolean visible) {
		this.visible = visible;
		this.table.revalidate();
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(final Rectangle bounds) {
		this.bounds = bounds;
		border.validate(bounds);
		table.revalidate();
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(final Color background) {
		this.background = background;
		this.table.revalidate();
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(final Color foreground) {
		this.foreground = foreground;
		this.table.revalidate();
	}

	public ReportBorder getBorder() {
		return border;
	}

	void paint1(final Graphics2D g, final JLabel label) {
		if (visible) {
			label.setOpaque(false);
			label.setBounds(labelBounds);
			label.setHorizontalAlignment(horizontalAlignment);
			label.setVerticalAlignment(verticalAlignment);
			label.setText(printText);
			label.setFont(font == null ? label.getFont() : font);
			if (background != null) {
				label.setOpaque(true);
				label.setBackground(background);
			}
			label.setForeground(foreground);
			label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			label.revalidate();
			if (g.hitClip(labelBounds.x, labelBounds.y, labelBounds.width,
					labelBounds.height)) {
				Graphics cg = g.create(labelBounds.x, labelBounds.y,
						labelBounds.width, labelBounds.height);
				label.paint(cg);
				cg.dispose();
			}
		}
	}

	void paint2(final Graphics2D g) {
		if (visible) {
			border.paint(g);
		}
	}

	void revalidate() {
		Insets insets = border.getInsets();
		labelBounds = new Rectangle(bounds.x + insets.left, bounds.y
				+ insets.top, bounds.width - insets.left - insets.right,
				bounds.height - insets.top - insets.bottom);
		table.revalidate();
	}

}
