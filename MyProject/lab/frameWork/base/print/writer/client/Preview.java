package frameWork.base.print.writer.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import frameWork.base.print.ReportException;
import frameWork.base.print.element.Page;
import frameWork.base.print.writer.Writer;

public class Preview extends JDialog {
	private int pageIndex;
	private final Writer writer;
	
	private JButton previousButton;
	private JButton nextButton;
	private final JPanel centerPanel;
	
	private JLabel label;
	private JLabel lblNewLabel;
	
	public Preview(final Writer writer) {
		super();
		this.pageIndex = 0;
		this.writer = writer;// ビュー生成.
		centerPanel = new JPanel() {
			@Override
			protected void paintComponent(final Graphics g) {
				writer.get(pageIndex).paint((Graphics2D) g);
			}
			
			@Override
			protected void paintChildren(final Graphics g) {
				// NOOP
			}
			
			@Override
			protected void paintBorder(final Graphics g) {
				// NOOP
			}
		};
		initialize();
		setScale(1);
		refreshView();
	}
	
	private void initialize() {
		final JPanel topPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(20);
		flowLayout.setVgap(20);
		final JPanel backGraundPanel = new JPanel();
		backGraundPanel.setLayout(flowLayout);
		backGraundPanel.add(centerPanel, null);
		
		final JScrollPane backGraundScrollPane = new JScrollPane();
		backGraundScrollPane.setViewportView(backGraundPanel);
		
		final JPanel reportContentPane = new JPanel();
		reportContentPane.setLayout(new BorderLayout());
		reportContentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		final JPanel panel_2 = new JPanel();
		topPanel.add(panel_2, BorderLayout.EAST);
		
		final JButton printButton = new JButton();
		panel_2.add(printButton);
		printButton.setText("印刷");
		
		final JButton exitButton = new JButton();
		panel_2.add(exitButton);
		exitButton.setText("閉じる");
		label = new JLabel();
		topPanel.add(label, BorderLayout.WEST);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						exit();
					}
				});
			}
			
		});
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							writer.print();
							exit();
						}
						catch (final ReportException e1) {
							JOptionPane.showMessageDialog(Preview.this, "印刷に失敗しました");
						}
					}
				});
			}
		});
		
		reportContentPane.add(backGraundScrollPane, BorderLayout.CENTER);
		
		this.setModal(true);
		this.setSize(new Dimension(1024, 720));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("印刷プレビュー");
		this.setContentPane(reportContentPane);
		final JPanel topPanelr = new JPanel();
		reportContentPane.add(topPanelr, BorderLayout.SOUTH);
		topPanelr.setLayout(new BorderLayout(0, 0));
		
		final JPanel panel = new JPanel();
		final FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		topPanelr.add(panel);
		
		final JSlider slider = new JSlider();
		slider.setValue(10);
		slider.setMinimum(1);
		slider.setMaximum(100);
		slider.setFocusable(false);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setScale(slider.getValue() / 10d);
						centerPanel.revalidate();
					}
				});
			}
		});
		panel.add(slider);
		
		lblNewLabel = new JLabel();
		panel.add(lblNewLabel);
		
		previousButton = new JButton();
		previousButton.setPreferredSize(new Dimension(30, 12));
		previousButton.setMaximumSize(new Dimension(30, 12));
		previousButton.setMinimumSize(new Dimension(30, 12));
		previousButton.setMargin(new Insets(0, 0, 0, 0));
		previousButton.setText("<");
		previousButton.setFocusable(false);
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setPageIndex(pageIndex - 1);
						refreshView();
					}
				});
			}
		});
		reportContentPane.add(previousButton, BorderLayout.WEST);
		
		nextButton = new JButton();
		nextButton.setPreferredSize(new Dimension(30, 12));
		nextButton.setMinimumSize(new Dimension(30, 12));
		nextButton.setMaximumSize(new Dimension(30, 12));
		nextButton.setMargin(new Insets(0, 0, 0, 0));
		nextButton.setText(">");
		nextButton.setFocusable(false);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setPageIndex(pageIndex + 1);
						refreshView();
					}
				});
			}
		});
		reportContentPane.add(nextButton, BorderLayout.EAST);
	}
	
	void refreshView() {
		this.previousButton.setEnabled((pageIndex > 0));
		this.nextButton.setEnabled((pageIndex < (this.writer.size() - 1)));
		this.centerPanel.removeAll();
		this.label.setText((pageIndex + 1) + " / " + this.writer.size());
		this.centerPanel.setPreferredSize(new Dimension(writer.get(pageIndex).getWidth(), writer.get(pageIndex)
		        .getHeight()));
		this.revalidate();
		this.repaint();
	}
	
	void exit() {
		Preview.this.setVisible(false);
		Preview.this.dispose();
	}
	
	private void setPageIndex(final int index) {
		if (index >= 0) {
			this.pageIndex = (index > (this.writer.size() - 1) ? (this.writer.size() - 1) : index);
		}
	}
	
	private void setScale(final double scale) {
		for (final Page page : this.writer) {
			page.setScale(scale);
		}
		lblNewLabel.setText((int) (scale * 100) + " %");
		refreshView();
	}
	
}