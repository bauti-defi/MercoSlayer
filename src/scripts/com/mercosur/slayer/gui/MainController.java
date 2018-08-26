package scripts.com.mercosur.slayer.gui;

import com.allatori.annotations.DoNotRename;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import scripts.com.mercosur.slayer.models.Task;

import java.net.URL;
import java.util.ResourceBundle;

@DoNotRename
public class MainController extends AbstractGUIController {

	@FXML
	@DoNotRename
	private TextField taskToAddName;

	@FXML
	@DoNotRename
	private TreeView<Task> taskTree;

	@FXML
	@DoNotRename
	public void addTask() {
		final TreeItem<Task> newTask = new TreeItem<>(new Task(taskToAddName.getText()));
		taskTree.getRoot().getChildren().add(newTask);
		if (!taskTree.getRoot().isExpanded()) {
			taskTree.getRoot().setExpanded(true);
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		taskTree.setRoot(new TreeItem<>(new Task("Tasks")));
	}
}
