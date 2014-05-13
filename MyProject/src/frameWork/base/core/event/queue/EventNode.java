package frameWork.base.core.event.queue;

import frameWork.base.core.event.Event;

class EventNode {
	Event item;
	EventNode next;
	
	EventNode(final Event x) {
		item = x;
	}
}