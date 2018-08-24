package com.mercosur.framework;

public enum NodePriority {

	LAST(0), VERY_LOW(1), LOW(2), MEDIUM(3), HIGH(4), VERY_HIGH(5), CRITICAL(6), FIRST(7);

	private int rank;

	NodePriority(final int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}
}
