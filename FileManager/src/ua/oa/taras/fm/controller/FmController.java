package ua.oa.taras.fm.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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

	Filer fileMethod = new Filer();
	String currentLeftDir;
	String currentRightDir;
	ObservableList<File> leftListOfFiles;
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
		ObservableList<File> rightListOfFiles = FXCollections.observableArrayList(list);
		rightFilesListView.setItems(rightListOfFiles);
		currentRightDir = dir.toString();
		rightSideLabel.setText(currentRightDir);
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
	ObservableList<File>sourcelist = leftFilesListView.getSelectionModel().getSelectedItems();
		for(File source:sourcelist){
		if (source != null) {
			File dest = new File(currentRightDir + "\\" + source.getName());
			actlog.info(" copy source " + source.toPath() + " dest " + dest.toPath());
			try {
				Files.copy(source.toPath(), dest.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
