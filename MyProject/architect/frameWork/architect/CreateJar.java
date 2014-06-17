package frameWork.architect;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.ProjectHelper;

public class CreateJar {
	public static void main(final String[] args) {
		CreateAllTestClass.createAllTestClass();
		CreateBuildFile.createBuildFile();
		final File buildFile = new File("./build.xml");
		final org.apache.tools.ant.Project p = new org.apache.tools.ant.Project();
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());
		p.setUserProperty("JAVA_HOME", "./jdk");
		final DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);//
		consoleLogger.setMessageOutputLevel(org.apache.tools.ant.Project.MSG_INFO);
		p.addBuildListener(consoleLogger);
		try {
			p.fireBuildStarted();
			p.init();
			final ProjectHelper helper = ProjectHelper.getProjectHelper();
			p.addReference("ant.projectHelper", helper);
			helper.parse(p, buildFile);
			p.executeTarget(p.getDefaultTarget());
			p.fireBuildFinished(null);
		}
		catch (final BuildException e) {
			p.fireBuildFinished(e);
		}
	}
	
}
