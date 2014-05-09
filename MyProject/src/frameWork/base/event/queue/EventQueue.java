package frameWork.event.queue;

import frameWork.event.Event;
import frameWork.event.TimerEvent;

public class EventQueue {
	private static Queue QUEUE = new Queue();
	
	public void putEvent(final Event event) {
		QUEUE.putEvent(event);
	}
	
	public void putEvent(final TimerEvent event) {
		QUEUE.putEvent(event);
	}
}