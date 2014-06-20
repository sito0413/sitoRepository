package frameWork.architect.build.compileInstaller;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;

public class CompileInstaller extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new CompileInstaller().invoke(new BuildTask());
	}
	
	public CompileInstaller() {
		super(new Compile(), new Jar(), new Archive());
	}
}
