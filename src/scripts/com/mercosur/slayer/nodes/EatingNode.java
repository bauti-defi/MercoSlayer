package scripts.com.mercosur.slayer.nodes;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.slayer.data.RunTimeVariables;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.util.Sleep;

import java.util.stream.Stream;

public class EatingNode extends Node {

	private final Food food = RunTimeVariables.SCRIPT_SETTINGS.getFood();

	private int eatAtHP = RunTimeVariables.ANTIBAN.generateEatAtHP();

	@Override
	public boolean condition() {
		if (hasFood()) {
			return needsToEat();
		}
		return false;
	}

	@Override
	public Node.Response execute() {
		final RSItem food = Stream.of(Inventory.find(this.food.getName())).findAny().orElseThrow(() -> new NullPointerException("No Food found"));
		if (food != null) {
			if (Sleep.conditionalSleep(new Condition() {
				@Override
				public boolean active() {
					return food.click("Eat");
				}
			}, 500, 2000)) {
				generateNewEatAtHP();
				return Response.LOOP;//in case we need to quickly eat again.
			}
		}
		return Response.CONTINUE;
	}

	private void generateNewEatAtHP() {
		eatAtHP = RunTimeVariables.ANTIBAN.generateEatAtHP();
		System.out.println("Next eat at: " + eatAtHP);
	}

	private boolean needsToEat() {
		return eatAtHP <= Combat.getHP();
	}

	private boolean hasFood() {
		return Inventory.getCount(food.getName()) > 0;
	}
}
