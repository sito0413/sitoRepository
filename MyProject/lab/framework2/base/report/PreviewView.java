package framework2.base.report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * 帳票ビュー<br>
 */
@SuppressWarnings("nls")
public class PreviewView extends JDialog {

	private double scale;
	int pageIndex;
	final ReportBook reportBook;
	final JPanel centerPanel;

	public PreviewView(final ReportBook reportBook) {
		super();
		this.pageIndex = 0;
		this.reportBook = reportBook;
		centerPanel = new JPanel() {
			@Override
			public Component add(final Component comp) {
				super.add(comp, BorderLayout.CENTER);
				return comp;
			}

			@Override
			public void paint(final Graphics g) {
				double localScale = getScale();
				((Graphics2D) g).scale(localScale, localScale);
				super.paint(g);
			}

			@Override
			public Dimension getSize() {
				double localScale = getScale();
				Dimension size = super.getSize();
				return new Dimension((int) (size.width * localScale),
						(int) (size.height * localScale));

			}

			@Override
			public Rectangle getBounds() {
				double localScale = getScale();
				Rectangle bounds = super.getBounds();
				return new Rectangle(bounds.x, bounds.y,
						(int) (bounds.width * localScale),
						(int) (bounds.height * localScale));
			}

			@Override
			public Dimension getPreferredSize() {
				double localScale = getScale();
				Dimension preferredSize = super.getPreferredSize();
				return new Dimension((int) (preferredSize.width * localScale),
						(int) (preferredSize.height * localScale));
			}
		};
		final JLabel label = new JLabel();

		centerPanel.setLayout(new BorderLayout());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);

		initialize(label);
	}

	private void initialize(final JLabel label) {

		setModal(true);
		setSize(new Dimension(1024, 720));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("印刷プレビュー");
		setContentPane(new JPanel() {
			{
				setLayout(new GridBagLayout());
				setPreferredSize(new Dimension(1, 45));
				add(label, createGridBagConstraints());
				add(new JButton(new AbstractAction("前ページ") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						int nextIndex = pageIndex - 1;
						pageIndex = (nextIndex < 0 ? 0 : nextIndex);
						refreshView(label);
					}
				}), createGridBagConstraints());
				add(new JButton(new AbstractAction("次ページ") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						int nextIndex = pageIndex + 1;
						pageIndex = (nextIndex > getMaxPageIndex() ? getMaxPageIndex()
								: nextIndex);
						refreshView(label);
					}
				}), createGridBagConstraints());
				add(new JButton(new AbstractAction("ズーム") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						setScale(((getScale() == 1) ? 1.5 : 1));
					}
				}), createGridBagConstraints());
				add(new JButton(new AbstractAction("設定") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						reportBook.get(pageIndex).setPageFormat();
						refreshView(label);
					}
				}), createGridBagConstraints());
				add(new JButton(new AbstractAction("印刷") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						reportBook.print();
						setVisible(false);
						dispose();
					}
				}), new GridBagConstraints(GridBagConstraints.RELATIVE,
						GridBagConstraints.RELATIVE, 1, 1, 1, 0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(5, 5, 5, 5), 0, 0));
				add(new JButton(new AbstractAction("閉じる") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						setVisible(false);
						dispose();
					}
				}), new GridBagConstraints(0, 2, 100, 1, 0, 0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(5, 5, 5, 5), 0, 0));
				add(new JScrollPane(new JPanel() {
					{
						setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
						setBackground(new Color(144, 153, 174));
						add(centerPanel, null);
					}
				}), new GridBagConstraints(0, 1, 100, 1, 1.0D, 1.0D,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
			}

			private Object createGridBagConstraints() {
				return new GridBagConstraints(GridBagConstraints.RELATIVE,
						GridBagConstraints.RELATIVE, 1, 1, 0, 0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(5, 5, 5, 0), 0, 0);
			}
		});
		setScale(1);
		refreshView(label);
	}

	void refreshView(final JLabel label) {
		centerPanel.removeAll();
		label.setText((pageIndex + 1) + " / " + (getMaxPageIndex() + 1));
		if (reportBook.get(pageIndex) != null) {
			panel = new JPanel() {
				{
					PageFormat pageFormat = reportBook.get(pageIndex)
							.getPageFormat();
					this.setLayout(null);
					this.setOpaque(true);
					this.setSize((int) pageFormat.getWidth(),
							(int) pageFormat.getHeight());
					this.setPreferredSize(this.getSize());
				}

				@Override
				public void paint(final Graphics g) {
					Graphics2D g2d = (Graphics2D) g;
					if (g2d.getTransform().getScaleX() != getScale()) {
						g2d.scale(getScale(), getScale());
					}
					reportBook.get(pageIndex).paint(g);
				}
			};
			centerPanel.add(panel);
		}
		centerPanel.repaint();
	}

	// --------------------------------------------------

	int getMaxPageIndex() {
		return reportBook.getNumberOfPages() - 1;
	}

	// --------------------------------------------------

	synchronized double getScale() {
		return scale;
	}

	synchronized void setScale(final double scale) {
		this.scale = scale;
		centerPanel.revalidate();
		repaint();
	}

	JPanel panel;

	@Override
	public void revalidate() {
		if (panel != null) {
			PageFormat pageFormat = reportBook.get(pageIndex).getPageFormat();
			panel.setSize((int) pageFormat.getWidth(),
					(int) pageFormat.getHeight());
			panel.setPreferredSize(this.getSize());
			centerPanel.revalidate();
			repaint();
		}
		super.revalidate();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
