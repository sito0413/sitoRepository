package frameWork.event.timerEvent;

import java.util.Iterator;
import java.util.NoSuchElementException;

import frameWork.event.TimerEvent;

public class TimerEventList implements Iterable<TimerEvent> {
	private transient TimerEventNode head;
	private transient TimerEventNode last;
	
	public TimerEventList() {
		this.last = this.head = new TimerEventNode(null);
	}
	
	public synchronized void add(final TimerEvent e) {
		final TimerEventNode node = new TimerEventNode(e);
		last.next = node;
		last = node;
		return;
	}
	
	public void remove(final TimerEvent o) {
		if (o == null) {
			return;
		}
		TimerEventNode preNode = head;
		TimerEventNode node = head.next;
		while (node != null) {
			if (o.equals(node.item)) {
				synchronized (this) {
					if (o.equals(node.item)) {
						preNode.next = node.next;
						if (last == node) {
							last = preNode;
						}
						return;
					}
				}
			}
			preNode = node;
			node = node.next;
		}
	}
	
	@Override
	public Iterator<TimerEvent> iterator() {
		return new TimerEventListIterator(head);
	}
	
	private static class TimerEventListIterator implements Iterator<TimerEvent> {
		private TimerEventNode cursor;
		
		private TimerEventListIterator(final TimerEventNode head) {
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
}