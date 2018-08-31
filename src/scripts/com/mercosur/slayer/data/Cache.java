package scripts.com.mercosur.slayer.data;

import com.google.gson.Gson;
import org.tribot.api.General;
import org.tribot.util.Util;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.items.Item;
import scripts.com.mercosur.slayer.models.items.Weapon;
import scripts.com.mercosur.slayer.models.items.consumable.Food;
import scripts.com.mercosur.slayer.models.items.consumable.Potion;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;
import scripts.com.mercosur.slayer.util.Directory;
import scripts.com.mercosur.slayer.util.InvalidFileNameException;

import java.io.*;
import java.util.stream.Stream;

public class Cache {

	private static Cache instance;

	private final Directory directory;

	private final String cacheName;

	private CacheContext context;

	private Cache(String scriptName) {
		this.directory = new Directory(Util.getWorkingDirectory() + File.separator + "MercosurScripts" + File.separator + scriptName + File.separator + "cache" + File.separator);
		this.cacheName = "cache." + scriptName;
		if (!validate()) {
			rebuild();
		} else {
			loadCache();
		}
	}

	private boolean validate() {
		return directory.exists();
	}

	public void rebuild() {
		try {
			if (directory.delete()) {
				if (directory.create()) {
					downloadCache();
					loadCache();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void downloadCache() {
		//TODO: to implement
	}


	private void loadCache() {
		General.println("Loading cache...");
		File cache = null;
		try {
			cache = directory.getFile(cacheName);
		} catch (InvalidFileNameException e) {
			e.printStackTrace();
			General.println("Downloading cache...");
			downloadCache();
			loadCache();
		}
		if (cache != null && cache.exists()) {
			try (Reader reader = new FileReader(cache)) {
				Gson gson = new Gson();
				context = gson.fromJson(reader, CacheContext.class);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			General.println("Cache loaded.");
		}
	}

	public String getPath() {
		return directory.getPath();
	}

	public static CacheContext getContext() {
		if (instance == null) {
			instance = new Cache(Constants.SCRIPT_NAME);
			return instance.context;
		}
		return instance.context;
	}

	public final class CacheContext {

		private Task[] tasks;

		private Monster[] monsters;

		private Food[] foods;

		private Potion[] potions;

		private Item[] items;

		private Weapon[] weapons;

		public CacheContext() {

		}

		public Task[] getTasks() {
			return tasks;
		}

		public Stream<Task> getTaskStream() {
			return Stream.of(tasks);
		}

		public Monster[] getMonsters() {
			return monsters;
		}

		public Stream<Monster> getMonsterStream() {
			return Stream.of(monsters);
		}

		public Food[] getFoods() {
			return foods;
		}

		public Stream<Food> getFoodStream() {
			return Stream.of(foods);
		}

		public Potion[] getPotions() {
			return potions;
		}

		public Stream<Potion> getPotionStream() {
			return Stream.of(potions);
		}

		public Item[] getItems() {
			return items;
		}

		public Stream<Item> getItemStream() {
			return Stream.of(items);
		}

		public Weapon[] getWeapons() {
			return weapons;
		}

		public Stream<Weapon> getWeaponStream() {
			return Stream.of(weapons);
		}
	}
}
