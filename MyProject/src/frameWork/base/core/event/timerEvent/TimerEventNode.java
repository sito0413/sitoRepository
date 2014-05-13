package frameWork.base.core.event.timerEvent;

import frameWork.base.core.event.TimerEvent;

class TimerEventNode {
	TimerEvent item;
	TimerEventNode next;
	
	TimerEventNode(final TimerEvent x) {
		item = x;
	}
}