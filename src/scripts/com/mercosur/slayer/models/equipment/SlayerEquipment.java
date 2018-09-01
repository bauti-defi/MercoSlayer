package scripts.com.mercosur.slayer.models.equipment;

import org.tribot.api2007.Equipment;
import scripts.com.mercosur.slayer.models.items.Item;
import scripts.com.mercosur.slayer.models.items.Weapon;

import java.util.HashMap;

public class SlayerEquipment {

	private HashMap<Equipment.SLOTS, Item> equipment;

	private Weapon shieldCompatibleWeapon;

	private Weapon nonShieldCompatibleWeapon;

	private Item shield;

	public SlayerEquipment() {

	}

}
