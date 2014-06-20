package frameWork.architect.build.clean;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.AllTests;
import frameWork.ParallelSuite;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;

public class CleanAllTestClass extends Task {
	public static void main(final String[] args) throws Exception {
		new CleanAllTestClass().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		try (FileOutputStream os = new FileOutputStream(new File(build.testDir + "/"
		        + AllTests.class.getCanonicalName().replace(".", "/") + ".java"))) {
			os.write(new StringBuilder("package ").append(AllTests.class.getPackage().getName().replace(".", "/"))
			        .append(";\r\n@").append(RunWith.class.getCanonicalName()).append("(")
			        .append(ParallelSuite.class.getCanonicalName()).append(".class)\r\n@")
			        .append(SuiteClasses.class.getCanonicalName()).append("({\r\n})\r\npublic class ")
			        .append(AllTests.class.getSimpleName()).append(" {}").toString().getBytes());
		}
	}
	
}
