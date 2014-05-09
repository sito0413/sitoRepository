package frameWork.base.event.timerEvent;

import frameWork.base.event.TimerEvent;

class TimerEventNode {
	TimerEvent item;
	TimerEventNode next;
	
	TimerEventNode(final TimerEvent x) {
		item = x;
	}
}