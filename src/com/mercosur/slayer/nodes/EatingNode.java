package com.mercosur.slayer.nodes;

import com.mercosur.framework.Node;
import com.mercosur.framework.NodePriority;
import com.mercosur.slayer.models.items.consumable.Food;
import com.mercosur.slayer.nodes.banking.BankingNode;
import com.mercosur.slayer.util.RunTimeVariables;
import com.mercosur.slayer.util.Sleep;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import java.util.stream.Stream;

public class EatingNode extends Node {

	private final Food food = RunTimeVariables.food;

	private int eatAtHP = RunTimeVariables.ANTIBAN.generateEatAtHP();

	public EatingNode() {
		super(NodePriority.CRITICAL);
	}

	@Override
	public boolean condition() {
		if (hasFood()) {
			return needsToEat();
		} else {
			BankingNode.requestCriticalItemWithdraw(food, 10);
		}
		return false;
	}

	@Override
	public int execute() {
		final RSItem food = Stream.of(Inventory.find(this.food.getName())).findAny().orElseThrow(() -> new NullPointerException("No food found"));
		if (food != null) {
			if (Sleep.conditionalSleep(new Condition() {
				@Override
				public boolean active() {
					return food.click("Eat");
				}
			}, 500, 2000)) {
				generateNewEatAtHP();
			}
		}
		return 150;
	}

	private void generateNewEatAtHP() {
		eatAtHP = RunTimeVariables.ANTIBAN.generateEatAtHP();
		System.out.println("Next eat at: " + eatAtHP);
	}

	private boolean needsToEat() {
		return eatAtHP <= Combat.getHP();
	}

	private boolean hasFood() {
		return Inventory.getCount(food.getName()) > 0;
	}
}
