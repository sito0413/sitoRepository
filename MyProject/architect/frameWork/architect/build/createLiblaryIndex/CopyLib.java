package frameWork.architect.build.createLiblaryIndex;

import java.io.File;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.base.core.fileSystem.FileUtil;

public class CopyLib extends Task {
	public static void main(final String[] args) throws Exception {
		new CopyLib().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		FileUtil.copy(new File(build.libDir), new File(build.installerJarLibDir));
		FileUtil.copy(new File(build.clsDir), new File(build.installerJarClsDir));
	}
}
