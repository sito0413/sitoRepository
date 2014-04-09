package frameWork.event.queue;

import frameWork.ThrowableUtil;

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
				ThrowableUtil.throwable(e);
			}
		}
	}
}