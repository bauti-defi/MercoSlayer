package scripts.com.mercosur.framework;

import org.tribot.api.General;
import org.tribot.script.Script;
import scripts.com.mercosur.slayer.gui.GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NodeScript extends Script {

	private final List<Node> nodes = new ArrayList<>();

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

		sortNodes();
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

	private void sortNodes() {
		nodes.sort((node1, node2) -> {
			if (node1.getPriority().getRank() > node2.getPriority().getRank()) {
				return 1;
			} else if (node1.getPriority().getRank() < node2.getPriority().getRank()) {
				return -1;
			}
			return 0;
		});
	}


}
