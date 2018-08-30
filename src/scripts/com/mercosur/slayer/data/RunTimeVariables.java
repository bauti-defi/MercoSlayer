package scripts.com.mercosur.slayer.data;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.slayer.models.SlayerAssignment;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.models.npcs.AttackStyle;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class RunTimeVariables {

	public static final ABCUtil ANTIBAN = new ABCUtil();

	public static Monster monster = new Monster("Chicken", 178, null, new RSArea(new RSTile[] {new RSTile(3236, 3293, 0), new RSTile(3236, 3301, 0), new RSTile(3225, 3301, 0), new RSTile(3225, 3294, 0)}), null, null, new AttackStyle[] {AttackStyle.MELEE});

	public static SlayerAssignment currentSlayerAssignment = null;//new SlayerAssignment(monster, 100);

	public static SlayerMaster currentSlayerMaster = SlayerMaster.VANNAKA;

	public static Food FOOD = new Food("Monkfish");

	public static final List<Task> tasks = new ArrayList<>();


}
