package frameWork.architect.build.clean;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;

public class Clean extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new Clean().invoke(new BuildTask());
	}
	
	public Clean() {
		super(new CleanAllTestClass(), new CleanTargetFile(), new CleanLibFile(), new CleanJdkZip());
	}
	
}
