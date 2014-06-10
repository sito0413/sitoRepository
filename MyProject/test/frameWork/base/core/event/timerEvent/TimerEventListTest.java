package frameWork.base.core.event.timerEvent;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import frameWork.base.core.event.TimerEvent;

public class TimerEventListTest {
	
	@Test
	public void hasNextTest() {
		final TimerEventList timerEventList = new TimerEventList();
		final TimerEvent event = new TimerEvent() {
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		};
		final Iterator<TimerEvent> iterator = timerEventList.iterator();
		assertFalse(iterator.hasNext());
		timerEventList.add(event);
		timerEventList.add(new TimerEvent() {
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		});
		assertTrue(iterator.hasNext());
	}
	
	@Test
	public void nextTest() {
		final TimerEventList timerEventList = new TimerEventList();
		final TimerEvent event = new TimerEvent() {
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		};
		timerEventList.add(event);
		timerEventList.add(new TimerEvent() {
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		});
		final Iterator<TimerEvent> iterator = timerEventList.iterator();
		assertTrue(iterator.next().equals(event));
		assertFalse(iterator.next().equals(event));
		try {
			iterator.next();
			fail("error");
		}
		catch (final NoSuchElementException e) {
			//NOOP
		}
	}
	
	@Test
	public void removeTest() {
		final TimerEventList timerEventList = new TimerEventList();
		final TimerEvent event = new TimerEvent() {
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		};
		timerEventList.add(new TimerEvent() {
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		});
		timerEventList.add(event);
		timerEventList.add(new TimerEvent() {
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		});
		final Iterator<TimerEvent> iterator = timerEventList.iterator();
		try {
			iterator.remove();
			fail("error");
		}
		catch (final UnsupportedOperationException e) {
			timerEventList.remove(event);
			timerEventList.remove(new TimerEvent() {
				@Override
				public void run() {
				}
				
				@Override
				public long nextTime() {
					return 0;
				}
			});
			timerEventList.remove(null);
			timerEventList.add(event);
			timerEventList.remove(event);
			assertTrue(true);
		}
	}
}
