package frameWork.architect.build.createLiblaryIndex;

import java.io.File;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.base.core.fileSystem.FileUtil;

public class CreateZip extends Task {
	public static void main(final String[] args) throws Exception {
		new CreateZip().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		FileUtil.zip(new File(build.jdkDir), new File(build.jdkZipPath));
	}
	
}
