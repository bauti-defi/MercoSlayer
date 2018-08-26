package scripts.com.mercosur.slayer.script.nodes;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.dax_api.api_lib.DaxWalker;
import scripts.com.mercosur.dax_api.walker_engine.WalkingCondition;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.models.Task;
import scripts.com.mercosur.slayer.models.npcs.monster.Monster;
import scripts.com.mercosur.slayer.util.RunTimeVariables;
import scripts.com.mercosur.slayer.util.Sleep;

import java.util.stream.Stream;

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
		if (!Combat.isAutoRetaliateOn()) {
			General.println("Enabling auto retaliate.");
			Combat.setAutoRetaliate(true);
			Sleep.conditionalSleep(new Condition() {
				@Override
				public boolean active() {
					return Combat.isAutoRetaliateOn();
				}
			}, 2000, 3000);
		}
		if (currentTask != null) {
			if (!inCombat()) {
				if (currentTarget == null || !currentTarget.isValid() || !canTarget(currentTarget)) {
					if ((currentTarget = getNextTarget()) != null) {
						General.println("Next " + currentMonster.getName() + " found");
					} else {
						General.println("Walking to task: " + currentTask.getMonster().getName());
						DaxWalker.walkTo(currentMonster.getArea().getRandomTile(), () -> {
							if (atTask()) {
								return WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS;
							}
							return WalkingCondition.State.CONTINUE_WALKER;
						});
					}
				} else if (currentTarget != null) {
					return atTask();
				}
			}
		}
		return false;
	}

	@Override
	public Node.Response execute() {
		if (currentTarget != null) {
			General.println("Attacking " + currentMonster.getName());
			if (antiban.shouldOpenMenu()) {
				currentTarget.click("Attack");
			} else {
				currentTarget.click();
			}
			Sleep.conditionalSleep(new Condition() {
				@Override
				public boolean active() {
					return inCombat() || !canTarget(currentTarget);
				}
			}, 3500, 5000);
			return Response.CONTINUE;
		}
		return Response.LOOP;
	}

	private boolean atTask() {
		return currentMonster.getArea().contains(Player.getPosition())
				|| getNearestAreaTileToPlayer() != null;
	}

	private RSTile getNearestAreaTileToPlayer() {
		final RSTile myPosition = Player.getPosition();
		return Stream.of(currentMonster.getArea().getAllTiles()).sorted((tile1, tile2) -> {
			if (tile1.distanceTo(myPosition) < tile2.distanceTo(myPosition)) {
				return 1;
			} else if (tile1.distanceTo(myPosition) > tile2.distanceTo(myPosition)) {
				return -1;
			}
			return 0;
		}).filter(rsTile -> PathFinding.canReach(rsTile, false)).findFirst().orElse(null);
	}

	private boolean inCombat() {
		return Combat.isUnderAttack()
				|| Combat.getTargetEntity() != null
				|| Combat.getAttackingEntities().length > 0;
	}

	private boolean canTarget(RSNPC npc) {
		if (npc != null && npc.isClickable() && npc.isValid() && npc.getName().equalsIgnoreCase(currentMonster.getName()) && PathFinding.canReach(npc.getPosition(), false)) {
			return !npc.isInCombat() && npc.getInteractingCharacter() == null || npc.getInteractingCharacter() != null && npc.getInteractingCharacter().equals(Player.getRSPlayer());
		}
		return false;
	}

	private boolean isMultiCombatZone() {
		return Interfaces.isInterfaceSubstantiated(548, 20);
	}

	private RSNPC getNextTarget() {
		final RSNPC[] possibleTargets = NPCs.findNearest(currentMonster.getName());
		return Stream.of(possibleTargets)
				.filter(npc -> {
					final RSCharacter interactingCharacter = npc.getInteractingCharacter();
					if (interactingCharacter != null) {
						return interactingCharacter.equals(Player.getRSPlayer());
					}
					return false;
				})
				.findFirst()
				.orElse((RSNPC) antiban.selectNextTarget(Stream.of(possibleTargets).filter(npc -> canTarget(npc)).toArray(Positionable[]::new)));
	}

}
