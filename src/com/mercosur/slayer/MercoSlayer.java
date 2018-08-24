package com.mercosur.slayer;

import com.mercosur.dax_api.api_lib.DaxWalker;
import com.mercosur.dax_api.api_lib.WebWalkerServerApi;
import com.mercosur.dax_api.api_lib.models.DaxCredentials;
import com.mercosur.dax_api.api_lib.models.DaxCredentialsProvider;
import com.mercosur.dax_api.walker_engine.WalkingCondition;
import com.mercosur.framework.NodeScript;
import com.mercosur.slayer.nodes.FightingNode;
import com.mercosur.slayer.nodes.AntibanNode;
import com.mercosur.slayer.nodes.taskrenewal.RenewTaskNode;
import com.mercosur.slayer.nodes.banking.BankingNode;
import com.mercosur.slayer.util.RunTimeVariables;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.script.ScriptManifest;

@ScriptManifest(name = "MercoSlayer", authors = {"Mercosur"}, description = "Trains slayer in OSRS.", category = "Slayer", gameMode = 1)
public class MercoSlayer extends NodeScript {

	public MercoSlayer() {
		super(new AntibanNode(), new FightingNode(), new BankingNode(), new RenewTaskNode());

		WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
			@Override
			public DaxCredentials getDaxCredentials() {
				return new DaxCredentials("sub_DPjcfqN4YkIxm8", "sub_DPjXXzL5DeSiPf");
			}
		});

		DaxWalker.setGlobalWalkingCondition(() -> {
			if (Combat.getHP() < 20) {
				return WalkingCondition.State.EXIT_OUT_WALKER_FAIL;
			} else if (RunTimeVariables.ANTIBAN.shouldCheckTabs()) {
				RunTimeVariables.ANTIBAN.checkTabs();
			} else if (RunTimeVariables.ANTIBAN.shouldExamineEntity()) {
				RunTimeVariables.ANTIBAN.examineEntity();
			} else if (RunTimeVariables.ANTIBAN.shouldCheckXP()) {
				RunTimeVariables.ANTIBAN.checkXP();
			} else if (RunTimeVariables.ANTIBAN.shouldMoveMouse()) {
				RunTimeVariables.ANTIBAN.moveMouse();
			} else if (RunTimeVariables.ANTIBAN.shouldPickupMouse()) {
				RunTimeVariables.ANTIBAN.pickupMouse();
			} else if (RunTimeVariables.ANTIBAN.shouldRightClick()) {
				RunTimeVariables.ANTIBAN.rightClick();
			} else if (RunTimeVariables.ANTIBAN.shouldRotateCamera()) {
				RunTimeVariables.ANTIBAN.rotateCamera();
			} else if (RunTimeVariables.ANTIBAN.shouldLeaveGame()) {
				RunTimeVariables.ANTIBAN.shouldLeaveGame();
			}
			return WalkingCondition.State.CONTINUE_WALKER;
		});

		General.useAntiBanCompliance(true);
	}

	//get task
	//bank for supplies
	//walk to task
	//fight task
	//eat
	//pot
	//prayer
}
