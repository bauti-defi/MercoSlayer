package scripts.com.mercosur.slayer.nodes;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.slayer.data.RunTimeVariables;

public class AntibanNode extends Node {

	private final static ABCUtil antiban = RunTimeVariables.ANTIBAN;

	private int runActivationFlag;

	@Override
	public boolean condition() {
		if (!Game.isRunOn() && runActivationFlag == 0) {
			runActivationFlag = antiban.generateRunActivation();
		}
		return !Game.isRunOn() && Game.getRunEnergy() >= runActivationFlag;
	}

	@Override
	public Node.Response execute() {
		if (!Game.isRunOn() && Game.getRunEnergy() >= runActivationFlag) {
			Options.setRunEnabled(true);
			runActivationFlag = 0;
		}
		antiban.generateTrackers();
		return Response.CONTINUE;
	}
}
