package com.mercosur.framework;

public abstract class Node {

	private final NodePriority priority;

	public Node(final NodePriority priority) {
		this.priority = priority;
	}

	public NodePriority getPriority(){
		return priority;
	}

	public abstract boolean condition();

	public abstract int execute();

}
