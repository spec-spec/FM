package ua.oa.taras.fm.filemanager;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.stage.Stage;

public class Filer {
	Logger log = Logger.getLogger("fileLog");
	private static Stage currentWindow;

	public static void init(Stage stage) {
		currentWindow = stage;
	}

	public boolean createFile(String path, String fileName) {
		log.info("create file function" + path + fileName);
		File f = new File(path, fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
				log.info("success file creation" + path + fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public void deleteFile(File selected) {
		if (selected.isFile()) {
			selected.delete();
		}
		if (selected.isDirectory()) {
			File[] dirFiles = selected.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				deleteFile(dirFiles[i]);
			}
			selected.delete();
		}
	}
	public void filesCopy(File source, Path target) throws IOException
	{ log.info("try copy " + source.toString()+ " to "+ target.toString());
			if(source.isFile()){
			Files.copy(source.toPath(), target.resolve(source.getName()));}
			if(source.isDirectory()){
				Files.copy(source.toPath(), target.resolve(source.getName()));
				Path innerPath=target.resolve(source.getName());
				File[] innerFiles =source.listFiles();
				for (int i = 0; i < innerFiles.length; i++) {
				filesCopy(innerFiles[i],innerPath);
			}
			}
		}
	
}
