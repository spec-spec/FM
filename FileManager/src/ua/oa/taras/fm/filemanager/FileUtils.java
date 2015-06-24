package ua.oa.taras.fm.filemanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

	public boolean createFile(Path path, String fileName) {
		L.debug("create file function" + path + fileName);
		File f = new File(path.toString(), fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
				L.debug("success file creation" + path + fileName);
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

	public void filesCopy(File source, Path target) {
		L.debug("try copy " + source.toString() + " to " + target.toString());
		if (source.isFile()) {
			try {
				Files.copy(source.toPath(), target.resolve(source.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (source.isDirectory()) {
			try {
				Files.copy(source.toPath(), target.resolve(source.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Path innerPath = target.resolve(source.getName());
			File[] innerFiles = source.listFiles();
			for (int i = 0; i < innerFiles.length; i++) {
				filesCopy(innerFiles[i], innerPath);
			}
		}
	}

}
