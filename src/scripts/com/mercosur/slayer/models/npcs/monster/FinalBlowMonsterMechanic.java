package scripts.com.mercosur.slayer.models.npcs.monster;

public enum FinalBlowMonsterMechanic {

	/*monster mechanic
	final blow item ->  an action
	required armor/wep -> boolean
	 */

	THROW_SALT(null), BREAK_WITH_HAMMER(null);

	private final MonsterMechanic monsterMechanic;

	FinalBlowMonsterMechanic(final MonsterMechanic monsterMechanic) {
		this.monsterMechanic = monsterMechanic;
	}

	public boolean activate() {
		return monsterMechanic.activate();
	}

	public boolean execute() {
		return monsterMechanic.execute();
	}
}
