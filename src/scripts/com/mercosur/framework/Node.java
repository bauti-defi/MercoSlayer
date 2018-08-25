package scripts.com.mercosur.framework;

public abstract class Node {

	private final NodePriority priority;

	public enum Response {
		LOOP, CONTINUE
	}

	public Node(final NodePriority priority) {
		this.priority = priority;
	}

	public NodePriority getPriority(){
		return priority;
	}

	public abstract boolean condition();

	public abstract Response execute();

}
