package frameWork.architect.build.compileKicker;

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
		final File compileKickerBuildFile = new File(build.compileKickerBuildFile);
		new File(build.targetCompileKickerDir).mkdirs();
		try (FileOutputStream os = new FileOutputStream(compileKickerBuildFile)) {
			os.write(new StringBuilder(
			        "<?xml version=\"1.0\"?><project default=\"build\"><target name=\"build\"><path id=\"classPath\"></path>")
			        .append(AntUtil.compile(build.kickerDir, build.targetCompileKickerDir))
			        .append("</target></project>").toString().getBytes());
		}
		AntUtil.invoke(build.jdkDir, compileKickerBuildFile);
	}
}
