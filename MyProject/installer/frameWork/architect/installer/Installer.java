package frameWork.architect.installer;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.UIManager;

import frameWork.architect.installer.gui.InstallerGUI;
import frameWork.base.core.fileSystem.FileSystem;

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
	
	private Progress progress;
	private String installDir;
	private List<String> installComponents;
	private final List<InstallComponent> components;
	private final String appName = "フレームワーク";
	private String appVersion;
	
	public Installer() {
		this.components = new ArrayList<>();
		try {
			final URL url = FileSystem.class.getResource("/frameWork/architect/info.xml");
			if (url != null) {
				final URLConnection connection = url.openConnection();
				if (connection != null) {
					final Properties properties = new Properties();
					properties.loadFromXML(connection.getInputStream());
					appVersion = (properties.getProperty("Ver", "--------------"));
				}
				else {
					appVersion = ("NOT CONNECT");
				}
			}
			else {
				appVersion = ("NOT BUILD");
			}
		}
		catch (final IOException e1) {
			appVersion = ("ERROR");
		}
		try {
			final URL url = FileSystem.class.getResource("/frameWork/architect/jar/info.xml");
			if (url != null) {
				final URLConnection connection = url.openConnection();
				if (connection != null) {
					final Properties properties = new Properties();
					properties.loadFromXML(connection.getInputStream());
					final String path = properties.getProperty("Path", "--------------");
					final String[] files = path.replace("\r\n", "\n").replace("\r", "").split("\n");
					for (final String file : files) {
						final String[] names = file.split("\\\\");
						final String name = names[names.length - 1];
						final URL jar = FileSystem.class.getResource("/frameWork/architect/jar/" + name);
						final URLConnection jarConnection = jar.openConnection();
						final byte[] buffer = new byte[1024 * 1024];
						try (InputStream is = jarConnection.getInputStream()) {
							int total = 0;
							int r = -1;
							while ((r = is.read(buffer)) != -1) {
								total += r;
							}
							components.add(new InstallComponent(name + " (" + (total / 1024) + "Kb)",
							        "/frameWork/architect/jar/" + name, total));
						}
					}
				}
			}
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void install(final Progress p, final String i, final List<String> c) {
		this.progress = p;
		this.installDir = i;
		this.installComponents = c;
		new Thread(this, "Install thread").start();
	}
	
	@Override
	public void run() {
		progress.setMaximum(installComponents.size() + 3);
		try {
			final File dir = new File(installDir + File.separator + "WebContent" + File.separator + "WEB-INF"
			        + File.separator + "lib");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			for (int i = 0; i < installComponents.size(); i++) {
				final URL jar = FileSystem.class.getResource(installComponents.get(i));
				final URLConnection jarConnection = jar.openConnection();
				final byte[] buffer = new byte[1024 * 1024];
				try (InputStream is = jarConnection.getInputStream()) {
					final String[] fileNames = installComponents.get(i).split("/");
					final File file = new File(dir, fileNames[fileNames.length - 1]);
					progress.message("インストール　" + file.getAbsolutePath());
					int r = -1;
					try (FileOutputStream fo = new FileOutputStream(file)) {
						while ((r = is.read(buffer)) != -1) {
							fo.write(buffer, 0, r);
						}
					}
					progress.advance(1);
				}
			}
			{
				final URL jar = FileSystem.class.getResource("/frameWork/architect/jar/framework.jar");
				final URLConnection jarConnection = jar.openConnection();
				final byte[] buffer = new byte[1024 * 1024];
				try (InputStream is = jarConnection.getInputStream()) {
					final File file = new File(dir, "framework.jar");
					progress.message("インストール　" + file.getAbsolutePath());
					int r = -1;
					try (FileOutputStream fo = new FileOutputStream(file)) {
						while ((r = is.read(buffer)) != -1) {
							fo.write(buffer, 0, r);
						}
					}
					progress.advance(1);
				}
			}
			{
				final URL jar = FileSystem.class.getResource("/frameWork/architect/jar/manager.jar");
				final URLConnection jarConnection = jar.openConnection();
				final byte[] buffer = new byte[1024 * 1024];
				try (InputStream is = jarConnection.getInputStream()) {
					final File file = new File(dir, "manager.jar");
					progress.message("インストール　" + file.getAbsolutePath());
					int r = -1;
					try (FileOutputStream fo = new FileOutputStream(file)) {
						while ((r = is.read(buffer)) != -1) {
							fo.write(buffer, 0, r);
						}
					}
					progress.advance(1);
				}
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
		return appName;
	}
	
	public String getAppVersion() {
		return appVersion;
	}
	
	public String getTitle() {
		return getAppName() + " " + getAppVersion() + " インストーラー";
	}
	
	public List<InstallComponent> getInstallComponents() {
		return components;
	}
	
	public String getInstallDirectory() {
		return new File("").getAbsolutePath();
	}
	
	public String getReadme1() {
		return getAppName() + "のインストールを開始します。";
	}
	
	public String getReadme2() {
		return getAppName() + "のインストールが完了しました。";
	}
}
