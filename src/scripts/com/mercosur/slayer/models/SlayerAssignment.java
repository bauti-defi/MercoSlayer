package scripts.com.mercosur.slayer.models;

import scripts.com.mercosur.slayer.models.npcs.monster.Monster;

public class SlayerAssignment {

	private final Monster monster;

	private final int assignedAmount;

	private int killsLeft;

	public SlayerAssignment(final Monster monster, final int assignedAmount) {
		this.monster = monster;
		this.assignedAmount = assignedAmount;
		this.killsLeft = assignedAmount;
	}

	public Monster getMonster() {
		return monster;
	}

	public int getAssignedAmount() {
		return assignedAmount;
	}

	public int getKillsLeft() {
		return killsLeft;
	}
}
