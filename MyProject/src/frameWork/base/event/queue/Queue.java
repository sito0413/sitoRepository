package frameWork.event.queue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import frameWork.event.Event;
import frameWork.event.TimerEvent;

class Queue {
	private transient EventNode head;
	private transient EventNode last;
	private final AtomicInteger threadCount;
	private final ReentrantLock takeLock;
	private final Condition notEmpty;
	private final Timer timer;
	private final boolean timerFlg;
	
	Queue() {
		this.timerFlg = false;
		this.timer = new Timer(this);
		this.takeLock = new ReentrantLock();
		this.notEmpty = this.takeLock.newCondition();
		this.last = this.head = new EventNode(null);
		this.threadCount = new AtomicInteger(0);
		while (threadCount.get() < Math.max(5, Runtime.getRuntime().availableProcessors() * 2)) {
			final Thread thread = new QueueThread(this);
			thread.setName("eqt" + hashCode() + "-" + threadCount.getAndIncrement());
			thread.setPriority(Thread.NORM_PRIORITY);
			thread.setDaemon(true);
			thread.start();
		}
	}
	
	Event pollEvent() throws InterruptedException {
		Event x = null;
		takeLock.lock();
		try {
			while (head.next == null) {
				notEmpty.await(1, TimeUnit.MINUTES);
			}
			head = head.next;
			x = head.item;
			head.item = null;
		}
		finally {
			takeLock.unlock();
		}
		return x;
	}
	
	void putEvent(final Event event) {
		if (head.next == null) {
			//head.next == null は _put より先に実行
			_put(new EventNode(event));
			takeLock.lock();
			try {
				notEmpty.signalAll();
			}
			finally {
				takeLock.unlock();
			}
		}
		else {
			_put(new EventNode(event));
		}
	}
	
	private synchronized void _put(final EventNode node) {
		last.next = node;
		last = node;
	}
	
	void putEvent(final TimerEvent event) {
		if (!timerFlg) {
			putEvent(timer);
		}
		timer.add(event);
	}
	
}
