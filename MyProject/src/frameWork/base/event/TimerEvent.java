package frameWork.event;

public interface TimerEvent extends Runnable {
	long nextTime();
}
