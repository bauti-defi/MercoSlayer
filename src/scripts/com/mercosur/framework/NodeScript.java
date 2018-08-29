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

	private URL fxml;

	public NodeScript(Node... nodes) {
		for (Node node : nodes) {
			this.nodes.add(node);
		}
		sortNodes();
		println("Nodes configured.");
	}

	@Override
	public void run() {
		/*try {
			fxml = new File(Util.getWorkingDirectory() + File.separator + "bin" + File.separator + "scripts" + File.separator + "com" + File.separator + "mercosur" + File.separator + "slayer" + File.separator + "gui" + File.separator + "main.fxml").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		gui = new GUI(fxml);
		gui.show();
		do {
			sleep(500);
		} while (gui.isOpen());*/

		println("GUI closed.");

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
