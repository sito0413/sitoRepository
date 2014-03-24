package frameWork.event.timerEvent;

import frameWork.event.TimerEvent;

class TimerEventNode {
	TimerEvent item;
	TimerEventNode next;
	
	TimerEventNode(final TimerEvent x) {
		item = x;
	}
}