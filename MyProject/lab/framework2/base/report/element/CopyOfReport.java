package framework2.base.report.element;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import framework2.base.report.Page;
import framework2.base.report.ReportP;


public class CopyOfReport extends Page {
	private static class FakeCellPanel extends CellPanel {
		final String id;
		final String key;
		final int offset;

		public FakeCellPanel(final String key, final int offset) {
			super();
			this.key = key;
			this.offset = offset;
			id = key + '/' + offset;
		}

		public int getOffset() {
			return offset;
		}

		public String getAncherKey() {
			return key;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof FakeCellPanel) {
				return id.equals(((FakeCellPanel) obj).id);
			}
			return false;
		}

		@Override
		protected void createTextLabel() {
			// NOP
		}

		// --------------------------------------------------
		@SuppressWarnings("deprecation")
		@Override
		public JLabel getObjTextLabel() {
			return null;
		}

		// --------------------------------------------------
		String text;

		/**
		 * テキストを取得します<br>
		 * 
		 * @return
		 */
		@Override
		public String getText() {
			return text;
		}

		/*
		 * テキストを設定します<br>
		 */
		@Override
		public void setText(final String text) {
			this.text = text;
		}

		// --------------------------------------------------
		int horizontalAlignment;

		@Override
		public int getHorizontalAlignment() {
			return horizontalAlignment;
		}

		@Override
		public void setHorizontalAlignment(final int alignment) {
			horizontalAlignment = alignment;
		}

		// --------------------------------------------------

		int verticalAlignment;

		@Override
		public int getVerticalAlignment() {
			return verticalAlignment;
		}

		@Override
		public void setVerticalAlignment(final int alignment) {
			verticalAlignment = alignment;
		}

		// --------------------------------------------------
		Font font;

		/*
		 * フォントを取得します<br>
		 */
		@Override
		public Font getFont() {
			return font;
		}

		/*
		 * フォントを設定します<br>
		 */
		@Override
		public void setFont(final Font font) {
			this.font = font;
		}

		// --------------------------------------------------
		@Override
		protected void ajastLayout() {
			// NOP
		}

		// --------------------------------------------------

		@SuppressWarnings("deprecation")
		@Override
		public BorderLabel getBorder() {
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void setBorderLabel(final BorderLabel borderLabel) {// NOP
		}

		@Override
		public void setBorder(final PositionType position, final LineStyleType lineStyle, final float weight) {
			this.getBorderInfos().add(new BorderInfo(lineStyle, weight, position));
		}

	}

	ReportP reportObject;
	List<FakeCellPanel> fakeCellPanelList;
	ReportP cloneReportObject;

	public CopyOfReport(final ReportP reportObject) {
		super(reportObject.getWidth(), reportObject.getHeight());
		this.reportObject = reportObject;
		fakeCellPanelList = new CopyOnWriteArrayList<FakeCellPanel>();
		centerNWPanelComponentMap = new ConcurrentHashMap<Component, Integer>();
		addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(final AncestorEvent event) {
				cloneReportObject = createCloneReportObject(CopyOfReport.this.reportObject);
				CopyOfReport.this.add(cloneReportObject, BorderLayout.CENTER);
			}

			@Override
			public void ancestorMoved(final AncestorEvent event) {
				// NOP
			}

			@Override
			public void ancestorRemoved(final AncestorEvent event) {
				CopyOfReport.this.remove(cloneReportObject);
				cloneReportObject = null;
			}
		});
		removeAll();
	}

	// --------------------------------------------------

	/** 中央パネル */
	private JPanel centerNWPanel;
	final Map<Component, Integer> centerNWPanelComponentMap;

	@Override
	public JPanel getCenterNWPanel() {
		if (this.centerNWPanel == null) {
			this.centerNWPanel = new JPanel() {
				@Override
				public Component add(final Component comp) {
					if (centerNWPanelComponentMap != null) {
						centerNWPanelComponentMap.put(comp, -1);
					}
					return super.add(comp);
				}

				@Override
				public void setComponentZOrder(final Component comp, final int index) {
					if (centerNWPanelComponentMap != null) {
						centerNWPanelComponentMap.put(comp, index);
					}
					super.setComponentZOrder(comp, index);
				}
			};
			this.centerNWPanel.setLayout(null);
			this.centerNWPanel.setBackground(Color.WHITE);
		}
		return this.centerNWPanel;
	}

	// --------------------------------------------------

	@Override
	public CellPanel getVariableCell(final String key, final int offset) {
		CellPanel myCellPanel = null;
		int index = fakeCellPanelList.indexOf(new FakeCellPanel(key, offset));
		if (index != -1) {
			myCellPanel = fakeCellPanelList.get(index);
		}
		if (myCellPanel == null) {
			CellPanel orgCellPanel = reportObject.getVariableCell(key, offset);
			if (orgCellPanel == null) {
				return null;
			}
			FakeCellPanel fakeCellPanel = new FakeCellPanel(key, offset);
			fakeCellPanel.copy(orgCellPanel);
			fakeCellPanelList.add(fakeCellPanel);
			myCellPanel = fakeCellPanel;
		}
		return myCellPanel;
	}

	@Override
	public void paint(final Graphics g) {
		if (cloneReportObject == null) {
			ReportP object = createCloneReportObject(reportObject);
			object.ajastLayout();
			this.removeAll();
			this.add(object, BorderLayout.CENTER);
			this.revalidate();
			super.paint(g);
			this.remove(object);
		}
		else {
			super.paint(g);
		}
	}

	// --------------------------------------------------

	ReportP createCloneReportObject(final ReportP orgReportObject) {
		ReportP object = null;
		try {
			object = orgReportObject.clone();
			for (FakeCellPanel fakeCellPanel : fakeCellPanelList) {
				object.getVariableCell(fakeCellPanel.getAncherKey(), fakeCellPanel.getOffset()).copy(fakeCellPanel);
			}
			List<Component> componentList = new CopyOnWriteArrayList<Component>();
			for (Entry<Component, Integer> entry : centerNWPanelComponentMap.entrySet()) {
				object.getCenterNWPanel().add(entry.getKey());
				componentList.add(entry.getKey());
			}
			for (Component component : componentList) {
				if (centerNWPanelComponentMap.get(component) != -1) {
					object.getCenterNWPanel().setComponentZOrder(component, centerNWPanelComponentMap.get(component));
				}
			}
			object.setScale(getScale());
		}
		catch (CloneNotSupportedException exp) {
			exp.printStackTrace();
		}
		return object;
	}

	@Override
	public synchronized void setScale(final double scale) {
		super.setScale(scale);
		if (cloneReportObject != null) {
			cloneReportObject.setScale(scale);
		}
	}
}