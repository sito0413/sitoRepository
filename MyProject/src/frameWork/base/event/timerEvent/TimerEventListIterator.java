package frameWork.base.event.timerEvent;

import java.util.Iterator;
import java.util.NoSuchElementException;

import frameWork.base.event.TimerEvent;

class TimerEventListIterator implements Iterator<TimerEvent> {
	private TimerEventNode cursor;
	
	TimerEventListIterator(final TimerEventNode head) {
		cursor = head;
	}
	
	@Override
	public boolean hasNext() {
		return (cursor.next != null);
	}
	
	@Override
	public TimerEvent next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		cursor = cursor.next;
		return cursor.item;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}