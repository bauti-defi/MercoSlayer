package scripts.com.mercosur.slayer.nodes.taskretrieval;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import scripts.com.mercosur.dax_api.api_lib.DaxWalker;
import scripts.com.mercosur.dax_api.walker_engine.WalkingCondition;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.models.SlayerAssignment;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.util.RunTimeVariables;
import scripts.com.mercosur.slayer.util.Sleep;

public class RetrieveTaskNode extends Node {

	private SlayerAssignment currentSlayerAssignment = RunTimeVariables.currentSlayerAssignment;

	private SlayerMaster currentSlayerMaster = RunTimeVariables.currentSlayerMaster;

	public RetrieveTaskNode() {
		super(NodePriority.HIGH);
	}

	@Override
	public boolean condition() {
		if (currentSlayerAssignment == null) {
			if (closeToSlayerMasterLocation()) {
				return true;
			} else {
				General.println("Walking to " + currentSlayerMaster.getName());
				DaxWalker.walkTo(currentSlayerMaster.getLocation(), () -> {
					if (closeToSlayerMasterLocation()) {
						return WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS;
					}
					return WalkingCondition.State.CONTINUE_WALKER;
				});
			}
		}
		return false;
	}

	@Override
	public Node.Response execute() {
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
			} else if (master.isClickable() && master.isValid()) {
				if (!isInDialogue()) {
					if (!Sleep.conditionalSleep(new Condition() {
						@Override
						public boolean active() {
							return master.click("Assignment") && isInDialogue();
						}
					}, 5000, 7000)) {
						throw new TaskRenewalException("Failed to enter dialogue with " + currentSlayerMaster.getName());
					}
				} else {
					final String assignmentMessage = NPCChat.getMessage();
				}
			}
		}
		return Response.CONTINUE;
	}

	public static boolean isInDialogue() {
		return !(NPCChat.getName() == null && NPCChat.getClickContinueInterface() == null
				&& NPCChat.getSelectOptionInterface() == null);
	}

	private boolean closeToSlayerMasterLocation() {
		return currentSlayerMaster.getLocation().distanceTo(Player.getPosition()) <= 15;
	}

	private boolean closeToSlayerMaster(RSNPC master) {
		return master.getPosition().distanceTo(Player.getPosition()) <= 8;
	}
}
