package frameWork.base.print.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.List;

/**
 * セルパネル<br>
 */
public class Cell {
	
	public static String getKey(final int rowIndex, final int columnIndex) {
		return rowIndex + "*" + columnIndex;
	}
	
	private final String LINE_SEPARATOR = "\n";
	
	private String text;
	private HorizontalAlignment horizontalAlignment;
	private VerticalAlignment verticalAlignment;
	private Font font;
	private boolean visible;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int textWidth;
	private int textHeight;
	
	private final int rowIndex;
	private final int columnIndex;
	private final int rowsCount;
	private final String key;
	private final CellBorder borderInfos;
	
	public Cell(final int rowIndex, final int columnIndex, final int x, final int y, final int width, final int height,
	        final int textWidth, final int textHeight, final Font font, final HorizontalAlignment horizontalAlignment,
	        final VerticalAlignment verticalAlignment, final String value, final List<BorderInfo> borderInfos,
	        final double scaleRate) {
		// 行列設定.
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		
		// 配置設定.
		this.width = width;
		this.height = height;
		this.textWidth = textWidth;
		this.textHeight = textHeight;
		this.x = x;
		this.y = y;
		
		// フォント設定.
		if (scaleRate != 1d) {
			float newFontSize = font.getSize2D();
			newFontSize *= scaleRate;
			this.font = font.deriveFont(newFontSize);
		}
		else {
			this.font = font;
		}
		
		// 文字配置設定.
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		
		// 変数キー情報取得.
		// 値設定.
		String k = null;
		int rc = 0;
		if (value != null) {
			if (value.startsWith(VariableConstants.KEY_STRING)) {
				k = value;
				rc = 1;
				final int openIndex = value.indexOf(VariableConstants.LEFT_PARENTHESES);
				final int closeIndex = value.indexOf(VariableConstants.RIGHT_PARENTHESES, openIndex + 1);
				if ((openIndex >= 0) && (closeIndex >= 0)) {
					try {
						rc = Integer.parseInt(value.substring(openIndex + 1, closeIndex));
						k = value.substring(0, openIndex);
					}
					catch (final NumberFormatException exp) {
						exp.printStackTrace();
					}
				}
			}
			this.key = k;
			this.rowsCount = rc;
			this.text = value.replace("\r\n", "\n").replace("\r", "\n");
		}
		else {
			this.key = null;
			this.rowsCount = 0;
			this.text = "";
		}
		
		this.visible = true;
		this.borderInfos = new CellBorder(borderInfos);
	}
	
	// --------------------------------------------------
	/**
	 * テキストを取得します<br>
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * テキストを設定します<br>
	 */
	public void setText(final String text) {
		this.text = text;
	}
	
	// --------------------------------------------------
	
	/**
	 * テキストの横配置を取得します<br>
	 */
	public HorizontalAlignment getHorizontalAlignment() {
		return horizontalAlignment;
	}
	
	/**
	 * テキストの横配置を設定します<br>
	 */
	public void setHorizontalAlignment(final HorizontalAlignment alignment) {
		horizontalAlignment = alignment;
	}
	
	/**
	 * テキストの縦配置を取得します<br>
	 */
	public VerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}
	
	/**
	 * テキストの縦配置を設定します<br>
	 */
	public void setVerticalAlignment(final VerticalAlignment alignment) {
		verticalAlignment = alignment;
	}
	
	/**
	 * 最大行数を取得します<br>
	 */
	public int getRowsCount() {
		return rowsCount;
	}
	
	// --------------------------------------------------
	/**
	 * 行インデックスを取得します<br>
	 */
	public int getRowIndex() {
		return this.rowIndex;
	}
	
	/**
	 * 列インデックスを取得します<br>
	 */
	public int getColumnIndex() {
		return this.columnIndex;
	}
	
	/**
	 * 変数キーを取得します<br>
	 */
	public String getKey() {
		return this.key;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(final boolean flag) {
		visible = flag;
	}
	
	/**
	 * フォントを取得します<br>
	 */
	public Font getFont() {
		return font;
	}
	
	/**
	 * フォントを設定します<br>
	 */
	public void setFont(final Font font) {
		this.font = font;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(final int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(final int height) {
		this.height = height;
	}
	
	public int getTextWidth() {
		return textWidth;
	}
	
	public void setTextWidth(final int textWidth) {
		this.textWidth = textWidth;
	}
	
	public int getTextHeight() {
		return textHeight;
	}
	
	public void setTextHeight(final int textHeight) {
		this.textHeight = textHeight;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(final int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(final int y) {
		this.y = y;
	}
	
	public CellBorder getBorderInfos() {
		return borderInfos;
	}
	
	void paint(final Graphics2D g) {
		if (visible) {
			final int textX = this.x;
			int textY = this.y;
			final float wrappingWidth = textWidth;
			if (!text.isEmpty()) {
				final AttributedString as = new AttributedString(text);
				as.addAttribute(TextAttribute.FONT, font);
				as.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
				as.addAttribute(TextAttribute.BACKGROUND, new Color(0, 0, 0, 0));
				
				final FontRenderContext context = g.getFontRenderContext();
				final LineBreakMeasurer measurer = new LineBreakMeasurer(as.getIterator(), context);
				int position;
				int textYoffset = 1;
				if ((verticalAlignment == VerticalAlignment.CENTER) || (verticalAlignment == VerticalAlignment.BOTTOM)) {
					@SuppressWarnings("hiding")
					int textHeight = 0;
					// 文字列の最後まで
					while ((position = measurer.getPosition()) < text.length()) {
						TextLayout layout;
						final int indexOf = text.indexOf(LINE_SEPARATOR, position);
						if (position < indexOf) {
							layout = measurer.nextLayout(wrappingWidth, indexOf, false);
						}
						else {
							layout = measurer.nextLayout(wrappingWidth);
						}
						
						if (layout == null) {
							break;
						}
						textHeight += (layout.getAscent() + layout.getDescent() + layout.getLeading());
					}
					if (verticalAlignment == VerticalAlignment.CENTER) {
						textYoffset += (this.textHeight - textHeight) / 2;
					}
					else if (verticalAlignment == VerticalAlignment.BOTTOM) {
						textYoffset += (this.textHeight - textHeight);
					}
				}
				textY += textYoffset;
				measurer.setPosition(0);
				while ((position = measurer.getPosition()) < text.length()) {
					TextLayout layout;
					final int indexOf = text.indexOf(LINE_SEPARATOR, position);
					if (position < indexOf) {
						layout = measurer.nextLayout(wrappingWidth, indexOf, false);
					}
					else {
						layout = measurer.nextLayout(wrappingWidth);
					}
					
					if (layout == null) {
						break;
					}
					
					textY += layout.getAscent();
					final int offset = 1;
					if (horizontalAlignment == HorizontalAlignment.CENTER) {
						layout.draw(g, textX + ((wrappingWidth - layout.getAdvance()) / 2), textY);
					}
					else if (horizontalAlignment == HorizontalAlignment.LEFT) {
						layout.draw(g, textX + offset, textY);
					}
					else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
						layout.draw(g, (textX + (wrappingWidth - layout.getAdvance() - offset)), textY);
					}
					else if (horizontalAlignment == HorizontalAlignment.TRAILING) {
						final float dx = layout.isLeftToRight() ? (wrappingWidth - layout.getAdvance() - offset)
						        : offset;
						layout.draw(g, textX + dx, textY);
					}
					else {
						final float dx = layout.isLeftToRight() ? offset
						        : (wrappingWidth - layout.getAdvance() - offset);
						layout.draw(g, textX + dx, textY);
					}
					textY += layout.getDescent() + layout.getLeading();
				}
			}
			this.borderInfos.paintBorder(g, x, y, width, height);
		}
	}
}
