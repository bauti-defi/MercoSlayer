package com.mercosur.slayer.util;

import com.mercosur.slayer.models.Task;
import com.mercosur.slayer.models.items.consumable.Food;
import com.mercosur.slayer.models.npcs.SlayerMaster;
import org.tribot.api.util.abc.ABCUtil;

public class RunTimeVariables {

	public static final ABCUtil ANTIBAN = new ABCUtil();

	public static Task currentTask;

	public static SlayerMaster currentSlayerMaster;

	public static Food food;

}
