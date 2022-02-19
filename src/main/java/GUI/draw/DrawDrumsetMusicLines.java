package GUI.draw;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class DrawDrumsetMusicLines extends Node {

	@FXML private Pane pane;
	private final double length = 50.0;
	private MLine musicLine;
	private List<Line> musicLineList;

	public DrawDrumsetMusicLines() {}

	public DrawDrumsetMusicLines(Pane pane) {
		super();
		this.pane = pane;
		this.musicLineList = new ArrayList<Line>();
	}

	public void draw(double x, double y) {
		for (int i = 0; i < 6; i++) {
			Line line = new Line();

			// Make the first line white.
			// We do this so that we can draw notes on the top line without the user seeing the line
			// (because the user should only see 5 lines instead of 6).
			if (i == 0) {
				line.setStyle("-fx-stroke: white");
			}

			line.setStartX(x);
			line.setStartY(y);
			line.setEndX(x + this.length);
			line.setEndY(y);

			pane.getChildren().add(line);

        	y+=10;

        	musicLineList.add(line);
    	}
	}

	public List<Line> getMusicLineList() {
		return musicLineList;
	}

	public void setMusicLineList(List<Line> musicLineList) {
		this.musicLineList = musicLineList;
	}

	public MLine getMusicLine() {
		return musicLine;
	}

	public void setMusicLine(MLine musicLine) {
		this.musicLine = musicLine;
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}
}
