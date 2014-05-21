package frameWork.developer.installer;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import frameWork.developer.installer.gui.InstallerGUI;


public class Installer implements Runnable {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[1].getClassName());
		}
		catch (final Exception e1) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (final Exception e2) {
				//NOOP
			}
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new InstallerGUI().setVisible(true);
			}
		});
	}
	
	// private members
	private Progress progress;
	private String installDir;
	private List<String> installComponents;
	
	public void install(final Progress p, final String i, final List<String> c) {
		this.progress = p;
		this.installDir = i;
		this.installComponents = c;
		new Thread(this, "Install thread").start();
	}
	
	@Override
	public void run() {
		progress.setMaximum(installComponents.size() + 1);
		try {
			for (int i = 0; i < installComponents.size(); i++) {
				progress.message("インストール　" + installComponents.get(i));
				System.out.println(installDir + File.separator + installComponents.get(i));
				progress.advance(1);
			}
			progress.message("インストール後　処理");
			OperatingSystem.getOperatingSystem().perform(this, installDir, installComponents, "framework.jar");
			progress.advance(1);
		}
		catch (final IOException io) {
			progress.error(io.toString());
			return;
		}
		progress.done();
	}
	
	public String getAppName() {
		return "appName";
	}
	
	public String getAppVersion() {
		return "1.0.0";
	}
	
	public String getTitle() {
		return getAppName() + " " + getAppVersion() + " インストーラー";
	}
	
	public List<InstallComponent> getInstallComponents() {
		final List<InstallComponent> components = new ArrayList<>();
		final int count = 10;
		
		for (int i = 0; i < count; i++) {
			components.add(new InstallComponent("～" + " (" + 10 + "Kb)", "××××××", 10));
			
		}
		return components;
	}
	
	public String getInstallDirectory() {
		return OperatingSystem.getOperatingSystem().getInstallDirectory(getAppName(), getAppVersion());
	}
	
	public String getReadme1() {
		// TODO 自動生成されたメソッド・スタブ
		return "";
	}
	
	public String getReadme2() {
		// TODO 自動生成されたメソッド・スタブ
		return "";
	}
}
