package frameWork.architect.build.clean;

import java.io.File;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.base.core.fileSystem.FileUtil;

public class CleanJdkZip extends Task {
	public static void main(final String[] args) throws Exception {
		new CleanJdkZip().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		FileUtil.delete(new File(build.jdkZipPath));
	}
	
}
