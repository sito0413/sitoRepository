package framework2.base.report.preview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import framework2.base.report.util.ReportException;


/**
 * 帳票ビュー<br>
 */
@SuppressWarnings("nls")
public class Preview extends JDialog {

	/** 帳票コントロール */
	private final ReportPreview control; // @jve:decl-index=0:

	private JButton previousButton;
	private JButton nextButton;
	private JPanel centerPanel;

	private JLabel label = null;

	/**
	 * コンストラクタ
	 * 
	 * @param control
	 */
	public Preview(final ReportPreview control) {
		super();
		this.control = control;
		initialize();
		setScale(1);
		refreshView();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		JPanel topPanell = new JPanel();
		topPanell.setLayout(null);
		topPanell.add(getNextButton(), null);
		topPanell.add(getPreviousButton(), null);
		topPanell.add(getLabel(), null);
		topPanell.setPreferredSize(new Dimension(300, 45));

		JButton zoomButton = new JButton();
		zoomButton.setBounds(new Rectangle(10, 10, 90, 25));
		zoomButton.setText("ズーム");
		zoomButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent e) {
				setScale(((getControl().getScale() == 1) ? 1.5 : 1));
			}
		});

		JButton printButton = new JButton();
		printButton.setText("印刷");
		printButton.setBounds(new Rectangle(110, 10, 90, 25));
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					getControl().printAction();
					exit();
				}
				catch (ReportException exp) {
					exp.printStackTrace();
				}
			}
		});

		JPanel topPanelc = new JPanel();
		topPanelc.setLayout(null);
		topPanelc.add(zoomButton, null);
		topPanelc.add(printButton, null);

		JButton exitButton = new JButton();
		exitButton.setText("閉じる");
		exitButton.setBounds(new Rectangle(0, 10, 90, 25));
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				exit();
			}

		});
		JPanel topPanelr = new JPanel();
		topPanelr.setLayout(null);
		topPanelr.add(exitButton, null);
		topPanelr.setPreferredSize(new Dimension(100, 45));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(1, 45));
		topPanel.add(topPanell, BorderLayout.WEST);
		topPanel.add(topPanelr, BorderLayout.EAST);
		topPanel.add(topPanelc, BorderLayout.CENTER);

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		flowLayout.setHgap(20);
		flowLayout.setVgap(20);
		JPanel backGraundPanel = new JPanel();
		backGraundPanel.setLayout(flowLayout);
		backGraundPanel.setBackground(new Color(144, 153, 174));
		backGraundPanel.add(getCenterPanel(), null);

		JScrollPane backGraundScrollPane = new JScrollPane();
		backGraundScrollPane.setViewportView(backGraundPanel);

		JPanel reportContentPane = new JPanel();
		reportContentPane.setLayout(new BorderLayout());
		reportContentPane.add(topPanel, BorderLayout.NORTH);
		reportContentPane.add(backGraundScrollPane, BorderLayout.CENTER);

		this.setModal(true);
		this.setSize(new Dimension(1024, 720));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("印刷プレビュー");
		this.setContentPane(reportContentPane);

	}

	private JButton getNextButton() {
		if (nextButton == null) {
			nextButton = new JButton();
			nextButton.setText("次ページ");
			nextButton.setBounds(new Rectangle(210, 10, 90, 25));
			nextButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					getControl().nextPage();
					refreshView();
				}
			});
		}
		return nextButton;
	}

	private JButton getPreviousButton() {
		if (previousButton == null) {
			previousButton = new JButton();
			previousButton.setText("前ページ");
			previousButton.setBounds(new Rectangle(110, 10, 90, 25));
			previousButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					getControl().previousPage();
					// プレビュー更新.
					refreshView();
				}
			});
		}
		return previousButton;
	}

	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel() {

				@Override
				public Component add(final Component comp) {
					super.add(comp, BorderLayout.CENTER);
					return comp;
				}

				@Override
				public void paint(final Graphics g) {
					double localScale = getControl().getScale();
					((Graphics2D) g).scale(localScale, localScale);
					super.paint(g);
				}

				@Override
				public Dimension getSize() {
					double localScale = getControl().getScale();
					Dimension size = super.getSize();
					return new Dimension((int) (size.width * localScale), (int) (size.height * localScale));

				}

				@Override
				public Rectangle getBounds() {
					double localScale = getControl().getScale();
					Rectangle bounds = super.getBounds();
					return new Rectangle(bounds.x, bounds.y, (int) (bounds.width * localScale),
					        (int) (bounds.height * localScale));
				}

				@Override
				public Dimension getPreferredSize() {
					double localScale = getControl().getScale();
					Dimension preferredSize = super.getPreferredSize();
					return new Dimension((int) (preferredSize.width * localScale), (int) (preferredSize.height * localScale));
				}
			};
			centerPanel.setLayout(new BorderLayout());
		}
		return centerPanel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setBounds(new Rectangle(10, 10, 90, 25));
		}
		return label;
	}

	void setScale(final double scale) {
		this.getControl().setScale(scale);
		this.getCenterPanel().revalidate();
		this.repaint();
	}

	ReportPreview getControl() {
		return this.control;
	}

	void refreshView() {
		boolean prevPage = false;
		boolean nextPage = false;
		// ページが存在することが各ボタンの使用の前提条件.
		if (getControl().getPageIndex() >= 0) {
			// 印刷ボタン.
			// 前ページボタン.
			prevPage = (getControl().getPageIndex() > 0);
			// 次ページボタン.
			nextPage = (getControl().getPageIndex() < getControl().getMaxPageIndex());
		}
		// ボタン使用可否変更.
		this.getPreviousButton().setEnabled(prevPage);
		this.getNextButton().setEnabled(nextPage);
		this.getCenterPanel().removeAll();
		this.getLabel().setText((getControl().getPageIndex() + 1) + " / " + (getControl().getMaxPageIndex() + 1));
		if (getControl().getActivePage() != null) {
			this.getCenterPanel().add(getControl().getActivePage());
		}
		this.getCenterPanel().repaint();
	}

	void exit() {
		Preview.this.setVisible(false);
		Preview.this.dispose();
	}

} // @jve:decl-index=0:visual-constraint="10,10"