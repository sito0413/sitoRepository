package frameWork.base.core.event.timerEvent;

import java.util.Iterator;

import frameWork.base.core.event.TimerEvent;

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
}