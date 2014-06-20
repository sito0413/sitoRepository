package frameWork.architect.build;

public abstract class Task {
	protected abstract void invoke(final BuildTask build) throws Exception;
	
	void _invoke(final BuildTask build) throws Exception {
		System.out.println("Start - " + getClass().getSimpleName());
		invoke(build);
		System.out.println("End - " + getClass().getSimpleName());
	}
	
	int getWhight() {
		return 1;
	}
}
