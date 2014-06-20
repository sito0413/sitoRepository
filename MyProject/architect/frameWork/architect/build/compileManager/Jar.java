package frameWork.architect.build.compileManager;

import java.io.File;

import frameWork.architect.build.AntUtil;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.manager.FrameworkManager;

public class Jar extends Task {
	public static void main(final String[] args) throws Exception {
		new Jar().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		new File(build.targetManagerDir).mkdirs();
		AntUtil.jar(build.jdkDir, new File(build.jarManagerBuildFile), build.managerJarPath,
		        FrameworkManager.class.getCanonicalName(), build.targetManagerDir, build.managerDir);
		
	}
	
}
