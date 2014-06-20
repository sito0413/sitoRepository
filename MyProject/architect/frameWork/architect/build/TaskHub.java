package frameWork.architect.build;

public abstract class TaskHub extends Task {
	private final Task[] tasks;
	
	public TaskHub(final Task... tasks) {
		this.tasks = tasks;
	}
	
	@Override
	public final void invoke(final BuildTask build) throws Exception {
		for (final Task task : tasks) {
			task._invoke(build);
		}
	}
	
	@Override
	int getWhight() {
		int whight = 0;
		for (final Task task : tasks) {
			whight += task.getWhight();
		}
		return whight;
	}
}
