package scripts.com.mercosur.slayer.nodes.taskrenewal;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.util.RunTimeVariables;
import scripts.com.mercosur.slayer.util.Sleep;

public class RenewTaskNode extends Node {

	private Task currentTask = RunTimeVariables.currentTask;

	private SlayerMaster currentSlayerMaster = RunTimeVariables.currentSlayerMaster;

	public RenewTaskNode() {
		super(NodePriority.HIGH);
	}

	@Override
	public boolean condition() {
		if (currentSlayerMaster != null) {
			return currentTask == null;
		}
		return false;
	}

	@Override
	public int execute() {
		final RSNPC master = NPCs.find(currentSlayerMaster.getName())[0];
		if (master != null) {
			if (!closeToSlayerMaster(master)) {
				if (!Walking.blindWalkTo(master.getPosition(), new Condition() {
					@Override
					public boolean active() {
						return closeToSlayerMaster(master);
					}
				}, General.randomLong(7000, 9000))) {
					throw new TaskRenewalException("Failed to get closer to " + currentSlayerMaster.getName());
				}
			} else if (master.isClickable() && master.isValid() && !isInDialogue()) {
				if (!Sleep.conditionalSleep(new Condition() {
					@Override
					public boolean active() {
						return master.click("Assignment") && isInDialogue();
					}
				}, 2000, 5000)) {
					throw new TaskRenewalException("Failed to enter dialogue with " + currentSlayerMaster.getName());
				}
			} else if (isInDialogue()) {
				//traverse dialogue with master
			}
		}
		return 150;
	}

	private boolean isInDialogue() {
		return NPCChat.getMessage() != null;
	}

	private boolean closeToSlayerMaster(RSNPC master) {
		return master.getPosition().distanceTo(Player.getPosition()) <= 5;
	}
}
