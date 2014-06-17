package frameWork.architect.installer.gui;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class SwingProgress extends JPanel {
	private final JProgressBar progress;
	private final Pages pages;
	
	SwingProgress(final Pages pages) {
		super(new BorderLayout());
		this.pages = pages;
		this.progress = new JProgressBar();
		this.progress.setStringPainted(true);
		add(BorderLayout.NORTH, progress);
	}
	
	public void setMaximum(final int max) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progress.setMaximum(max);
			}
		});
	}
	
	public void advance(final int value) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					progress.setValue(progress.getValue() + value);
				}
			});
			Thread.yield();
		}
		catch (final Exception e) {
		}
	}
	
	public void done() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pages.next(getRootPane());
			}
		});
	}
	
	public void error(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, message, "Installation aborted", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		});
	}
	
	public void message(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progress.setString(message);
			}
		});
	}
}