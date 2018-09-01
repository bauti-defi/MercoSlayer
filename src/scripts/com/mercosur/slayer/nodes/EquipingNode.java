package scripts.com.mercosur.slayer.nodes;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.types.RSItem;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.slayer.data.RunTimeVariables;
import scripts.com.mercosur.slayer.models.equipment.SlayerEquipment;

import java.util.HashMap;

public class EquipingNode extends Node {

	private final HashMap<Equipment.SLOTS, RSItem> currentEquipment = new HashMap<>();
	private SlayerEquipment currentPreset = RunTimeVariables.SCRIPT_SETTINGS.getMeleeEquipmentPreset();

	@Override
	public boolean condition() {
		return false;
	}

	@Override
	public Response execute() {
		return null;
	}
}
