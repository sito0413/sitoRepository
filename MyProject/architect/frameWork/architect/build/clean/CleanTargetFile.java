package frameWork.architect.build.clean;

import java.io.File;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.base.core.fileSystem.FileUtil;

public class CleanTargetFile extends Task {
	public static void main(final String[] args) throws Exception {
		new CleanTargetFile().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		for (final String target : build.clearTarget) {
			FileUtil.delete(new File(target));
		}
	}
}
