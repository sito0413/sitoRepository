package frameWork.architect.installer.gui;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JRootPane;

import frameWork.architect.installer.Installer;

class Pages {
	private final InstallerGUI installerGUI;
	private final Installer installer;
	private final Component[] pages;
	private int currentPage;
	private final ChooseDirectory chooseDirectory;
	private final SwingProgress progress;
	
	public Pages(final InstallerGUI installerGUI, final Installer installer, final int w, final int h) {
		this.installerGUI = installerGUI;
		this.installer = installer;
		this.progress = new SwingProgress(this);
		this.chooseDirectory = new ChooseDirectory(this.installerGUI, installer.getInstallDirectory());
		this.chooseDirectory.setName("インストール先を選択してください。");
		this.progress.setName(installer.getAppName() + "をインストール中です。");
		this.pages = new Component[] {
		        chooseDirectory, progress, new EndPanel(installer.getReadme(), w, h)
		};
		
	}
	
	public void pageChanged(final JRootPane rootPane) {
		this.installerGUI.prevButton.setEnabled(!((currentPage == 0) || (currentPage >= (pages.length - 2))));
		this.installerGUI.nextButton.setEnabled(!(currentPage == (pages.length - 2)));
		this.installerGUI.nextButton.setText((currentPage < (pages.length - 3)) ? "進む"
		        : ((currentPage == (pages.length - 3)) ? "インストール" : "完了"));
		this.installerGUI.cancelButton.setEnabled(!(currentPage == (pages.length - 1)));
		this.installerGUI.caption.setText(pages[currentPage].getName());
		if (currentPage == (pages.length - 2)) {
			installer.install(progress, chooseDirectory.getInstallDirectory());
		}
		rootPane.invalidate();
		rootPane.validate();
	}
	
	public void next(final JRootPane rootPane) {
		if (currentPage == (pages.length - 1)) {
			System.exit(0);
		}
		else {
			currentPage++;
			pageChanged(rootPane);
		}
	}
	
	public void prev(final JRootPane rootPane) {
		currentPage--;
		pageChanged(rootPane);
	}
	
	public Component getComponent(final int i) {
		return pages[i];
	}
	
	public int length() {
		return pages.length;
	}
	
	public void layoutContainer(final Rectangle currentPageBounds) {
		for (int i = 0; i < length(); i++) {
			final Component page = getComponent(i);
			page.setBounds(currentPageBounds);
			page.setVisible(i == currentPage);
		}
	}
}