package ua.oa.taras.fm.filemanager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.oa.taras.fm.controller.FmController;

public class FileManager extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		URL url = FileManager.class.getResource("/ua/oa/taras/fm/view/FmView.fxml");
        FXMLLoader loader = new FXMLLoader(url);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		FmController controller = loader.getController();
        controller.setDirectoryLeft(new File("C:/"));
        controller.setDirectoryRight(new File("C:/"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("SimpleFileManager");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
