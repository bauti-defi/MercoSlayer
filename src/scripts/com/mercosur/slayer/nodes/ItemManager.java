package scripts.com.mercosur.slayer.nodes;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.data.Cache;
import scripts.com.mercosur.slayer.data.RunTimeVariables;
import scripts.com.mercosur.slayer.models.SlayerAssignment;
import scripts.com.mercosur.slayer.models.items.AbstractItem;
import scripts.com.mercosur.slayer.models.items.Item;
import scripts.com.mercosur.slayer.models.items.ItemProperty;
import scripts.com.mercosur.slayer.models.items.consumable.Food;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemManager extends Node {

	private final Food food = RunTimeVariables.FOOD;

	private SlayerAssignment currentSlayerAssignment = RunTimeVariables.currentSlayerAssignment;

	public ItemManager() {
		super(NodePriority.CRITICAL);
	}

	@Override
	public boolean condition() {
		if (isLoggedIn()) {
			return !hasFood() || !hasPotions() || !hasRequiredSlayerAssignmentItems();
		}
		return false;
	}

	@Override
	public Response execute() {
		//calculate inventory and equipmet

		//send bank requests to banking node accordingly
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

	private boolean hasPotions() {
		return false;
	}

	private List<ItemProperty> getAllRequiredItemProperties() {
		return Arrays.asList(currentSlayerAssignment.getMonster().getRequiredItemProperties());
	}

	private List<Item> getAllVariationsOfRequiredItems() {
		final List<ItemProperty> requiredItemProperties = getAllRequiredItemProperties();
		return Stream.concat(Cache.getContext().getItemStream(), Cache.getContext().getWeaponStream())
				.filter(item -> requiredItemProperties.stream().anyMatch(property -> item.hasProperty(property)))
				.collect(Collectors.toList());
	}

	private boolean hasRequiredSlayerAssignmentItems() {
		if (currentSlayerAssignment != null) {
			return getAllVariationsOfRequiredItems().stream().anyMatch(item -> !hasItemInInventory(item) && !hasItemEquiped(item));
		}
		return false;
	}

}
