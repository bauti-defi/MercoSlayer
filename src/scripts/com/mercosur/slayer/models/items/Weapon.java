package scripts.com.mercosur.slayer.models.items;

import scripts.com.mercosur.slayer.models.npcs.AttackStyle;

public class Weapon extends Item {

	private final AttackStyle attackStyle;

	public Weapon(final String name, final boolean mutatableName, final boolean stackable, final AttackStyle attackStyle, final ItemProperty... properties) {
		super(name, mutatableName, stackable, true, properties);
		this.attackStyle = attackStyle;
	}

	public AttackStyle getAttackStyle() {
		return attackStyle;
	}
}
