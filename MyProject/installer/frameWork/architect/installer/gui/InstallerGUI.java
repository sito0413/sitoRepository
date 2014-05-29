package frameWork.architect.installer.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frameWork.architect.installer.Installer;

public class InstallerGUI extends JFrame {
	public InstallerGUI() {
		final Installer installer = new Installer();
		pages = new Pages(this, installer, 450, 250);
		setTitle(installer.getTitle());
		caption = new JLabel();
		caption.setFont(new Font("SansSerif", Font.BOLD, 18));
		cancelButton = new JButton("キャンセル");
		cancelButton.setRequestFocusEnabled(false);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				System.exit(0);
			}
		});
		prevButton = new JButton("戻る");
		prevButton.setRequestFocusEnabled(false);
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				pages.prev(getRootPane());
			}
		});
		nextButton = new JButton("次へ");
		nextButton.setRequestFocusEnabled(false);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				pages.next(getRootPane());
			}
		});
		
		final JPanel content = new JPanel(new WizardLayout(12));
		content.add(caption);
		content.add(cancelButton);
		content.add(prevButton);
		content.add(nextButton);
		for (int i = 0; i < pages.length(); i++) {
			content.add(pages.getComponent(i));
		}
		
		pages.pageChanged(getRootPane());
		
		setContentPane(content);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent evt) {
				System.exit(0);
			}
		});
		
		final Dimension screen = getToolkit().getScreenSize();
		pack();
		setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);
	}
	
	final JLabel caption;
	final JButton cancelButton;
	final JButton prevButton;
	final JButton nextButton;
	private final Pages pages;
	
	private class WizardLayout implements LayoutManager {
		private final int padding;
		
		public WizardLayout(final int padding) {
			this.padding = padding;
		}
		
		@Override
		public void addLayoutComponent(final String name, final Component comp) {
		}
		
		@Override
		public void removeLayoutComponent(final Component comp) {
		}
		
		@Override
		public Dimension preferredLayoutSize(final Container parent) {
			final Dimension dim = new Dimension();
			
			final Dimension captionSize = caption.getPreferredSize();
			dim.width = captionSize.width;
			
			for (int i = 0; i < pages.length(); i++) {
				final Dimension _dim = pages.getComponent(i).getPreferredSize();
				dim.width = Math.max(_dim.width, dim.width);
				dim.height = Math.max(_dim.height, dim.height);
			}
			
			dim.width += padding * 2;
			dim.height += padding * 2;
			dim.height += nextButton.getPreferredSize().height;
			dim.height += captionSize.height;
			return dim;
		}
		
		@Override
		public Dimension minimumLayoutSize(final Container parent) {
			return preferredLayoutSize(parent);
		}
		
		@Override
		public void layoutContainer(final Container parent) {
			final Dimension size = parent.getSize();
			
			final Dimension captionSize = caption.getPreferredSize();
			caption.setBounds(padding, padding, captionSize.width, captionSize.height);
			
			// make all buttons the same size
			final Dimension buttonSize = cancelButton.getPreferredSize();
			buttonSize.width = Math.max(buttonSize.width, prevButton.getPreferredSize().width);
			buttonSize.width = Math.max(buttonSize.width, nextButton.getPreferredSize().width);
			
			// cancel button goes on far left
			cancelButton.setBounds(padding, size.height - buttonSize.height - padding, buttonSize.width,
			        buttonSize.height);
			
			// prev and next buttons are on the right
			prevButton.setBounds(size.width - (buttonSize.width * 2) - (padding / 2) - padding, size.height
			        - buttonSize.height - padding, buttonSize.width, buttonSize.height);
			
			nextButton.setBounds(size.width - buttonSize.width - padding, size.height - buttonSize.height - padding,
			        buttonSize.width, buttonSize.height);
			
			// calculate size for current page
			final Rectangle currentPageBounds = new Rectangle();
			currentPageBounds.x = padding;
			currentPageBounds.y = (padding * 2) + captionSize.height;
			currentPageBounds.width = size.width - currentPageBounds.x - padding;
			currentPageBounds.height = size.height - buttonSize.height - currentPageBounds.y - (padding * 2);
			
			pages.layoutContainer(currentPageBounds);
		}
	}
}
