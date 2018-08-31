package scripts.com.mercosur.slayer.data;

import com.google.gson.Gson;
import org.tribot.util.Util;
import scripts.com.mercosur.slayer.models.equipment.SlayerEquipment;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.models.items.consumable.Potion;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.nodes.taskretrieval.TaskPreferences;
import scripts.com.mercosur.slayer.util.Directory;

import java.io.*;

public class ScriptSettings {

	private transient static final Directory directory = new Directory(Util.getWorkingDirectory() + File.separator + "MercosurScripts" + File.separator + Constants.SCRIPT_NAME + File.separator + "settings" + File.separator);

	private SlayerEquipment meleeEquipmentPreset, rangeEquipmentPreset, magicEquipmentPreset;

	private SlayerMaster slayerMaster;

	private TaskPreferences taskPreferences;

	private Food food;

	private Potion[] potions;

	private ScriptSettings(Builder builder) {
		if (!validate()) {
			if (!rebuild()) {
				throw new SlayerDataException("Failed to rebuild settings directory");
			}
		}
		this.meleeEquipmentPreset = builder.meleeEquipmentPreset;
		this.rangeEquipmentPreset = builder.rangeEquipmentPreset;
		this.magicEquipmentPreset = builder.magicEquipmentPreset;
		this.slayerMaster = builder.slayerMaster;
		this.taskPreferences = builder.taskPreferences;
		this.food = builder.food;
		this.potions = builder.potions;
	}

	public ScriptSettings() {

	}

	private boolean validate() {
		return directory.exists();
	}

	public boolean rebuild() {
		try {
			if (directory.delete()) {
				return directory.create();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public final void save(String profile) {
		try (Writer writer = new FileWriter(profile + ".profile")) {
			Gson gson = new Gson();
			gson.toJson(this, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SlayerEquipment getMeleeEquipmentPreset() {
		return meleeEquipmentPreset;
	}

	public SlayerEquipment getRangeEquipmentPreset() {
		return rangeEquipmentPreset;
	}

	public SlayerEquipment getMagicEquipmentPreset() {
		return magicEquipmentPreset;
	}

	public SlayerMaster getSlayerMaster() {
		return slayerMaster;
	}

	public TaskPreferences getTaskPreferences() {
		return taskPreferences;
	}

	public Potion[] getPotions() {
		return potions;
	}

	public Food getFood() {
		return food;
	}

	public static ScriptSettings load(String profile) {
		try (Reader reader = new FileReader(profile + ".profile")) {
			Gson gson = new Gson();
			return gson.fromJson(reader, ScriptSettings.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class Builder {

		private SlayerEquipment meleeEquipmentPreset, rangeEquipmentPreset, magicEquipmentPreset;

		private final SlayerMaster slayerMaster;

		private TaskPreferences taskPreferences = TaskPreferences.WEAKEST;

		private final Food food;

		private Potion[] potions;

		public Builder(final SlayerMaster slayerMaster, final Food food) {
			this.slayerMaster = slayerMaster;
			this.food = food;
		}

		public ScriptSettings.Builder setTaskPreferences(final TaskPreferences taskPreferences) {
			this.taskPreferences = taskPreferences;
			return this;
		}

		public ScriptSettings.Builder setMeleeEquipmentPreset(final SlayerEquipment meleeEquipmentPreset) {
			this.meleeEquipmentPreset = meleeEquipmentPreset;
			return this;
		}

		public ScriptSettings.Builder setRangeEquipmentPreset(final SlayerEquipment rangeEquipmentPreset) {
			this.rangeEquipmentPreset = rangeEquipmentPreset;
			return this;
		}

		public ScriptSettings.Builder setMagicEquipmentPreset(final SlayerEquipment magicEquipmentPreset) {
			this.magicEquipmentPreset = magicEquipmentPreset;
			return this;
		}

		public ScriptSettings.Builder setPotions(Potion... potions) {
			this.potions = potions;
			return this;
		}

		public ScriptSettings build() {
			return new ScriptSettings(this);
		}
	}

}
