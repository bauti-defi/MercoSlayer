package scripts.com.mercosur.slayer.models;

import scripts.com.mercosur.slayer.models.npcs.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class Task {

	private final String name;

	private List<Monster> monsters = new ArrayList<>();

	public Task(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	@Override
	public String toString() {
		return name;
	}
}
