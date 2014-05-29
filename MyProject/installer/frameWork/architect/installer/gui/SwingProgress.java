package frameWork.architect.installer.gui;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import frameWork.architect.installer.Progress;

class SwingProgress extends JPanel implements Progress {
	private final JProgressBar progress;
	private final Pages pages;
	
	SwingProgress(final Pages pages) {
		super(new BorderLayout());
		this.pages = pages;
		this.progress = new JProgressBar();
		this.progress.setStringPainted(true);
		add(BorderLayout.NORTH, progress);
	}
	
	@Override
	public void setMaximum(final int max) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progress.setMaximum(max);
			}
		});
	}
	
	@Override
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
	
	@Override
	public void done() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pages.next(getRootPane());
			}
		});
	}
	
	@Override
	public void error(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, message, "Installation aborted", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		});
	}
	
	@Override
	public void message(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progress.setString(message);
			}
		});
	}
}