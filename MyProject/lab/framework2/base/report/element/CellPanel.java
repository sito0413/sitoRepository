package framework2.base.report.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;



/**
 * セルパネル<br>
 * 
 * @version 1.0.0
 * @作成者 津田<br>
 * @作成日 2009/06/18
 * @最終更新者 瀬谷<br>
 * @最終更新日 2010/01/20
 */
public class CellPanel {

	/**
	 * コンストラクタ<br>
	 * 
	 * @param width
	 * @param height
	 */
	public CellPanel() {
		super();
		this.initialize();

	}

	/**
	 * 初期化<br>
	 * 
	 * @param width
	 * @param height
	 */
	private void initialize() {
		this.createTextLabel();
		this.setVisible(true);
		this.setKey(null);
		this.setIntMaxRow(0);
	}

	protected void createTextLabel() {
		this.textLabel = new JLabel();
		this.textLabel.setOpaque(false);
		textLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(final MouseEvent mouseevent) {
				textLabel.setOpaque(true);
				textLabel.setBackground(Color.green);
			}

			@Override
			public void mouseExited(final MouseEvent mouseevent) {
				textLabel.setOpaque(false);
				textLabel.setBackground(Color.white);
				textLabel.repaint();
			}
		});
	}

	// --------------------------------------------------

	public void copy(final CellPanel cellPanel) {
		setSize(cellPanel.getWidth(), cellPanel.getHeight());
		setLocation(cellPanel.getX(), cellPanel.getY());
		setVisible(cellPanel.isVisible());
		setText(cellPanel.getText());
		setHorizontalAlignment(cellPanel.getHorizontalAlignment());
		setVerticalAlignment(cellPanel.getVerticalAlignment());
		setFont(cellPanel.getFont());
		setRowIndex(cellPanel.getRowIndex());
		setColumnIndex(cellPanel.getColumnIndex());
		setIntMaxRow(cellPanel.getIntMaxRow());
		if (cellPanel.getKey() != null) {
			setKey(cellPanel.getKey());
		}
		if (cellPanel.getBorder() != null) {
			setBorderLabel(new BorderLabel());
		}
		borderInfos = cellPanel.getBorderInfos();
		if (borderLabel != null) {
			CellBorder cellBorder = new CellBorder(getBorderInfos());
			this.textLabel.setBorder(cellBorder);
		}
		ajastLayout();
	}

	/** テキストラベル */
	JLabel textLabel;

	/**
	 * テキストラベルを取得します<br>
	 * 
	 * @return
	 */
	@Deprecated
	public JLabel getObjTextLabel() {
		return this.textLabel;
	}

	// --------------------------------------------------

	/**
	 * テキストを取得します<br>
	 * 
	 * @return
	 */
	public String getText() {
		return this.getObjTextLabel().getText();
	}

	/*
	 * テキストを設定します<br>
	 */
	public void setText(final String text) {
		this.getObjTextLabel().setText(text);
	}

	// --------------------------------------------------

	/**
	 * テキストの横配置を取得します<br>
	 * 
	 * @return SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.RIGHT,
	 *         SwingConstants.LEADING or SwingConstants.TRAILING
	 */
	public int getHorizontalAlignment() {
		return this.getObjTextLabel().getHorizontalAlignment();
	}

	/**
	 * テキストの横配置を設定します<br>
	 * 
	 * @param alignment
	 *            SwingConstants.LEFT, SwingConstants.CENTER,
	 *            SwingConstants.RIGHT, SwingConstants.LEADING or
	 *            SwingConstants.TRAILING
	 */
	public void setHorizontalAlignment(final int alignment) {
		if (!isHorizontalAlignment(alignment)) {
			return;
		}
		this.getObjTextLabel().setHorizontalAlignment(alignment);
	}

	/**
	 * テキストの横配置の指定が正しいか判定します<br>
	 * 
	 * @param alignment
	 * @return
	 */
	private static boolean isHorizontalAlignment(final int alignment) {
		return (alignment == SwingConstants.LEFT) || (alignment == SwingConstants.CENTER) || (alignment == SwingConstants.RIGHT)
		        || (alignment == SwingConstants.LEADING) || (alignment == SwingConstants.TRAILING);
	}

	// --------------------------------------------------

	/**
	 * テキストの縦配置を取得します<br>
	 * 
	 * @return SwingConstants.TOP, SwingConstants.CENTER or
	 *         SwingConstants.BOTTOM
	 */
	public int getVerticalAlignment() {
		return this.getObjTextLabel().getVerticalAlignment();
	}

	/**
	 * テキストの縦配置を設定します<br>
	 * 
	 * @param alignment
	 *            SwingConstants.TOP, SwingConstants.CENTER or
	 *            SwingConstants.BOTTOM
	 */
	public void setVerticalAlignment(final int alignment) {
		if (!isVerticalAlignment(alignment)) {
			return;
		}
		this.getObjTextLabel().setVerticalAlignment(alignment);
	}

	/**
	 * テキストの縦配置の指定が正しいか判定します<br>
	 * 
	 * @param alignment
	 * @return
	 */
	private static boolean isVerticalAlignment(final int alignment) {
		return (alignment == SwingConstants.TOP) || (alignment == SwingConstants.CENTER) || (alignment == SwingConstants.BOTTOM);
	}

	// --------------------------------------------------

	/** 変数領域行数 */
	private int rowsCount;

	/**
	 * 最大行数を取得します<br>
	 * 
	 * @return
	 */
	public int getIntMaxRow() {
		return this.rowsCount;
	}

	/**
	 * 最大行数をセットします<br>
	 * 
	 * @param maxRow
	 */
	public void setIntMaxRow(final int maxRow) {
		this.rowsCount = maxRow;
	}

	// --------------------------------------------------

	/** 行インデックス */
	private int rowIndex;

	/**
	 * 行インデックスを取得します<br>
	 * 行インデックスは 1 から始まります<br>
	 * 
	 * @return
	 */
	public int getRowIndex() {
		return this.rowIndex;
	}

	/**
	 * 行インデックスを設定します<br>
	 * 行インデックスは 1 から始まります<br>
	 * 
	 * @param rowIndex
	 */
	public void setRowIndex(final int rowIndex) {
		this.rowIndex = rowIndex;
	}

	// --------------------------------------------------

	/** 列インデックス */
	private int columnIndex;

	/**
	 * 列インデックスを取得します<br>
	 * 列インデックスは 1 から始まります<br>
	 * 
	 * @return
	 */
	public int getColumnIndex() {
		return this.columnIndex;
	}

	/**
	 * 列インデックスを設定します<br>
	 * 列インデックスは 1 から始まります<br>
	 * 
	 * @param columnIndex
	 */
	public void setColumnIndex(final int columnIndex) {
		this.columnIndex = columnIndex;
	}

	// --------------------------------------------------

	/** 変数キー */
	private String key;

	/**
	 * 変数キーを取得します<br>
	 * 
	 * @return
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 変数キーを設定します<br>
	 * 
	 * @param key
	 */
	public void setKey(final String key) {
		this.key = key;
	}

	// --------------------------------------------------
	private boolean visible;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(final boolean flag) {
		visible = flag;
	}

	// --------------------------------------------------
	/*
	 * フォントを取得します<br>
	 */
	public Font getFont() {
		return this.getObjTextLabel().getFont();
	}

	/*
	 * フォントを設定します<br>
	 */
	public void setFont(final Font font) {
		if (font == null) {
			return;
		}
		this.getObjTextLabel().setFont(font);
	}

	// --------------------------------------------------

	int width;
	int height;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Dimension getSize() {
		return new Dimension(width, height);
	}

	public void setSize(final int width, final int height) {
		this.width = width;
		this.height = height;
		ajastLayout();
	}

	// --------------------------------------------------

	int x;
	int y;

	public void setLocation(final int x, final int y) {
		this.x = x;
		this.y = y;
		ajastLayout();
	}

	public Point getLocation() {
		return new Point(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// --------------------------------------------------
	protected void ajastLayout() {
		double borderLabelWidth = width + LineWeight.THICK.toInteger() * 2;
		double borderLabelHeight = height + LineWeight.THICK.toInteger() * 2;
		double borderLabelX = x - LineWeight.THICK.toInteger();
		double borderLabelY = y - LineWeight.THICK.toInteger();
		if (borderLabel != null) {
			this.borderLabel.setSize((int) borderLabelWidth, (int) borderLabelHeight);
			this.borderLabel.setLocation((int) borderLabelX, (int) borderLabelY);
		}
		if (textLabel != null) {
			this.textLabel.setVisible(isVisible());
			this.textLabel.setSize(width/*- 2*/, height /*- 2*/);
			this.textLabel.setLocation(x /* + 1 */, y /* + 1 */);
		}
	}

	// --------------------------------------------------

	BorderLabel borderLabel;

	@Deprecated
	public BorderLabel getBorder() {
		return borderLabel;
	}

	@Deprecated
	public void setBorderLabel(final BorderLabel borderLabel) {
		this.borderLabel = borderLabel;
		this.borderLabel.setAncher(this);
	}

	/**
	 * ボーダ情報を設定します<br>
	 * 
	 * @param position
	 * @param lineStyle
	 * @param weight
	 */
	public void setBorder(final PositionType position, final LineStyleType lineStyle, final float weight) {
		if (borderLabel == null) {
			borderLabel = new BorderLabel();
			borderLabel.setAncher(this);
			ajastLayout();
		}
		this.getBorderInfos().add(new BorderInfo(lineStyle, weight, position));
	}

	// --------------------------------------------------
	/** ボーダ情報 */
	private List<BorderInfo> borderInfos;

	/**
	 * ボーダ情報を取得します<br>
	 * 
	 * @return
	 */
	protected List<BorderInfo> getBorderInfos() {
		if (this.borderInfos == null) {
			this.borderInfos = new CopyOnWriteArrayList<BorderInfo>();
			if (borderLabel != null) {
				CellBorder cellBorder = new CellBorder(getBorderInfos());
				this.textLabel.setBorder(cellBorder);
			}
		}
		return this.borderInfos;
	}

	// --------------------------------------------------
	/** ボーダ情報 */
	protected static class BorderInfo {
		/**
		 * コンストラクタ<br>
		 * 
		 * @param style
		 * @param weight
		 */
		public BorderInfo(final LineStyleType style, final float weight, final PositionType position) {
			this.setStyle(style);
			this.setWeight(weight);
			this.setPosition(position);
		}

		// --------------------------------------------------

		/** 線種 */
		private LineStyleType style;

		/**
		 * 線種を取得します<br>
		 * 
		 * @return
		 */
		public LineStyleType getStyle() {
			return this.style;
		}

		/**
		 * 線種を指定します<br>
		 * 
		 * @param style
		 */
		public void setStyle(final LineStyleType style) {
			this.style = style;
		}

		// --------------------------------------------------

		/** 太さ */
		private float weight;

		/**
		 * 太さを取得します<br>
		 * 
		 * @return
		 */
		public float getWeight() {
			return this.weight;
		}

		/**
		 * 太さを設定します<br>
		 * 
		 * @param weight
		 */
		public void setWeight(final float weight) {
			this.weight = weight;
		}

		// --------------------------------------------------
		PositionType position;

		public PositionType getPosition() {
			return position;
		}

		public void setPosition(final PositionType position) {
			this.position = position;
		}
	}

}
