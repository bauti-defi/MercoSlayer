package scripts.com.mercosur.framework;

import org.tribot.api.General;
import org.tribot.script.Script;
import scripts.com.mercosur.slayer.gui.GUI;

import java.net.URL;
import java.util.LinkedList;

public class NodeScript extends Script {

	private final LinkedList<Node> nodes = new LinkedList<>();

	private GUI gui;

	protected void addNode(Node node) {
		this.nodes.add(node);
	}

	protected void addGUI(URL fxml) {
		this.gui = new GUI(fxml);
	}

	@Override
	public void run() {
		if (gui != null) {
			println("Opening GUI");
			gui.show();
			do {
				sleep(500);
			} while (gui.isShowing());

			println("GUI closed.");
		}

		while (!nodes.isEmpty()) {
			for (Node node : nodes) {
				if (node.condition()) {
					switch (node.execute()) {
						case LOOP:
							General.sleep(80, 150);
							break;
						case CONTINUE:
							General.sleep(80, 150);
							continue;
					}
				}
			}
		}
	}

	protected void onStop() {
		if (gui != null) {
			gui.close();
		}
	}


}
