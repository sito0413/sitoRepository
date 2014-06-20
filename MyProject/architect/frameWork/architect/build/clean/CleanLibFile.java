package frameWork.architect.build.clean;

import java.io.File;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.base.core.fileSystem.FileUtil;

public class CleanLibFile extends Task {
	public static void main(final String[] args) throws Exception {
		new CleanLibFile().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		for (final File file : new File(build.installerJarDir).listFiles()) {
			if (!file.getName().endsWith(".java")) {
				FileUtil.delete(file);
			}
		}
	}
}
