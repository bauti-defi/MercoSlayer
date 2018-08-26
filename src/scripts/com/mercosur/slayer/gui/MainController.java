package scripts.com.mercosur.slayer.gui;

import com.allatori.annotations.DoNotRename;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

@DoNotRename
public class MainController extends AbstractGUIController {

	@FXML
	@DoNotRename
	private TextField taskToAddName;

	@FXML
	@DoNotRename
	private TreeView<String> taskTree;

	@FXML
	@DoNotRename
	public void addTask() {
		taskTree.setRoot(new TreeItem<>(taskToAddName.getText()));
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

	}
}
