package frameWork.architect.build.compileFramework;

import java.io.File;

import frameWork.architect.build.AntUtil;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;

public class Jar extends Task {
	public static void main(final String[] args) throws Exception {
		new Jar().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		new File(build.targetCompileFrameworkDir).mkdirs();
		AntUtil.jar(build.jdkDir, new File(build.jarFrameworkBuildFile), build.frameworkJarPath, "",
		        build.targetCompileFrameworkDir, build.srcDir);
		
	}
	
}
