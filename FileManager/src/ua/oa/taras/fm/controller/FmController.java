package ua.oa.taras.fm.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import ua.oa.taras.fm.filemanager.Filer;

public class FmController implements Initializable  {

	@FXML
	private ListView<File> leftFilesListView;
	@FXML
	private ListView<File> rightFilesListView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		leftFilesListView.selectionModelProperty().addListener(
				new ChangeListener<MultipleSelectionModel<File>>() {

					@Override
					public void changed(
							ObservableValue<? extends MultipleSelectionModel<File>> arg0,
							MultipleSelectionModel<File> oldValue,
							MultipleSelectionModel<File> newValue) {
						File currentFile = newValue.getSelectedItem();
						if (currentFile.isDirectory()) {
							Filer.showFilesInDirectoryWindow(currentFile);
						}
					}
				});
		
	}
	private void setDirectory(File dir, ListView<File> lv){
		File[] list = dir.listFiles();
		ObservableList<File> listOfFiles = FXCollections
				.observableArrayList(list);
		lv.setItems(listOfFiles);
	}
	public void setDirectoryLeft(File dir){
		setDirectory( dir, leftFilesListView);
	}
	public void setDirectoryRight(File dir){
		setDirectory( dir, rightFilesListView);
	}
}
