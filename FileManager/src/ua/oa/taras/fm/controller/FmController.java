package ua.oa.taras.fm.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
import ua.oa.taras.fm.filemanager.Filer;

public class FmController implements Initializable {
	Logger actlog = Logger.getLogger("actionLog");
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

	private Filer fileMethod = new Filer();
	private  String currentLeftDir;
	private  Path currentRightDir;
	private ObservableList<File> leftListOfFiles;
	private ObservableList<File> rightListOfFiles;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		leftFilesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	public void setDirectoryLeft(File dir) {
		File[] list = dir.listFiles();
		leftListOfFiles = FXCollections.observableArrayList(list);
		leftFilesListView.setItems(leftListOfFiles);
		currentLeftDir = dir.toString();
		leftSideLabel.setText(currentLeftDir);
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
		if (mouseEvent.getClickCount() == 2) {
			File currentFile = leftFilesListView.getSelectionModel().getSelectedItem();
			if (currentFile.isDirectory()) {
				setDirectoryLeft(currentFile);
			}
		}
	}

	@FXML
	public void enterRightDirectory(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2) {
			File currentFile = rightFilesListView.getSelectionModel().getSelectedItem();
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
	ObservableList<File>source = leftFilesListView.getSelectionModel().getSelectedItems();
	for(File file:source){
		try {
			fileMethod.filesCopy(file, currentRightDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	@FXML
	public void deleteItem(ActionEvent event) {
		ObservableList<File>sourcelist = leftFilesListView.getSelectionModel().getSelectedItems();
		for(File source:sourcelist){
		fileMethod.deleteFile(source);
		}
		leftListOfFiles.removeAll(sourcelist);
	}

}
