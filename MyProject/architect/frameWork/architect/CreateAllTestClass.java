package frameWork.architect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import frameWork.AllTests;
import frameWork.ParallelSuite;

public class CreateAllTestClass {
	public static void main(final String[] args) {
		createAllTestClass();
	}
	
	static File createAllTestClass() {
		final File file = new File("./test/" + AllTests.class.getCanonicalName().replace(".", "/") + "2.java");
		try (FileOutputStream os = new FileOutputStream(file)) {
			os.write(new StringBuilder("package ").append(AllTests.class.getPackage().getName().replace(".", "/"))
			        .append(";\r\n@").append(RunWith.class.getCanonicalName()).append("(")
			        .append(ParallelSuite.class.getCanonicalName()).append(".class)\r\n@")
			        .append(SuiteClasses.class.getCanonicalName()).append("({\r\n").append(testTarget())
			        .append(" \r\n})\r\npublic class ").append(AllTests.class.getSimpleName()).append("2 {}")
			        .toString().getBytes());
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}
		return file;
	}
	
	private static Object testTarget() {
		final StringBuilder stringBuilder = new StringBuilder();
		final File file = new File("./test");
		if (file.isDirectory()) {
			stringBuilder.append(getClassList(file.getAbsolutePath().length() + 1, file));
		}
		return stringBuilder.toString();
	}
	
	private static Object getClassList(final int baseLengh, final File dir) {
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
