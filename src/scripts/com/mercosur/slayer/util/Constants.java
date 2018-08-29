package scripts.com.mercosur.slayer.util;

import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.slayer.models.npcs.AttackStyle;

import java.util.stream.Stream;

public class Constants {

	public static final String[] MELEE_WEAPON_KEYWORDS = {"sword", "hammer", "whip", "spear", "hasta", "scimitar", "dagger", "maul", "anchor", "arclight",
			"axe", "rapier", "sythe", "bludgeon", "flail", "halberd", "mace", "claws"};

	public static final String[] RANGE_WEAPONS_KEYWORDS = {"bow", "knives", "dart", "blowpipe", "javelin", "ballista"};

	//For later
	private AttackStyle getWeaponAttackStyle(String weaponName) {
		return Stream.of(MELEE_WEAPON_KEYWORDS).anyMatch(keyword -> weaponName.toLowerCase().contains(keyword)) ? AttackStyle.MELEE : AttackStyle.RANGE;
	}

	public static final RSTile VANNAKA_TILE = new RSTile(3146, 9913, 0);

}
