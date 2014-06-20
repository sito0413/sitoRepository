package frameWork.architect.build.createLiblaryIndex;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import frameWork.architect.Literal;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;

public class CreateIndexFile extends Task {
	public static void main(final String[] args) throws Exception {
		new CreateLiblaryIndex().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		final StringBuilder builder = new StringBuilder();
		for (final File file : new File(build.installerJarDir).listFiles()) {
			if (file.isFile()) {
				builder.append(file.getName()).append("\r\n");
			}
		}
		for (final File file : new File(build.installerJarClsDir).listFiles()) {
			builder.append("cls/").append(file.getName()).append("\r\n");
		}
		for (final File file : new File(build.installerJarLibDir).listFiles()) {
			builder.append("lib/").append(file.getName()).append("\r\n");
		}
		final Properties properties = new Properties();
		properties.setProperty(Literal.Path, builder.toString());
		final File file = new File(build.installerJarInfo);
		file.getParentFile().mkdirs();
		try (FileOutputStream os = new FileOutputStream(file)) {
			properties.storeToXML(os, "");
		}
	}
	
}
