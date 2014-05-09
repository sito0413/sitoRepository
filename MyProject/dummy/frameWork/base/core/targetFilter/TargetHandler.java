package frameWork.base.core.targetFilter;

import frameWork.base.core.state.State;

public interface TargetHandler {
	void get(State state);
	
	void post(State state);
}
