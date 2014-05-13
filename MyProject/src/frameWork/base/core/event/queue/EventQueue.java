package frameWork.base.core.event.queue;

import frameWork.base.core.event.Event;
import frameWork.base.core.event.TimerEvent;

public class EventQueue {
	private static Queue QUEUE = new Queue();
	
	public void putEvent(final Event event) {
		QUEUE.putEvent(event);
	}
	
	public void putEvent(final TimerEvent event) {
		QUEUE.putEvent(event);
	}
}