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
import frameWork.architect.jar.Project;

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
	private final List<InstallComponent> components;
	private final List<InstallComponent> libComponents;
	private final List<InstallComponent> clsComponents;
	private final String appName = "フレームワーク";
	private final String directory;
	
	public Installer() {
		this.components = new ArrayList<>();
		this.libComponents = new ArrayList<>();
		this.clsComponents = new ArrayList<>();
		try {
			final URL url = Installer.class.getResource("/frameWork/architect/jar/info.xml");
			if (url != null) {
				final URLConnection connection = url.openConnection();
				if (connection != null) {
					final Properties properties = new Properties();
					properties.loadFromXML(connection.getInputStream());
					final String path = properties.getProperty("Path", "--------------");
					final String[] files = path.replace("\r\n", "\n").replace("\r", "").split("\n");
					for (final String name : files) {
						if (!name.isEmpty()) {
							final String className = "/frameWork/architect/jar/" + name;
							System.out.println(className);
							final URL jar = Installer.class.getResource(className);
							final URLConnection jarConnection = jar.openConnection();
							final byte[] buffer = new byte[1024 * 1024];
							try (InputStream is = jarConnection.getInputStream()) {
								int total = 0;
								int r = -1;
								while ((r = is.read(buffer)) != -1) {
									total += r;
								}
								if (name.startsWith("lib/")) {
									libComponents.add(new InstallComponent(name, className, total));
								}
								else if (name.startsWith("cls/")) {
									clsComponents.add(new InstallComponent(name, className, total));
								}
								else {
									components.add(new InstallComponent(name, className, total));
								}
							}
						}
					}
				}
			}
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}
		directory = new File("").getAbsolutePath();
	}
	
	public void install(final SwingProgress p, final String i) {
		this.progress = p;
		this.installDir = i;
		new Thread(this, "Install thread").start();
	}
	
	@Override
	public void run() {
		try {
			int total = 0;
			for (final InstallComponent component : components) {
				total += component.size;
			}
			for (final InstallComponent component : clsComponents) {
				total += component.size;
			}
			for (final InstallComponent component : libComponents) {
				if (component.name.endsWith(".zip")) {
					total += component.size;
				}
				total += component.size;
			}
			progress.setMaximum(total);
			final File dir = new File(installDir, "cls");
			dir.mkdirs();
			new File(installDir, "lib").mkdirs();
			final File instDir = new File(installDir);
			final File src = new File(installDir + Project.class.getPackage().getName().replace(".", "/"));
			if (!src.exists()) {
				src.mkdirs();
			}
			for (final InstallComponent component : components) {
				if (component.name.endsWith(".java")) {
					installFile(src, component.name, component.label);
				}
				else if (component.name.endsWith(".xml")) {
					continue;
				}
				else {
					installFile(dir, component.name, component.label);
				}
				progress.advance(component.size);
			}
			for (final InstallComponent component : clsComponents) {
				installFile(instDir, component.name, component.label);
				progress.advance(component.size);
			}
			for (final InstallComponent component : libComponents) {
				installFile(instDir, component.name, component.label);
				progress.advance(component.size);
				if (component.name.endsWith(".zip")) {
					unzip(new File(installDir, "jdk"), new File(instDir, component.name), component.size);
				}
			}
			progress.done();
		}
		catch (final IOException io) {
			io.printStackTrace();
			progress.error(io.toString());
			progress.done();
			return;
		}
	}
	
	private void unzip(final File file, final File zip, final int size) throws IOException {
		file.mkdirs();
		progress.error(zip.toString());
		final byte[] buf = new byte[1024];
		try (final ZipFile zf = new ZipFile(zip)) {
			int i = 0;
			for (final Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
				
				i++;
				final ZipEntry entry = e.nextElement();
				progress.error(entry.getName());
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
		zip.delete();
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
	
	public String getTitle() {
		return getAppName() + " インストーラー";
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
