package frameWork.base.event.queue;

import frameWork.base.event.Event;

class EventNode {
	Event item;
	EventNode next;
	
	EventNode(final Event x) {
		item = x;
	}
}