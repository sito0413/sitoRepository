package frameWork.architect.installer;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.UIManager;

import frameWork.architect.installer.gui.InstallerGUI;
import frameWork.architect.installer.gui.SwingProgress;

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
	
	private SwingProgress progress;
	private String installDir;
	private List<InstallComponent> installComponents;
	private final List<InstallComponent> components;
	private final String appName = "フレームワーク";
	private String appVersion;
	private final String directory;
	
	public Installer() {
		this.components = new ArrayList<>();
		try {
			final URL url = Installer.class.getResource("/frameWork/architect/info.xml");
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
			final URL url = Installer.class.getResource("/frameWork/architect/jar/info.xml");
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
						components.add(createInstallComponent(name, true));
					}
				}
			}
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}
		directory = new File("").getAbsolutePath();
	}
	
	private InstallComponent createInstallComponent(final String name, final boolean isSelect) throws IOException {
		final URL jar = Installer.class.getResource("/frameWork/architect/jar/" + name);
		final URLConnection jarConnection = jar.openConnection();
		final byte[] buffer = new byte[1024 * 1024];
		try (InputStream is = jarConnection.getInputStream()) {
			int total = 0;
			int r = -1;
			while ((r = is.read(buffer)) != -1) {
				total += r;
			}
			return new InstallComponent(name + " (" + (total / 1024) + "Kb)", "/frameWork/architect/jar/" + name,
			        total, isSelect);
		}
	}
	
	public void install(final SwingProgress p, final String i, final List<InstallComponent> c) {
		this.progress = p;
		this.installDir = i;
		this.installComponents = c;
		new Thread(this, "Install thread").start();
	}
	
	@Override
	public void run() {
		try {
			installComponents.add(createInstallComponent("framework.jar", false));
			installComponents.add(createInstallComponent("manager.jar", false));
			InstallComponent zip = null;
			{
				final URL jar = Installer.class.getResource("/frameWork/architect/jar/jdk.zip");
				final URLConnection jarConnection = jar.openConnection();
				final byte[] buffer = new byte[1024 * 1024];
				try (InputStream is = jarConnection.getInputStream()) {
					int total = 0;
					int r = -1;
					while ((r = is.read(buffer)) != -1) {
						total += r;
					}
					zip = new InstallComponent("", "/frameWork/architect/jar/jdk.zip", total, false);
				}
			}
			InstallComponent java = null;
			{
				final URL jar = Installer.class.getResource("/frameWork/architect/Project.java");
				final URLConnection jarConnection = jar.openConnection();
				final byte[] buffer = new byte[1024 * 1024];
				try (InputStream is = jarConnection.getInputStream()) {
					int total = 0;
					int r = -1;
					while ((r = is.read(buffer)) != -1) {
						total += r;
					}
					java = new InstallComponent("", "/frameWork/architect/Project.java", total, false);
				}
			}
			
			int total = 0;
			for (final InstallComponent component : installComponents) {
				total += component.size;
			}
			total += zip.size;
			total += zip.size;
			total += java.size;
			progress.setMaximum(total);
			final File dir = new File(installDir + "/WebContent/WEB-INF/lib");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			final File src = new File(installDir + "/src/frameWork/architect");
			if (!src.exists()) {
				src.mkdirs();
			}
			for (final InstallComponent component : installComponents) {
				final String[] fileNames = component.getName().split("/");
				installFile(dir, component.getName(), fileNames[fileNames.length - 1]);
				progress.advance(component.size);
			}
			installFile(src, java.getName(), "Project.java");
			progress.advance(java.size);
			installFile(new File(installDir), zip.getName(), "jdk.zip");
			progress.advance(zip.size);
			unzip(new File(installDir, "jdk"), zip.size);
			
			progress.done();
		}
		catch (final IOException io) {
			io.printStackTrace();
			progress.error(io.toString());
			return;
		}
	}
	
	private void unzip(final File file, final int size) throws IOException {
		file.mkdirs();
		final byte[] buf = new byte[1024];
		try (final ZipFile zf = new ZipFile(new File(file.getParent(), "jdk.zip"))) {
			int i = 0;
			for (final Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
				i++;
				final ZipEntry entry = e.nextElement();
				if (entry.isDirectory()) {
					new File(file, entry.getName()).mkdirs();
					continue;
				}
				try (FileOutputStream fo = new FileOutputStream(new File(file, entry.getName()))) {
					try (final InputStream is = zf.getInputStream(entry)) {
						for (;;) {
							final int len = is.read(buf);
							if (len < 0) {
								break;
							}
							fo.write(buf, 0, len);
						}
					}
				}
				progress.advance((size / zf.size()) * i);
				i = 0;
			}
		}
		new File(file.getParent(), "jdk.zip").delete();
	}
	
	private void installFile(final File dir, final String resource, final String fileName) throws IOException {
		final URL jar = Installer.class.getResource(resource);
		final URLConnection jarConnection = jar.openConnection();
		final byte[] buffer = new byte[1024 * 1024];
		try (InputStream is = jarConnection.getInputStream()) {
			final File file = new File(dir, fileName);
			progress.message("インストール　" + file.getName());
			int r = -1;
			try (FileOutputStream fo = new FileOutputStream(file)) {
				while ((r = is.read(buffer)) != -1) {
					fo.write(buffer, 0, r);
				}
			}
		}
		
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
		return directory;
	}
	
	public String getReadme() {
		return getAppName() + "のインストールが完了しました。";
	}
}
