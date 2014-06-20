package frameWork.architect.build;

import frameWork.AllTests;
import frameWork.architect.build.clean.Clean;
import frameWork.architect.build.compileFramework.CompileFramework;
import frameWork.architect.build.compileInstaller.CompileInstaller;
import frameWork.architect.build.compileKicker.CompileKicker;
import frameWork.architect.build.compileManager.CompileManager;
import frameWork.architect.build.createLiblaryIndex.CreateLiblaryIndex;

public class BuildTask extends TaskHub {
	public final String kickerDir = "./kicker";
	public final String srcDir = "./src";
	public final String libDir = "./lib";
	public final String clsDir = "./cls";
	public final String jdkDir = "./jdk";
	public final String jarDir = "./jar";
	public final String testDir = "./test";
	public final String htmlDir = "./html";
	public final String managerDir = "./manager";
	public final String installerDir = "./installer";
	
	private final String targetDir = "./target";
	
	public final String managerArchitectDir = managerDir + "/frameWork/architect";
	public final String targetCompileFrameworkDir = targetDir + "/compileFrameworkClass";
	public final String targetTestDir = targetDir + "/testClass";
	public final String installerJarDir = installerDir + "/frameWork/architect/jar";
	public final String installerJarLibDir = installerDir + "/frameWork/architect/jar/lib";
	public final String installerJarClsDir = installerDir + "/frameWork/architect/jar/cls";
	public final String targetManagerDir = targetDir + "/compileManagerClass";
	public final String targetCompileKickerDir = targetDir + "/compileKickerClass";
	public final String targetCompileInstallerDir = targetDir + "/compileInstallerClass";
	
	public final String coverageTitel = "フレームワークBUILD";
	public final String coverageReportTitel = "カバレッジレポート";
	public final String frameworkJar = "framework.jar";
	
	public final String compileFrameworkBuildFile = "./compileFrameworkBuild.xml";
	public final String jarFrameworkBuildFile = "./jarFrameworkBuild.xml";
	public final String compileTestBuildFile = "./compileTestBuild.xml";
	public final String testBuildFile = "./testBuild.xml";
	public final String coverageXmlBuildFile = "./coverageXmlBuild.xml";
	public final String coverageHtmlBuildFile = "./coverageHtmlBuild.xml";
	public final String managerTest = "./manager/frameWork/architect/result.xml";
	public final String compileManagerBuildFile = "./compileManagerBuild.xml";
	public final String jarManagerBuildFile = "./jarManagerBuild.xml";
	public final String compileKickerBuildFile = "./compileKickerBuild.xml";
	public final String jarKickerBuildFile = "./jarKickerBuild.xml";
	public final String compileInstallerBuildFile = "./compileInstallerBuild.xml";
	public final String jarInstallerBuildFile = "./jarInstallerBuild.xml";
	public final String frameworkJarPath = installerJarDir + "/" + frameworkJar;
	public final String managerJarPath = installerJarDir + "/manager.jar";
	public final String kickerJarPath = installerJarDir + "/kicker.jar";
	public final String installerJarInfo = installerJarDir + "/info.xml";
	public final String managerResult = managerDir + "/frameWork/architect/TEST-" + AllTests.class.getCanonicalName()
	        + ".xml";
	public final String coverageExecFile = targetDir + "/coverage.exec";
	public final String managerInfo = managerDir + "/frameWork/architect/info.xml";
	public final String jdkZipPath = libDir + "/jdk.zip";
	public final String installerJarPath = jarDir + "/installer.jar";
	
	public String[] clearTarget = {
	        targetDir, compileFrameworkBuildFile, jarFrameworkBuildFile, compileTestBuildFile, testBuildFile,
	        coverageXmlBuildFile, coverageHtmlBuildFile, compileManagerBuildFile, jarManagerBuildFile,
	        compileKickerBuildFile, jarKickerBuildFile, compileInstallerBuildFile, jarInstallerBuildFile, managerTest,
	        managerResult, coverageExecFile, managerInfo, frameworkJarPath, managerJarPath, kickerJarPath,
	        installerJarInfo
	};
	public final long currentTimeMillis = System.currentTimeMillis();
	
	public static void main(final String[] args) throws Exception {
		new BuildTask().invoke();
	}
	
	public BuildTask() {
		super(new Clean(), new CompileFramework(), new CompileKicker(), new CompileManager(), new CreateLiblaryIndex(),
		        new CompileInstaller(), new Clean()
		//		, new CleanHtml()
		);
	}
	
	public void invoke() throws Exception {
		invoke(this);
	}
	
}
