package frameWork.event.queue;

import frameWork.event.Event;

class EventNode {
	Event item;
	EventNode next;
	
	EventNode(final Event x) {
		item = x;
	}
}