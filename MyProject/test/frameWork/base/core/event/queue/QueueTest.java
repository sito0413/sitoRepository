package frameWork.base.core.event.queue;

import static org.junit.Assert.*;

import org.junit.Test;

import frameWork.base.core.event.Event;
import frameWork.base.core.event.TimerEvent;

public class QueueTest {
	@Test
	public void test() {
		final EventQueue queue = new EventQueue();
		for (int i = 0; i < 10; i++) {
			final Event event = new Event() {
				@Override
				public void run() {
				}
			};
			final TimerEvent timerEvent = new TimerEvent() {
				int i0 = 1;

				@Override
				public void run() {
				}

				@Override
				public long nextTime() {
					return --i0;
				}
			};
			final TimerEvent timerEvent1 = new TimerEvent() {
				int i1 = 1000000;

				@Override
				public void run() {
				}

				@Override
				public long nextTime() {
					final int i2 = i1;
					i1 -= 1000001;
					return System.currentTimeMillis() + i2;
				}
			};
			final TimerEvent timerEvent2 = new TimerEvent() {

				@Override
				public void run() {
				}

				@Override
				public long nextTime() {
					return -1;
				}
			};
			queue.putEvent(timerEvent);
			queue.putEvent(event);
			queue.putEvent(timerEvent1);
			queue.putEvent(timerEvent2);
		}
		try {
	        Thread.sleep(1000);
        }
        catch (InterruptedException e) {
	      fail(e.getMessage());
        }
	}
}
