package frameWork.architect.build.compileInstaller;

import java.io.File;

import frameWork.architect.build.AntUtil;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.architect.installer.Installer;

public class Jar extends Task {
	public static void main(final String[] args) throws Exception {
		new Jar().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		new File(build.jarDir).mkdirs();
		new File(build.targetCompileInstallerDir).mkdirs();
		AntUtil.jar(build.jdkDir, new File(build.jarInstallerBuildFile), build.installerJarPath,
		        Installer.class.getCanonicalName(), build.targetCompileInstallerDir, build.installerDir);
	}
}
