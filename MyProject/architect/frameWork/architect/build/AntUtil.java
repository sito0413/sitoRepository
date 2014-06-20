package frameWork.architect.build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.ant.ProjectHelper;

public class AntUtil {
	
	public static String compile(final String srcdir, final String destdir, final String... classpathStrings) {
		final StringBuilder builder = new StringBuilder("<javac srcdir=\"").append(srcdir).append("\" destdir=\"")
		        .append(destdir)
		        .append("\" debug=\"true\" includeantruntime=\"false\" fork=\"true\" encoding=\"utf-8\" >");
		builder.append("<classpath refid=\"classPath\"/>");
		for (final String classpath : classpathStrings) {
			builder.append("<classpath refid=\"").append(classpath).append("\"/>");
		}
		
		return builder.append("</javac>").toString();
	}
	
	public static String taskdefJacocoAnt(final String libDir) {
		return "<taskdef uri=\"antlib:org.jacoco.ant\" resource=\"org/jacoco/ant/antlib.xml\"><classpath path=\""
		        + libDir + "/jacocoant.jar\"/></taskdef>";
	}
	
	public static void invoke(final String jdkDir, final File buildFile) {
		final org.apache.tools.ant.Project p = new org.apache.tools.ant.Project();
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());
		p.setUserProperty("JAVA_HOME", jdkDir);
		p.fireBuildStarted();
		p.init();
		final ProjectHelper helper = ProjectHelper.getProjectHelper();
		p.addReference("ant.projectHelper", helper);
		helper.parse(p, buildFile);
		p.executeTarget(p.getDefaultTarget());
		p.fireBuildFinished(null);
		
	}
	
	public static void jar(final String jdkDir, final File buildFile, final String jarfile, final String className,
	        final String... filesetStrings) throws FileNotFoundException, IOException {
		final StringBuilder builder = new StringBuilder(
		        "<?xml version=\"1.0\"?><project default=\"build\"><target name=\"build\"><jar jarfile=\"").append(
		        jarfile).append("\">");
		if (!className.isEmpty()) {
			builder.append("<manifest><attribute name=\"Main-Class\" value=\"" + className + "\"/></manifest>");
		}
		for (final String fileset : filesetStrings) {
			for (final String includes : new String[] {
			        "**/*.png", "**/*.class", "**/*.java", "**/*.ttf", "**/*.xml", "**/*.jar", "**/*.zip"
			}) {
				builder.append("<fileset dir=\"").append(fileset).append("\" includes=\"").append(includes)
				        .append("\"/>");
			}
		}
		builder.append("</jar></target></project>").toString();
		try (FileOutputStream os = new FileOutputStream(buildFile)) {
			os.write(builder.toString().getBytes());
		}
		invoke(jdkDir, buildFile);
	}
}
