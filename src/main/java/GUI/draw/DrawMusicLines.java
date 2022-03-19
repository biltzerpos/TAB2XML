package GUI.draw;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

// this class draws music lines (group of 6 lines) with the width of  50; 
public class DrawMusicLines extends Node {

	@FXML
	private Pane pane;
	private double length;
	private MLine musicLine;
	private List<MLine> musicLineList;

	// Constructor 1
	public DrawMusicLines() {

	}

	// Constructor 2
	public DrawMusicLines(Pane pane, int lenght) {
		super();
		this.pane = pane;
		this.musicLineList = new ArrayList<MLine>();
		this.length = lenght; 

	}

	// Method that does the actual drawing
	public void draw(double x, double y) {
		for (int i = 0; i < 6; i++) {
			musicLine = new MLine(pane, x, y, x + this.length, y, i + 1);
			y += 10;
			musicLineList.add(musicLine);
		}
	}

	// this method allows access to the list of 6 lines at any moment
	public List<MLine> getMusicLineList() {
		return musicLineList;
	}

	// getters and setters
	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	

}
