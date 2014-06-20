package frameWork.architect.build.compileFramework.test;

import java.io.File;
import java.io.FileOutputStream;

import frameWork.AllTests;
import frameWork.architect.Literal;
import frameWork.architect.build.AntUtil;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;
import frameWork.base.core.fileSystem.FileUtil;

public class JUnit extends Task {
	
	public static void main(final String[] args) throws Exception {
		new JUnit().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		final File testBuildFile = new File(build.testBuildFile);
		new File(build.targetTestDir).mkdirs();
		try (FileOutputStream os = new FileOutputStream(testBuildFile)) {
			os.write(new StringBuilder("<?xml version=\"1.0\"?><project xmlns:jacoco=\"antlib:org.jacoco.ant\" name=\"")
			        .append(build.coverageTitel)
			        .append("\" default=\"build\">")
			        .append(AntUtil.taskdefJacocoAnt(build.libDir))
			        .append("<target name=\"build\">")
			        .append("<jacoco:coverage destfile=\"")
			        .append(build.coverageExecFile)
			        .append("\"><junit fork=\"true\" forkmode=\"once\"><formatter type=\"xml\" /><classpath><path path=\"")
			        .append(build.targetTestDir).append("\" /><fileset dir=\"").append(build.libDir)
			        .append("\" includes=\"*.jar\"/><fileset dir=\"").append(build.clsDir)
			        .append("\" includes=\"*.jar\"/><fileset dir=\"").append(build.installerJarDir)
			        .append("\" includes=\"").append(build.frameworkJar).append("\"/></classpath><batchtest todir=\"")
			        .append(build.managerArchitectDir).append("\"><fileset dir=\"").append(build.testDir)
			        .append("\"><include name=\"**/").append(AllTests.class.getSimpleName())
			        .append(".java\" /></fileset></batchtest></junit></jacoco:coverage></target></project>").toString()
			        .getBytes());
		}
		AntUtil.invoke(build.jdkDir, testBuildFile);
		FileUtil.delete(new File("./" + Literal.Temp));
	}
}
