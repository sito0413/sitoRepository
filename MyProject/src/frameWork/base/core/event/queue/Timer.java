package frameWork.base.core.event.queue;

import frameWork.base.core.event.Event;
import frameWork.base.core.event.TimerEvent;
import frameWork.base.core.event.timerEvent.CallTimerEvent;
import frameWork.base.core.event.timerEvent.TimerEventList;
import frameWork.base.core.event.timerEvent.TimerEventStack;
import frameWork.base.core.fileSystem.FileSystem;

class Timer implements Event {
	private final Queue queue;
	final TimerEventList timerList;
	private final TimerEventStack runningList;
	
	Timer(final Queue queue) {
		this.queue = queue;
		this.timerList = new TimerEventList();
		this.runningList = new TimerEventStack();
	}
	
	@Override
	public void run() {
		for (final TimerEvent timerEvent : timerList) {
			if (timerEvent.nextTime() == -1) {
				timerList.remove(timerEvent);
			}
			else {
				if ((System.currentTimeMillis() > timerEvent.nextTime()) && !runningList.contains(timerEvent)) {
					runningList.add(timerEvent);
					queue.putEvent(new CallTimerEvent(timerEvent, runningList));
				}
			}
		}
		synchronized (this) {
			try {
				wait(0, 1);
			}
			catch (final InterruptedException e) {
				FileSystem.Log.logging(e);
			}
		}
		queue.putEvent(this);
	}
}