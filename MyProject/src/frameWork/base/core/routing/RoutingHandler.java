package frameWork.base.core.routing;

import frameWork.base.core.state.State;

public interface RoutingHandler {
	void get(State state);
	
	void post(State state);
}
