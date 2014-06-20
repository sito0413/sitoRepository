package frameWork.architect.build.compileFramework;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;
import frameWork.architect.build.compileFramework.test.Test;

public class CompileFramework extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new CompileFramework().invoke(new BuildTask());
	}
	
	public CompileFramework() {
		super(new Compile(), new Jar(), new Test());
	}
}
