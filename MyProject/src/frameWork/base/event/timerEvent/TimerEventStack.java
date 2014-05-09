package frameWork.base.event.timerEvent;

import frameWork.base.event.TimerEvent;

public class TimerEventStack {
	private transient TimerEventNode head;
	private transient TimerEventNode last;
	
	public TimerEventStack() {
		this.last = this.head = new TimerEventNode(null);
	}
	
	public boolean contains(final TimerEvent o) {
		if (o == null) {
			return false;
		}
		TimerEventNode node = head.next;
		while (node != null) {
			if (o.equals(node.item)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}
	
	public void add(final TimerEvent e) {
		final TimerEventNode node = new TimerEventNode(e);
		last.next = node;
		last = node;
	}
	
	void remove(final TimerEvent o) {
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
	
}