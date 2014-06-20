package frameWork.architect.build.createLiblaryIndex;

import frameWork.architect.build.BuildTask;
import frameWork.architect.build.TaskHub;

public class CreateLiblaryIndex extends TaskHub {
	public static void main(final String[] args) throws Exception {
		new CreateLiblaryIndex().invoke(new BuildTask());
	}
	
	public CreateLiblaryIndex() {
		super(new CreateZip(), new CopyLib(), new CreateIndexFile());
	}
}
