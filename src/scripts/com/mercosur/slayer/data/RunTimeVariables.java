package scripts.com.mercosur.slayer.data;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.slayer.models.SlayerAssignment;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.models.npcs.AttackStyle;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;

public class RunTimeVariables {

	static {
		Cache.getContext();
		ANTIBAN = new ABCUtil();


		//*********TESTING VARIABLES*********
		SCRIPT_SETTINGS = new ScriptSettings.Builder(SlayerMaster.VANNAKA, new Food("Monkfish")).build();
		//currentSlayerAssignment = new SlayerAssignment(monster, 100);
		monster = new Monster("Chicken", 178, null, new RSArea(new RSTile[] {new RSTile(3236, 3293, 0), new RSTile(3236, 3301, 0), new RSTile(3225, 3301, 0), new RSTile(3225, 3294, 0)}), null, null, new AttackStyle[] {AttackStyle.MELEE});
	}

	public static final ABCUtil ANTIBAN;

	public static ScriptSettings SCRIPT_SETTINGS;

	public static Monster monster;

	public static SlayerAssignment currentSlayerAssignment;
}
