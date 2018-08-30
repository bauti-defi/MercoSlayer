package scripts.com.mercosur.slayer.data;

import com.google.gson.Gson;
import org.tribot.api.General;
import org.tribot.util.Util;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.items.Item;
import scripts.com.mercosur.slayer.models.items.Weapon;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;
import scripts.com.mercosur.slayer.util.Directory;
import scripts.com.mercosur.slayer.util.InvalidFileNameException;

import java.io.*;

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
			try {
				FileInputStream fileInputStream = new FileInputStream(cache);
				InputStreamReader isr = new InputStreamReader(fileInputStream);
				BufferedReader bufferedReader = new BufferedReader(isr);
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}
				String json = sb.toString();
				Gson gson = new Gson();
				context = gson.fromJson(json, CacheContext.class);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			General.println("Cache loaded.");
		}
	}

	private Directory getDirectory() {
		return directory;
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

	private final class CacheContext {

		private Task[] tasks;

		private Monster[] monsters;

		private Item[] items;

		private Weapon[] weapons;

		public CacheContext() {

		}

		public Task[] getTasks() {
			return tasks;
		}

		public Monster[] getMonsters() {
			return monsters;
		}

		public Item[] getItems() {
			return items;
		}

		public Weapon[] getWeapons() {
			return weapons;
		}
	}
}
