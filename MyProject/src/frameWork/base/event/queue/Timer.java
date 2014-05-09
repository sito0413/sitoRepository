package frameWork.base.event.queue;

import frameWork.base.event.Event;
import frameWork.base.event.TimerEvent;
import frameWork.base.event.timerEvent.CallTimerEvent;
import frameWork.base.event.timerEvent.TimerEventList;
import frameWork.base.event.timerEvent.TimerEventStack;
import frameWork.base.util.ThrowableUtil;

class Timer implements Event {
	private final Queue queue;
	private final TimerEventList timerList;
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
				final long now = System.currentTimeMillis();
				if ((now > timerEvent.nextTime()) && !runningList.contains(timerEvent)) {
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
				ThrowableUtil.throwable(e);
			}
		}
		queue.putEvent(this);
	}
	
	void add(final TimerEvent event) {
		timerList.add(event);
	}
}