package scripts.com.mercosur.slayer.nodes;

import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;
import scripts.com.mercosur.slayer.util.RunTimeVariables;
import scripts.com.mercosur.slayer.util.Sleep;

public class FightingNode extends Node {

	private final ABCUtil antiban = RunTimeVariables.ANTIBAN;

	private Task currentTask = RunTimeVariables.currentTask;

	private Monster currentMonster = currentTask.getMonster();

	private RSNPC currentTarget;

	public FightingNode() {
		super(NodePriority.VERY_LOW);
	}

	@Override
	public boolean condition() {
		if (currentTask != null) {
			if (currentTarget == null || !currentTarget.isValid()) {
				currentTarget = getNextTarget();
				System.out.println("New target found.");
			}
			return atTask() && !inCombat();
		}
		return false;
	}

	@Override
	public int execute() {
		Sleep.conditionalSleep(new Condition() {
			@Override
			public boolean active() {
				return currentTarget.click() && (inCombat() || canTarget(currentTarget));
			}
		}, 1500, 4000);
		return 100;
	}

	private boolean atTask() {
		return currentMonster.getArea().contains(Player.getPosition());
	}

	private boolean inCombat() {
		return Combat.isUnderAttack() && Combat.getTargetEntity() == null;
	}

	private boolean canTarget(RSNPC npc) {
		return npc != null && npc.isClickable() && npc.isValid() && !npc.isInCombat() && !npc.isInteractingWithMe()
				&& npc.getInteractingCharacter() == null;
	}

	private RSNPC getNextTarget() {
		return (RSNPC) antiban.selectNextTarget(NPCs.findNearest(new Filter<RSNPC>() {
			@Override
			public boolean accept(final RSNPC rsnpc) {
				return canTarget(rsnpc);
			}
		}));
	}

}
