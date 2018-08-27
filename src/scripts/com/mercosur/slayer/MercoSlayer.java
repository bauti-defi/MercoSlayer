package scripts.com.mercosur.slayer;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.ScriptManifest;
import scripts.com.mercosur.dax_api.api_lib.DaxWalker;
import scripts.com.mercosur.dax_api.api_lib.WebWalkerServerApi;
import scripts.com.mercosur.dax_api.api_lib.models.DaxCredentials;
import scripts.com.mercosur.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.com.mercosur.dax_api.walker_engine.WalkingCondition;
import scripts.com.mercosur.framework.NodeScript;
import scripts.com.mercosur.slayer.nodes.FightingNode;
import scripts.com.mercosur.slayer.util.RunTimeVariables;

@ScriptManifest(name = "MercoSlayer", authors = {"Mercosur"}, description = "Trains slayer in OSRS.", category = "Slayer", gameMode = 1)
public class MercoSlayer extends NodeScript {

	public MercoSlayer() {
		super(new FightingNode());

		for (RSItem item : Inventory.getAll()) {
			println(item.getIndex());
		}

		WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
			@Override
			public DaxCredentials getDaxCredentials() {
				return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
			}
		});
		println("DaxWalker authenticated.");


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
		println("Walker configured.");

		General.useAntiBanCompliance(true);
		println("Antiban enabled.");
	}

	//get task
	//bank for supplies
	//walk to task
	//fight task
	//eat
	//pot
	//prayer
}
