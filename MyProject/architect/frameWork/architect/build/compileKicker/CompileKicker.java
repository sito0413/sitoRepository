package frameWork.architect.build.compileKicker;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;

public class CompileKicker extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new CompileKicker().invoke(new BuildTask());
	}
	
	public CompileKicker() {
		super(new Compile(), new Jar());
	}
}
