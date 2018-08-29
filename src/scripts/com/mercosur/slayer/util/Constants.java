package scripts.com.mercosur.slayer.util;

import scripts.com.mercosur.slayer.models.npcs.AttackStyle;
import scripts.com.mercosur.slayer.models.travel.RouteItem;

import java.util.stream.Stream;

public class Constants {

	public static final String[] MELEE_WEAPON_KEYWORDS = {"sword", "hammer", "whip", "spear", "hasta", "scimitar", "dagger", "maul", "anchor", "arclight",
			"axe", "rapier", "sythe", "bludgeon", "flail", "halberd", "mace", "claws"};

	public static final String[] RANGE_WEAPONS_KEYWORDS = {"bow", "knives", "dart", "blowpipe", "javelin", "ballista"};

	//For later
	private AttackStyle getWeaponAttackStyle(String weaponName) {
		return Stream.of(MELEE_WEAPON_KEYWORDS).anyMatch(keyword -> weaponName.toLowerCase().contains(keyword)) ? AttackStyle.MELEE : AttackStyle.RANGE;
	}

	public static final RouteItem AMULET_OF_GLORY = new RouteItem("Amulet of Glory", true, false, 1);

	public static final RouteItem COINS = new RouteItem("Coins", false, true, 2000);

	public static final RouteItem ECTOPHIAL = new RouteItem("Ectophial", false, false, 1);

	public static final RouteItem HOUSE_TELEPORT_TAB = new RouteItem("House Teleport", false, true, 4); //Any house teleport action with this
}
