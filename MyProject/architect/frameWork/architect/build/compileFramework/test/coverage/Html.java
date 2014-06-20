package frameWork.architect.build.compileFramework.test.coverage;

import java.io.File;
import java.io.FileOutputStream;

import frameWork.architect.build.AntUtil;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;

public class Html extends Task {
	public static void main(final String[] args) throws Exception {
		new Html().invoke(new BuildTask());
	}
	
	@Override
	public void invoke(final BuildTask build) throws Exception {
		final File coverageHtmlBuildFile = new File(build.coverageHtmlBuildFile);
		new File(build.htmlDir).mkdirs();
		try (FileOutputStream os = new FileOutputStream(coverageHtmlBuildFile)) {
			os.write(new StringBuilder("<?xml version=\"1.0\"?><project xmlns:jacoco=\"antlib:org.jacoco.ant\" name=\"")
			        .append(build.coverageTitel).append("\" default=\"build\">")
			        .append(AntUtil.taskdefJacocoAnt(build.libDir))
			        .append("<target name=\"build\"><jacoco:report><executiondata><file file=\"")
			        .append(build.coverageExecFile).append("\"/></executiondata><structure name=\"")
			        .append(build.coverageReportTitel).append("\"><classfiles><fileset dir=\"")
			        .append(build.targetCompileFrameworkDir)
			        .append("\"/></classfiles><sourcefiles encoding=\"UTF-8\"><fileset dir=\"").append(build.srcDir)
			        .append("\"/></sourcefiles></structure><html destdir=\"").append(build.htmlDir).append("\"/>")
			        .append("</jacoco:report></target></project>").toString().getBytes());
		}
		AntUtil.invoke(build.jdkDir, coverageHtmlBuildFile);
	}
}
