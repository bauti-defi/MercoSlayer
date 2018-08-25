package scripts.com.mercosur.slayer.util;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;

public class RunTimeVariables {

	public static final ABCUtil ANTIBAN = new ABCUtil();

	public static Monster monster = new Monster("Chicken", 178, null, new RSArea(new RSTile[] {new RSTile(3236, 3293, 0), new RSTile(3236, 3301, 0), new RSTile(3225, 3301, 0), new RSTile(3225, 3294, 0)}), null, new Monster.AttackStyle[] {Monster.AttackStyle.MELEE});

	public static Task currentTask = new Task(monster, 100);

	public static SlayerMaster currentSlayerMaster = new SlayerMaster("name", 1, 1, null);

	public static Food food = new Food("Monkfish");

}
