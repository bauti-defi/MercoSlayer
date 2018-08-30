package scripts.com.mercosur.slayer.data;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.slayer.models.SlayerAssignment;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.models.npcs.AttackStyle;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;
import scripts.com.mercosur.slayer.nodes.taskretrieval.TaskPreferences;

public class RunTimeVariables {

	static {
		Cache.getContext();
	}

	public static final ABCUtil ANTIBAN = new ABCUtil();

	public static Monster monster = new Monster("Chicken", 178, null, new RSArea(new RSTile[] {new RSTile(3236, 3293, 0), new RSTile(3236, 3301, 0), new RSTile(3225, 3301, 0), new RSTile(3225, 3294, 0)}), null, null, new AttackStyle[] {AttackStyle.MELEE});

	public static SlayerAssignment currentSlayerAssignment = null;//new SlayerAssignment(monster, 100);

	public static SlayerMaster SLAYER_MASTER = SlayerMaster.VANNAKA;

	public static TaskPreferences TASK_PREFERENCE = TaskPreferences.WEAKEST;

	public static final Food FOOD = new Food("Monkfish");

}
