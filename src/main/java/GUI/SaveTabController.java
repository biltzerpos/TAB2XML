package GUI;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SaveTabController {
	String tablature;
	
	public SaveTabController(String tab) {
		tablature = tab;
	}

	public void save() {
		try (PrintWriter out = new PrintWriter("Tab.txt")) {
			out.println(tablature);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
