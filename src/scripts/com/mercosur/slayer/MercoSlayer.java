package scripts.com.mercosur.slayer;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.PreEnding;
import org.tribot.script.interfaces.Starting;
import org.tribot.util.Util;
import scripts.com.mercosur.dax_api.api_lib.DaxWalker;
import scripts.com.mercosur.dax_api.api_lib.WebWalkerServerApi;
import scripts.com.mercosur.dax_api.api_lib.models.DaxCredentials;
import scripts.com.mercosur.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.com.mercosur.dax_api.walker_engine.WalkingCondition;
import scripts.com.mercosur.framework.NodeScript;
import scripts.com.mercosur.slayer.nodes.FightingNode;
import scripts.com.mercosur.slayer.nodes.taskretrieval.RetrieveTaskNode;
import scripts.com.mercosur.slayer.util.RunTimeVariables;

import java.io.File;
import java.net.MalformedURLException;

@ScriptManifest(name = "MercoSlayer", authors = {"Mercosur"}, description = "Trains slayer in OSRS.", category = "Slayer", gameMode = 1)
public class MercoSlayer extends NodeScript implements Starting, PreEnding {

	public static final String WALKER_KEY = "sub_DPjXXzL5DeSiPf";

	public static final String KEY_SECRET = "PUBLIC-KEY";

	public MercoSlayer() {
		addNode(new FightingNode());
		addNode(new RetrieveTaskNode());

		try {
			addGUI(new File(Util.getWorkingDirectory() + File.separator + "bin" + File.separator + "scripts" + File.separator + "com" + File.separator + "mercosur" + File.separator + "slayer" + File.separator + "gui" + File.separator + "main.fxml").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStart() {
		WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
			@Override
			public DaxCredentials getDaxCredentials() {
				return new DaxCredentials(WALKER_KEY, KEY_SECRET);
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

	@Override
	public void onPreEnd() {
		General.println("Thank you for using MercoSlayer.");
	}
}
