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
import scripts.com.mercosur.slayer.data.Cache;
import scripts.com.mercosur.slayer.data.RunTimeVariables;
import scripts.com.mercosur.slayer.models.SlayerAssignment;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.npcs.SlayerMaster;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;
import scripts.com.mercosur.slayer.util.Sleep;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RetrieveTaskNode extends Node {

	private SlayerAssignment currentSlayerAssignment = RunTimeVariables.currentSlayerAssignment;

	private SlayerMaster currentSlayerMaster = RunTimeVariables.SLAYER_MASTER;

	private TaskPreferences taskPreferences = RunTimeVariables.TASK_PREFERENCE;

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
					final String taskName = parseTaskName(assignmentMessage);
					final int killsRequired = parseTaskKillsRequired(assignmentMessage);
					final Task assignedTask = Stream.of(Cache.getContext().getTasks())
							.filter(task -> task.getName().equalsIgnoreCase(taskName))
							.findFirst().orElseThrow(() -> new TaskRenewalException("Task: " + taskName + " not supported"));

					RunTimeVariables.currentSlayerAssignment = currentSlayerAssignment = new SlayerAssignment(getOptimalMonsterForTask(assignedTask), killsRequired);
					General.println("New task: " + currentSlayerAssignment.getAssignedAmount() + " " + currentSlayerAssignment.getMonster().getName());
				}
			}
		}
		return Response.CONTINUE;
	}

	private String parseTaskName(String message) {
		return message;//TODO: correctly parse task name from message
	}

	private int parseTaskKillsRequired(String message) {
		return 100;//TODO: correctly parse task amount from message
	}

	private final Monster getOptimalMonsterForTask(Task task) {
		if (task.getMonsters().isEmpty()) {
			throw new NullPointerException("Task options null.");
		} else if (task.getMonsters().size() == 1) {
			return task.getMonsters().get(0);
		}

		List<Monster> monsterStream = task.getMonsters().stream().sorted((monster1, monster2) -> {
			if (monster1.getLevel() > monster2.getLevel()) {
				return -1;
			} else if (monster1.getLevel() < monster2.getLevel()) {
				return 1;
			}
			return 0;
		}).collect(Collectors.toList());//TODO: Make better preference options

		switch (taskPreferences) {
			case WEAKEST:
				return monsterStream.get(0);
			case MIDDLE:
				if (monsterStream.size() > 2) {
					return monsterStream.get(1);
				}
			case STRONGEST:
				return monsterStream.get(monsterStream.size() - 1);
			default:
				return monsterStream.get(0);
		}

	}

	/*
	Priority options
	-points (lowest cb)
	-cash (dynamic)
	-exp (most exp per kill)
	 */

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
