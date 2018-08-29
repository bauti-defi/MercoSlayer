package scripts.com.mercosur.slayer.models.npcs.monster;

public enum FinalBlowMonsterMechanic {

	NONE(null), THROW_SALT(null), BREAK_WITH_HAMMER(null), THROW_ICE(null);

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
