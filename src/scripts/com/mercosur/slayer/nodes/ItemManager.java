package scripts.com.mercosur.slayer.nodes;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.models.SlayerAssignment;
import scripts.com.mercosur.slayer.models.items.AbstractItem;
import scripts.com.mercosur.slayer.models.items.Item;
import scripts.com.mercosur.slayer.models.items.ItemProperty;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.util.RunTimeVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemManager extends Node {

	//All mapped items
	public static final List<AbstractItem> ITEMS = new ArrayList<>();

	private final Food food = RunTimeVariables.food;

	private SlayerAssignment currentSlayerAssignment = RunTimeVariables.currentSlayerAssignment;

	public ItemManager() {
		super(NodePriority.CRITICAL);
	}

	@Override
	public boolean condition() {
		if (isLoggedIn()) {
			return !hasFood() || !hasRequiredSlayerAssignmentItems();
		}
		return false;
	}

	@Override
	public Response execute() {
		return Response.CONTINUE;
	}

	private boolean isLoggedIn() {
		return Login.getLoginState() == Login.STATE.INGAME;
	}

	private boolean hasItemInInventory(AbstractItem item) {
		return Inventory.getCount(item.getName()) > 0;
	}

	private boolean hasItemEquiped(AbstractItem item) {
		return Equipment.find(item.getName()).length > 0;
	}

	private boolean hasFood() {
		return hasItemInInventory(food);
	}

	private List<ItemProperty> getAllRequiredItemProperties() {
		return Arrays.asList(currentSlayerAssignment.getMonster().getRequiredItemProperties());
	}

	private List<Item> getAllVariationsOfRequiredItems() {
		final List<ItemProperty> requiredItemProperties = getAllRequiredItemProperties();
		return ITEMS.stream().filter(abstractItem -> abstractItem instanceof Item)
				.map(abstractItem -> (Item) abstractItem)
				.filter(item -> requiredItemProperties.contains(item.getProperties()))
				.collect(Collectors.toList());
	}

	private boolean hasRequiredSlayerAssignmentItems() {
		if (currentSlayerAssignment != null) {
			return getAllVariationsOfRequiredItems().stream().anyMatch(item -> !hasItemInInventory(item) && !hasItemEquiped(item));
		}
		return false;
	}
}
