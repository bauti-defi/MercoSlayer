package scripts.com.mercosur.framework;

public abstract class Node {

	public enum Response {
		LOOP, CONTINUE
	}

	public abstract boolean condition();

	public abstract Response execute();

}
