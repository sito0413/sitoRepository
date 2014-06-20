package frameWork.architect.build;

import java.io.File;

import frameWork.base.core.fileSystem.FileUtil;

public class CleanHtml extends Task {
	public static void main(final String[] args) throws Exception {
		new CleanHtml().invoke(new BuildTask());
	}
	
	@Override
	protected void invoke(final BuildTask build) throws Exception {
		FileUtil.delete(new File(build.htmlDir));
	}
	
}
