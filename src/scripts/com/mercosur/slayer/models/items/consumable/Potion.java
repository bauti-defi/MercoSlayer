package scripts.com.mercosur.slayer.models.items.consumable;

import org.tribot.api2007.Skills;
import scripts.com.mercosur.slayer.models.items.AbstractItem;

public class Potion extends AbstractItem {

	private final Type type;
	private final Skills.SKILLS[] skills;

	enum Type {
		BUFF, RESTORATION
	}

	public Potion(final String name, Type type, Skills.SKILLS... skills) {
		super(name, false);
		this.type = type;
		this.skills = skills;
	}

	public Skills.SKILLS[] getSkills() {
		return skills;
	}

	public Type getType() {
		return type;
	}
}
