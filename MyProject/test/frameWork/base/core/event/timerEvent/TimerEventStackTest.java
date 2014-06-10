package frameWork.base.core.event.timerEvent;

import static org.junit.Assert.*;

import org.junit.Test;

import frameWork.base.core.event.TimerEvent;

public class TimerEventStackTest {
	
	@Test
	public void test() {
		final TimerEvent event = new TimerEvent() {
			
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		};
		final TimerEventStack stack = new TimerEventStack();
		stack.add(new TimerEvent() {
			
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		});
		stack.add(event);
		stack.add(new TimerEvent() {
			
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		});
		assertTrue(stack.contains(event));
		assertFalse(stack.contains(new TimerEvent() {
			
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		}));
		assertFalse(stack.contains(null));
		stack.remove(event);
		stack.remove(new TimerEvent() {
			
			@Override
			public void run() {
			}
			
			@Override
			public long nextTime() {
				return 0;
			}
		});
		stack.remove(null);
		stack.add(event);
		stack.remove(event);
		assertFalse(stack.contains(event));
	}
}
