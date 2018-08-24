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
		General.sleep(nodes.stream().filter(node -> node.condition()).findFirst().map(node -> node.execute()).orElse(50));
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
