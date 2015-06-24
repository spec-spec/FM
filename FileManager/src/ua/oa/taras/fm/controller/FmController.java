package ua.oa.taras.fm.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ua.oa.taras.fm.filemanager.FileUtils;

public class FmController implements Initializable {
	@FXML
	private ListView<File> leftFilesListView;
	@FXML
	private ListView<File> rightFilesListView;
	@FXML
	private TextField createTextField;
	@FXML
	private Label leftSideLabel;
	@FXML
	private Label rightSideLabel;

	private FileUtils fileMethod = new FileUtils();
	private Path currentLeftDir;
	private Path currentRightDir;
	private ObservableList<File> leftListOfFiles;
	private ObservableList<File> rightListOfFiles;
	private final String DEFAULT_DIR = "C:/";
	private final int CLICK_COUNT=2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		leftFilesListView.getSelectionModel().setSelectionMode(
				SelectionMode.MULTIPLE);
		rightFilesListView.getSelectionModel().setSelectionMode(
				SelectionMode.MULTIPLE);
	}

	public void setDirectoryLeft(File dir) {
		File[] list = dir.listFiles();
		leftListOfFiles = FXCollections.observableArrayList(list);
		leftFilesListView.setItems(leftListOfFiles);
		currentLeftDir = dir.toPath();
		leftSideLabel.setText(currentLeftDir.toString());
	}

	public void setDirectoryRight(File dir) {
		File[] list = dir.listFiles();
		rightListOfFiles = FXCollections.observableArrayList(list);
		rightFilesListView.setItems(rightListOfFiles);
		currentRightDir = dir.toPath();
		rightSideLabel.setText(currentRightDir.toString());
	}

	@FXML
	public void enterLeftDirectory(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == CLICK_COUNT) {
			File currentFile = leftFilesListView.getSelectionModel()
					.getSelectedItems().get(0);
			if (currentFile.isDirectory()) {
				setDirectoryLeft(currentFile);
			}
		}
	}

	@FXML
	public void enterRightDirectory(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == CLICK_COUNT) {
			File currentFile = rightFilesListView.getSelectionModel()
					.getSelectedItems().get(0);
			if (currentFile.isDirectory()) {
				setDirectoryRight(currentFile);
			}
		}
	}

	@FXML
	public void createFile(ActionEvent event) {
		String fileName = createTextField.getText();
		if (!fileName.isEmpty()) {
			fileMethod.createFile(currentLeftDir, fileName);
		} else
			createTextField.setPromptText("please input file name");
	}

	@FXML
	public void copy(ActionEvent event) {
		ObservableList<File> source = leftFilesListView.getSelectionModel().getSelectedItems();
		for (File file : source) {
			fileMethod.filesCopy(file, currentRightDir);
		}
	}

	@FXML
	public void deleteItem(ActionEvent event) {
		ObservableList<File> sourcelist = leftFilesListView.getSelectionModel().getSelectedItems();
		for (File source : sourcelist) {
			fileMethod.deleteFile(source);
		}
		leftListOfFiles.removeAll(sourcelist);
	}

	public void goToUpDir(MouseEvent mouseEvent) {
		if (mouseEvent.getSource() == leftSideLabel) {
			goToUpDirLeft();
		} else {
			goToUpDirRight();
		}
	}

	public void goToUpDirLeft() {
		Path parrentDir = currentLeftDir.getParent();
		if (parrentDir != null) {
			setDirectoryLeft(parrentDir.toFile());
		} else {
			setDirectoryLeft(new File(DEFAULT_DIR));
		}
	}

	public void goToUpDirRight() {
		Path parrentDir = currentRightDir.getParent();
		if (parrentDir != null) {
			setDirectoryRight(parrentDir.toFile());
		} else {
			setDirectoryRight(new File(DEFAULT_DIR));
		}
	}

}
