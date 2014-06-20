package frameWork.architect.build.compileManager;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;

public class CompileManager extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new CompileManager().invoke(new BuildTask());
	}
	
	public CompileManager() {
		super(new CreateInfoXml(), new Compile(), new Jar());
	}
}
