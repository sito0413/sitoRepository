package frameWork.architect.build.compileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import frameWork.architect.Literal;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;

public class CreateInfoXml extends Task {
	public static void main(final String[] args) throws Exception {
		new CreateInfoXml().invoke(new BuildTask());
	}
	
	private static final SimpleDateFormat guiFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		final Properties properties = new Properties();
		properties.setProperty(Literal.Ver, guiFormat.format(new Date(build.currentTimeMillis)));
		final File file = new File(build.managerInfo);
		file.getParentFile().mkdirs();
		try (FileOutputStream os = new FileOutputStream(file)) {
			properties.storeToXML(os, "");
		}
	}
}
