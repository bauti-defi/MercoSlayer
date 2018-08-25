package scripts.com.mercosur.framework;

import org.tribot.api.General;
import org.tribot.script.Script;

import java.util.ArrayList;
import java.util.List;

public class NodeScript extends Script {

	private final List<Node> nodes = new ArrayList<>();

	public NodeScript(Node... nodes) {
		for (Node node : nodes) {
			this.nodes.add(node);
		}
		sortNodes();
	}

	@Override
	public void run() {
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
