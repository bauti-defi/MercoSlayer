package scripts.com.mercosur.slayer.models.equipment;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSItem;

import java.util.HashMap;
import java.util.stream.Stream;

public class SlayerEquipment {

	private final HashMap<Equipment.SLOTS, RSItem> equipment = new HashMap<>();
	private static SlayerEquipment instance;

	private SlayerEquipment() {
		if (Login.getLoginState() != Login.STATE.INGAME) {
			throw new SlayerEquipmentException("Cannot map equipment while logged out.");
		}

	}

	private final void updateEquipment() {
		equipment.clear();
		Stream.of(Equipment.getItems()).forEach(rsItem -> equipment.put(rsItem.getEquipmentSlot(), rsItem));
	}

	public HashMap<Equipment.SLOTS, RSItem> getUpdatedEquipment() {
		updateEquipment();
		return equipment;
	}

	private final void updateEquipment(Equipment.SLOTS slot) {
		equipment.put(slot, Equipment.getItem(slot));
	}

	public void notifyEquipmentChanged(Equipment.SLOTS slot) {
		updateEquipment(slot);
	}

	public void notifyEquipmentChanged() {
		updateEquipment();
	}

	public static SlayerEquipment getInstance() {
		return instance == null ? instance = new SlayerEquipment() : instance;
	}

}
