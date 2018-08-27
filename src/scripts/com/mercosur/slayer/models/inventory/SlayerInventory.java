package scripts.com.mercosur.slayer.models.inventory;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSItem;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class SlayerInventory {

	private final LinkedHashMap<Integer, RSItem> inventory = new LinkedHashMap<>();
	private static SlayerInventory instance;

	private SlayerInventory() {
		if (Login.getLoginState() != Login.STATE.INGAME) {
			throw new SlayerInventoryException("Cannot map inventory while logged out.");
		}
		updateInventory();
	}

	private final void updateInventory() {
		inventory.clear();
		Stream.of(Inventory.getAll()).forEachOrdered(rsItem -> inventory.put(rsItem.getIndex(), rsItem));
	}

	public void notifyInventoryChanged() {
		updateInventory();
	}

	public static SlayerInventory getInstance() {
		return instance == null ? instance = new SlayerInventory() : instance;
	}

}
