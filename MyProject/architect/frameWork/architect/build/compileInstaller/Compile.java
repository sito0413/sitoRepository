package frameWork.architect.build.compileInstaller;

import java.io.File;
import java.io.FileOutputStream;

import frameWork.architect.build.AntUtil;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;

public class Compile extends Task {
	public static void main(final String[] args) throws Exception {
		new Compile().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		final File compileInstallerBuildFile = new File(build.compileInstallerBuildFile);
		new File(build.targetCompileInstallerDir).mkdirs();
		new File(build.installerJarDir).mkdirs();
		try (FileOutputStream os = new FileOutputStream(compileInstallerBuildFile)) {
			os.write(new StringBuilder(
			        "<?xml version=\"1.0\"?><project default=\"build\"><target name=\"build\"><path id=\"classPath\"><fileset dir=\"")
			        .append(build.libDir).append("\" includes=\"*.jar\"/><fileset dir=\"").append(build.clsDir)
			        .append("\" includes=\"*.jar\"/><fileset dir=\"").append(build.installerJarDir)
			        .append("\" includes=\"*.jar\"/></path>")
			        .append(AntUtil.compile(build.installerDir, build.targetCompileInstallerDir))
			        .append("</target></project>").toString().getBytes());
		}
		AntUtil.invoke(build.jdkDir, compileInstallerBuildFile);
	}
}
