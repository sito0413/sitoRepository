package frameWork.event.timerEvent;

import frameWork.event.Event;
import frameWork.event.TimerEvent;

public class CallTimerEvent implements Event {
	private final TimerEvent timerEvent;
	private final TimerEventStack runningList;
	
	public CallTimerEvent(final TimerEvent timerEvent, final TimerEventStack runningList) {
		this.timerEvent = timerEvent;
		this.runningList = runningList;
	}
	
	@Override
	public void run() {
		timerEvent.run();
		runningList.remove(timerEvent);
	}
}