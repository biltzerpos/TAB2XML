package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.stage.FileChooser;

public class SaveTabController {
	String tablature;
	
	public SaveTabController(String tab) {
		tablature = tab;
	}
	
	// Save inputted ASCII tab
	public void save() {
		String userDirectory = System.getProperty("user.home");
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(userDirectory + "/Desktop"));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Text File", "*.txt")
				);
		File directory = fc.showSaveDialog(null);
		if (directory != null) {
			directory = new File(directory.getAbsolutePath());
		}
		try (PrintWriter out = new PrintWriter(directory)) {
			out.println(tablature);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
