package frameWork.base.core.event.queue;

import frameWork.base.core.fileSystem.FileSystem;

class QueueThread extends Thread {
	private final Queue eventQueue;
	
	QueueThread(final Queue eventQueue) {
		this.eventQueue = eventQueue;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				eventQueue.pollEvent().run();
			}
			catch (final InterruptedException e) {
				FileSystem.Log.logging(e);
			}
		}
	}
}