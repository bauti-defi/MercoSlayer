package scripts.com.mercosur.slayer.models.npcs;

import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.slayer.models.travel.SlayerRegion;

public class SlayerMaster {//turn this into an enum

	private final String name;

	private final int requiredSlayerLevel;

	private final int requiredCombat;

	private final SlayerRegion slayerRegion;

	private final RSTile location;

	public SlayerMaster(final String name, final int requiredSlayerLevel, final int requiredCombat, final SlayerRegion slayerRegion, final RSTile location) {
		this.name = name;
		this.requiredSlayerLevel = requiredSlayerLevel;
		this.requiredCombat = requiredCombat;
		this.slayerRegion = slayerRegion;
		this.location = location;
	}

	public SlayerRegion getSlayerRegion() {
		return slayerRegion;
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
