package frameWork.architect.build.compileFramework.test;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.AllTests;
import frameWork.ParallelSuite;
import frameWork.architect.build.BuildTask;
import frameWork.architect.build.Task;

public class CreateAllTestClass extends Task {
	public static void main(final String[] args) throws Exception {
		new CreateAllTestClass().invoke(new BuildTask());
	}
	
	@Override
	public void invoke(final BuildTask build) throws Exception {
		try (FileOutputStream os = new FileOutputStream(new File(build.testDir + "/"
		        + AllTests.class.getCanonicalName().replace(".", "/") + ".java"))) {
			os.write(new StringBuilder("package ").append(AllTests.class.getPackage().getName().replace(".", "/"))
			        .append(";\r\n@").append(RunWith.class.getCanonicalName()).append("(")
			        .append(ParallelSuite.class.getCanonicalName()).append(".class)\r\n@")
			        .append(SuiteClasses.class.getCanonicalName()).append("({\r\n").append(testTarget(build))
			        .append(" \r\n})\r\npublic class ").append(AllTests.class.getSimpleName()).append(" {}").toString()
			        .getBytes());
		}
	}
	
	private Object testTarget(final BuildTask build) {
		final StringBuilder stringBuilder = new StringBuilder();
		final File file = new File(build.testDir);
		if (file.isDirectory()) {
			stringBuilder.append(getClassList(file.getAbsolutePath().length() + 1, file));
		}
		return stringBuilder.toString();
	}
	
	private Object getClassList(final int baseLengh, final File dir) {
		final StringBuilder stringBuilder = new StringBuilder();
		for (final File file : dir.listFiles()) {
			if (file.isDirectory()) {
				stringBuilder.append(getClassList(baseLengh, file));
			}
			else if (file.getName().endsWith("Test.java")) {
				final String name = file.getAbsolutePath().substring(baseLengh).replace(File.separator, ".");
				
				stringBuilder.append(name.substring(0, name.length() - ".java".length())).append(".class, \r\n");
			}
		}
		return stringBuilder.toString();
	}
}
