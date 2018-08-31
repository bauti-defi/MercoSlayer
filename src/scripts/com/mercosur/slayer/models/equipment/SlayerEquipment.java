package scripts.com.mercosur.slayer.models.equipment;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.types.RSItem;

import java.util.HashMap;

public class SlayerEquipment {

	private HashMap<Equipment.SLOTS, RSItem> equipment;

	private SlayerEquipment(final HashMap<Equipment.SLOTS, RSItem> equipment) {
		this.equipment = equipment;
	}

	public SlayerEquipment() {

	}

}
