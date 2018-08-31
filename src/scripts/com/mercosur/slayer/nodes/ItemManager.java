package scripts.com.mercosur.slayer.nodes;

import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSItem;
import scripts.com.mercosur.slayer.data.Cache;
import scripts.com.mercosur.slayer.data.RunTimeVariables;
import scripts.com.mercosur.slayer.models.items.AbstractItem;
import scripts.com.mercosur.slayer.models.items.Item;
import scripts.com.mercosur.slayer.models.items.ItemProperty;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.models.items.consumable.Potion;
import scripts.com.mercosur.slayer.nodes.banking.BankingNode;
import scripts.com.mercosur.slayer.nodes.banking.request.BankRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemManager {

	private final Food food;

	private final Potion[] potions;

	private static ItemManager instance;

	private ItemManager() {
		food = RunTimeVariables.SCRIPT_SETTINGS.getFood();
		potions = RunTimeVariables.SCRIPT_SETTINGS.getPotions();
	}

	public void notifyItemChange() {
		if (isLoggedIn()) {
			if (!hasFood()) {
				BankingNode.requestCriticalItemWithdraw(food, BankRequest.ALL);
			}
			if (!hasPotions()) {
				Stream.of(potions).filter(potion -> !hasItemInInventory(potion))
						.forEach(potion -> BankingNode.requestCriticalItemWithdraw(potion, 1));
			}
			if (!hasRequiredSlayerAssignmentItems()) {
				getAllVariationsOfRequiredItems().stream()
						.filter(item -> !hasItemInInventory(item) && !hasItemEquiped(item))
						.forEach(item -> BankingNode.requestCriticalItemWithdraw(item, item.isStackable() ? 300 : 1));
			}
		}
	}

	private boolean isLoggedIn() {
		return Login.getLoginState() == Login.STATE.INGAME;
	}

	private boolean hasItemInInventory(AbstractItem item) {
		if (item.isMutatableName()) {
			return Inventory.find(new Filter<RSItem>() {
				@Override
				public boolean accept(final RSItem rsItem) {
					return rsItem.name.contains(item.getName());
				}
			}).length > 0;
		}
		return Inventory.getCount(item.getName()) > 0;
	}

	private boolean hasItemEquiped(AbstractItem item) {
		return Equipment.find(item.getName()).length > 0;
	}

	private boolean hasFood() {
		return hasItemInInventory(food);
	}

	private boolean hasPotions() {
		return Stream.of(potions).anyMatch(potion -> !hasItemInInventory(potion));
	}

	private List<ItemProperty> getAllRequiredItemProperties() {
		return Arrays.asList(RunTimeVariables.currentSlayerAssignment.getMonster().getRequiredItemProperties());
	}

	private List<Item> getAllVariationsOfRequiredItems() {
		final List<ItemProperty> requiredItemProperties = getAllRequiredItemProperties();
		return Stream.concat(Cache.getContext().getItemStream(), Cache.getContext().getWeaponStream())
				.filter(item -> requiredItemProperties.stream().anyMatch(property -> item.hasProperty(property)))
				.collect(Collectors.toList());
	}

	private boolean hasRequiredSlayerAssignmentItems() {
		if (RunTimeVariables.currentSlayerAssignment != null) {
			return getAllVariationsOfRequiredItems().stream().anyMatch(item -> !hasItemInInventory(item) && !hasItemEquiped(item));
		}
		return false;
	}

	public static ItemManager getInstance() {
		return instance == null ? instance = new ItemManager() : instance;
	}

}
