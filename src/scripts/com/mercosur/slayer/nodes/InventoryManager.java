package scripts.com.mercosur.slayer.nodes;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.util.RunTimeVariables;

public class InventoryManager extends Node {

	private final Food food = RunTimeVariables.food;

	public InventoryManager() {
		super(NodePriority.CRITICAL);
	}

	@Override
	public boolean condition() {
		if (isLoggedIn()) {
			return !hasFood();
		}
		return false;
	}

	@Override
	public Response execute() {
		return null;
	}

	private boolean isLoggedIn() {
		return Login.getLoginState() == Login.STATE.INGAME;
	}

	private boolean hasFood() {
		return Inventory.getCount(food.getName()) > 0;
	}
}
