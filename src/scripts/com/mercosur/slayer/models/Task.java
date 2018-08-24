package scripts.com.mercosur.slayer.models;

import scripts.com.mercosur.slayer.models.npcs.monster.Monster;

public class Task {

	private final Monster monster;

	private final int assignedAmount;

	private int currentCount;

	public Task(final Monster monster, final int assignedAmount) {
		this.monster = monster;
		this.assignedAmount = assignedAmount;
		this.currentCount = this.assignedAmount;
	}

	public Monster getMonster() {
		return monster;
	}

	public int getAssignedAmount() {
		return assignedAmount;
	}

	public int getCurrentCount() {
		return currentCount;
	}

}
