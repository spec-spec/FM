package ua.oa.taras.fm.filemanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

	public void deleteFile(File f) {
		if (f.isFile()) {
			f.delete();
		}
		if (f.isDirectory()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++) {
				deleteFile(list[i]);
			}
			f.delete();
		}

	}
}
