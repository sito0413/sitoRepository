package frameWork.architect.build.compileFramework.test.coverage;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;

public class Coverage extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new Coverage().invoke(new BuildTask());
	}
	
	public Coverage() {
		super(new Html(), new Xml());
	}
}
