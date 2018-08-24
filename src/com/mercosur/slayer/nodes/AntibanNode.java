package com.mercosur.slayer.nodes;

import com.mercosur.framework.Node;
import com.mercosur.framework.NodePriority;
import com.mercosur.slayer.util.RunTimeVariables;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;

public class AntibanNode extends Node {

	private final static ABCUtil antiban = RunTimeVariables.ANTIBAN;

	private int runActivationFlag;

	public AntibanNode() {
		super(NodePriority.LAST);
	}

	@Override
	public boolean condition() {
		if (!Game.isRunOn() && runActivationFlag == 0) {
			runActivationFlag = antiban.generateRunActivation();
		}
		return !Game.isRunOn() && Game.getRunEnergy() >= runActivationFlag;
	}

	@Override
	public int execute() {
		if (!Game.isRunOn() && Game.getRunEnergy() >= runActivationFlag) {
			Options.setRunEnabled(true);
			runActivationFlag = 0;
		}
		antiban.generateTrackers();
		return 150;
	}
}
