package frameWork.architect.build.compileInstaller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.base.core.fileSystem.FileUtil;

public class Archive extends Task {
	private static final SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy/MM/dd/HHmm");
	
	public static void main(final String[] args) throws Exception {
		new Archive().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		new File(build.jarDir, fileFormat.format(new Date(build.currentTimeMillis))).mkdirs();
		final File jarFile = new File(build.installerJarPath);
		FileUtil.copy(jarFile, new File(build.jarDir, fileFormat.format(new Date(build.currentTimeMillis)) + "/"
		        + jarFile.getName()));
	}
}
