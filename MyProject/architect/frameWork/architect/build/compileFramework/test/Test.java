package frameWork.architect.build.compileFramework.test;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;
import frameWork.architect.build.compileFramework.test.coverage.Coverage;

public class Test extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new Test().invoke(new BuildTask());
	}
	
	public Test() {
		super(new CreateAllTestClass(), new Compile(), new JUnit(), new Coverage());
	}
}
