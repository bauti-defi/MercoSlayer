package scripts.com.mercosur.slayer.models.npcs;

import org.tribot.api2007.types.RSTile;

public class SlayerMaster {

	private final String name;

	private final int requiredSlayerLevel;

	private final int requiredCombat;

	private final RSTile location;

	public SlayerMaster(final String name, final int requiredSlayerLevel, final int requiredCombat, final RSTile location) {
		this.name = name;
		this.requiredSlayerLevel = requiredSlayerLevel;
		this.requiredCombat = requiredCombat;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public int getRequiredSlayerLevel() {
		return requiredSlayerLevel;
	}

	public int getRequiredCombat() {
		return requiredCombat;
	}

	public RSTile getLocation() {
		return location;
	}
}
