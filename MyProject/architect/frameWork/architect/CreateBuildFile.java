package frameWork.architect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import frameWork.AllTests;
import frameWork.architect.installer.Installer;
import frameWork.manager.FrameworkManager;

public class CreateBuildFile {
	public static void main(final String[] args) {
		createBuildFile();
	}
	
	private static String jarDir = "jar.dir";
	private static String srcDir = "src.dir";
	private static String libDir = "lib.dir";
	private static String managerDir = "manager.dir";
	private static String installerDir = "installer.dir";
	private static String targetDir = "target.dir";
	private static String testDir = "test.dir";
	private static String installerJarDir = "installer.jar.dir";
	private static String installerJarInfo = "installer.jar.info";
	private static String managerInfo = "manager.info";
	private static String managerTest = "manager.test";
	private static String managerResult = "manager.result";
	private static String managerArchitectDir = "manager.architect.dir";
	private static String targetCompileFrameworkDir = "target.compileFramework.dir";
	private static String targetTestDir = "target.test.dir";
	private static String targetManagerDir = "target.manager.dir";
	private static String targetInstallerDir = "target.installer.dir";
	private static String frameworkJar = "framework.jar";
	private static String managerJar = "manager.jar";
	private static String installerJar = "installer.jar";
	private static String coverageExecFile = "coverage.exec.file";
	private static String NOW = "NOW";
	
	private static String clean = "clean";
	private static String compileFramework = "compileFramework";
	private static String test = "test";
	private static String coverage = "coverage";
	private static String compileManager = "compileManager";
	private static String compileInstaller = "compileInstaller";
	
	private static File createBuildFile() {
		final File file = new File("./build.xml");
		try (FileOutputStream os = new FileOutputStream(file)) {
			os.write(new StringBuilder(
			        "<?xml version=\"1.0\"?><project xmlns:jacoco=\"antlib:org.jacoco.ant\" name=\"フレームワークBUILD\" default=\"build\"><tstamp><format property=\"")
			        .append(NOW)
			        .append("\" pattern=\"yyyyMMddHHmmss\"/></tstamp>")
			        .append(defineProperty(jarDir, "./jar"))
			        .append(defineProperty(srcDir, "./src"))
			        .append(defineProperty(libDir, "./lib"))
			        .append(defineProperty(managerDir, "./manager"))
			        .append(defineProperty(installerDir, "./installer"))
			        .append(defineProperty(targetDir, "./target"))
			        .append(defineProperty(testDir, "./test"))
			        .append(defineProperty(installerJarDir, "${" + installerDir + "}/frameWork/architect/jar"))
			        .append(defineProperty(installerJarInfo, "${" + installerJarDir + "}/info.xml"))
			        .append(defineProperty(managerArchitectDir, "${" + managerDir + "}/frameWork/architect"))
			        .append(defineProperty(managerInfo, "${" + managerDir + "}/frameWork/architect/info.xml"))
			        .append(defineProperty(managerTest, "${" + managerDir + "}/frameWork/architect/result.xml"))
			        .append(defineProperty(managerResult, "${" + managerDir + "}/frameWork/architect/TEST-"
			                + AllTests.class.getCanonicalName() + ".xml"))
			        .append(defineProperty(targetCompileFrameworkDir, "${" + targetDir + "}/" + compileFramework
			                + "Class"))
			        .append(defineProperty(targetTestDir, "${" + targetDir + "}/" + test + "Class"))
			        .append(defineProperty(targetManagerDir, "${" + targetDir + "}/" + compileManager + "Class"))
			        .append(defineProperty(targetInstallerDir, "${" + targetDir + "}/" + compileInstaller + "Class"))
			        .append(defineProperty(coverageExecFile, "${" + targetDir + "}/" + coverage + ".exec"))
			        .append("<taskdef uri=\"antlib:org.jacoco.ant\" resource=\"org/jacoco/ant/antlib.xml\"><classpath path=\"${")
			        .append(libDir).append("}/jacocoant.jar\"/></taskdef>").append(clean()).append(compileFramework())
			        .append(test()).append(coverage()).append(compileManager()).append(compileInstaller())
			        .append("<target name=\"build\" depends=\"").append(clean).append(",").append(compileFramework)
			        .append(",").append(test).append(",").append(coverage).append(",").append(compileManager)
			        .append(",").append(compileInstaller).append("\">")
			        
			        .append("<antcall target=\"").append(clean).append("\"/>")
			        
			        .append("</target></project>").toString().getBytes());
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}
		return file;
	}
	
	private static String clean() {
		return new StringBuilder("<target name=\"").append(clean).append("\"><delete dir=\"${").append(targetDir)
		        .append("}\"/><delete dir=\"${").append(installerJarDir).append("}\"/><delete file=\"${")
		        .append(managerInfo).append("}\"/><delete file=\"${").append(managerTest)
		        .append("}\"/><delete file=\"${").append(managerResult).append("}\"/></target>").toString();
	}
	
	private static String compileFramework() {
		return new StringBuilder("<target name=\"").append(compileFramework).append("\" depends=\"").append(clean)
		        .append("\"><mkdir dir=\"${").append(targetCompileFrameworkDir).append("}\"/><path id=\"")
		        .append(compileFramework).append("Path\"><fileset dir=\"${").append(libDir)
		        .append("}\" includes=\"*.jar\"/></path>")
		        .append(compile(srcDir, targetCompileFrameworkDir, compileFramework + "Path"))
		        .append(jar(installerJarDir, frameworkJar, "", targetCompileFrameworkDir, srcDir)).append("</target>")
		        .toString();
	}
	
	private static String test() {
		return new StringBuilder("<target name=\"")
		        .append(test)
		        .append("\" depends=\"")
		        .append(compileFramework)
		        .append("\"><mkdir dir=\"${")
		        .append(targetTestDir)
		        .append("}\"/><path id=\"")
		        .append(test + "Path\"><fileset dir=\"${")
		        .append(libDir)
		        .append("}\" includes=\"*.jar\"/>")
		        .append("<fileset dir=\"${")
		        .append(installerJarDir)
		        .append("}\" includes=\"")
		        .append(frameworkJar)
		        .append("\"/></path>")
		        .append(compile(testDir, targetTestDir, test + "Path"))
		        .append("<jacoco:coverage destfile=\"${")
		        .append(coverageExecFile)
		        .append("}\"><junit fork=\"true\" forkmode=\"once\"><formatter type=\"xml\" /><classpath><path path=\"${")
		        .append(targetTestDir).append("}\" /><fileset dir=\"${").append(libDir)
		        .append("}\" includes=\"*.jar\"/><fileset dir=\"${").append(installerJarDir).append("}\" includes=\"")
		        .append(frameworkJar).append("\"/></classpath><batchtest todir=\"${").append(managerArchitectDir)
		        .append("}\"><fileset dir=\"${").append(testDir).append("}\"><include name=\"**/")
		        .append(AllTests.class.getSimpleName())
		        .append(".java\" /></fileset></batchtest></junit></jacoco:coverage></target>").toString();
	}
	
	private static String coverage() {
		return new StringBuilder("<target name=\"").append(coverage).append("\" depends=\"").append(test)
		        .append("\"><jacoco:report><executiondata><file file=\"${").append(coverageExecFile)
		        .append("}\"/></executiondata><structure name=\"カバレッジ\"><classfiles><fileset dir=\"${")
		        .append(targetCompileFrameworkDir)
		        .append("}\"/></classfiles><sourcefiles encoding=\"UTF-8\"><fileset dir=\"${").append(srcDir)
		        .append("}\"/></sourcefiles></structure><html destdir=\"./html\"/><xml destfile=\"${")
		        .append(managerTest).append("}\"/></jacoco:report></target>").toString();
	}
	
	private static String compileManager() {
		return new StringBuilder("<target name=\"")
		        .append(compileManager)
		        .append("\" depends=\"")
		        .append(coverage)
		        .append("\"><mkdir dir=\"${")
		        .append(targetManagerDir)
		        .append("}\"/><path id=\"")
		        .append(compileManager)
		        .append("Path\"><fileset dir=\"${")
		        .append(libDir)
		        .append("}\" includes=\"*.jar\"/><fileset dir=\"${")
		        .append(installerJarDir)
		        .append("}\" includes=\"")
		        .append(frameworkJar)
		        .append("\"/></path>")
		        .append(compile(managerDir, targetManagerDir, compileManager + "Path"))
		        .append(definePropertyXml(managerInfo, new String[] {
		                "Ver", "${" + NOW + "}"
		        }))
		        .append(jar(installerJarDir, managerJar, FrameworkManager.class.getCanonicalName(), targetManagerDir,
		                managerDir)).append("</target>").toString();
	}
	
	private static String compileInstaller() {
		return new StringBuilder("	<target name=\"")
		        .append(compileInstaller)
		        .append("\" depends=\"")
		        .append(compileManager)
		        .append("\"><copy todir=\"${")
		        .append(installerJarDir)
		        .append("}\"><fileset dir=\"${")
		        .append(libDir)
		        .append("}\" includes=\"*.jar\"/></copy><fileset id=\"test\" dir=\"${")
		        .append(libDir)
		        .append("}\" includes=\"/**/*.jar\"/><pathconvert pathsep=\"&#xA;\" property=\"filelist\" refid=\"test\" />")
		        .append(definePropertyXml(installerJarInfo, new String[] {
		                "Path", "${filelist}"
		        }))
		        .append("<mkdir dir=\"${")
		        .append(targetInstallerDir)
		        .append("}\"/><path id=\"")
		        .append(compileInstaller)
		        .append("Path\"><fileset dir=\"${")
		        .append(installerJarDir)
		        .append("}\" includes=\"*.jar\"/></path>")
		        .append(compile(installerDir, targetInstallerDir, compileInstaller + "Path"))
		        .append(jar(jarDir, installerJar, Installer.class.getCanonicalName(), targetInstallerDir, installerDir,
		                managerDir)).append("<mkdir dir=\"${").append(jarDir).append("}/${").append(NOW)
		        .append("}\"/><copy todir=\"${").append(jarDir).append("}/${").append(NOW)
		        .append("}\" ><fileset dir=\"${").append(jarDir).append("}\" includes=\"").append(installerJar)
		        .append("\"/></copy></target>").toString();
	}
	
	private static String definePropertyXml(final String file, final String[]... kvs) {
		final StringBuilder builder = new StringBuilder("<echo file=\"${")
		        .append(file)
		        .append("}\" message=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;Shift_JIS&quot; standalone=&quot;no&quot;?&gt;&lt;!DOCTYPE properties SYSTEM &quot;http://java.sun.com/dtd/properties.dtd&quot;&gt;");
		for (final String[] kv : kvs) {
			builder.append("&lt;properties&gt;&lt;entry key=&quot;").append(kv[0]).append("&quot;&gt;").append(kv[1])
			        .append("&lt;/entry&gt;&lt;/properties&gt;");
		}
		return builder.append("\" encoding=\"Shift_JIS\"/>").toString();
		
	}
	
	private static String jar(final String jarfile1, final String jarfile2, final String className,
	        final String... filesetStrings) {
		final StringBuilder builder = new StringBuilder("<jar jarfile=\"${").append(jarfile1).append("}/")
		        .append(jarfile2).append("\">");
		if (!className.isEmpty()) {
			builder.append("<manifest><attribute name=\"Main-Class\" value=\"" + className + "\"/></manifest>");
		}
		for (final String fileset : filesetStrings) {
			for (final String includes : new String[] {
			        "**/*.png", "**/*.class", "**/*.java", "**/*.ttf", "**/*.xml", "**/*.jar"
			}) {
				builder.append("<fileset dir=\"${").append(fileset).append("}\" includes=\"").append(includes)
				        .append("\"/>");
			}
		}
		
		return builder.append("</jar>").toString();
	}
	
	private static String compile(final String srcdir, final String destdir, final String... classpathStrings) {
		final StringBuilder builder = new StringBuilder("<javac srcdir=\"${").append(srcdir).append("}\" destdir=\"${")
		        .append(destdir).append("}\" debug=\"true\" includeantruntime=\"false\">");
		for (final String classpath : classpathStrings) {
			builder.append("<classpath refid=\"").append(classpath).append("\"/>");
		}
		return builder.append("</javac>").toString();
	}
	
	private static String defineProperty(final String name, final String location) {
		return new StringBuilder("<property name=\"").append(name).append("\" location=\"").append(location)
		        .append("\"/>").toString();
	}
}
