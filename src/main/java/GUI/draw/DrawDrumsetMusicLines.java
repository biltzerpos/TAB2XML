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

	/**
	 * Draws the sheet music lines and adds invisible lines for placing notes above and below lines.
	 *
	 * @param x The x-coordinate of the music lines
	 * @param y The y-coordinate of the music lines
	 */
	public void draw(double x, double y) {
		// Make the first line invisible (the third parameter is visible = false).
		// We do this so that we can draw notes on the top line without the user seeing the line
		// (because the user should only see 5 lines).
		this.addLine(x, y, false);

		y += 5;

		for (int i = 1; i < 6; i++) {
			// Above every visible line, draw an invisible line.
			// We do this so that we can place notes in between lines easily.
			this.addLine(x, y, false);

        	y += 5;

        	// Draw the visible line
			this.addLine(x, y, true);

        	y += 5;
    	}
	}

	/**
	 * Draws an individual line.
	 *
	 * @param x       The x-coordinate of the line
	 * @param y       The y-coordinate of the line
	 * @param visible The visibility of the line. If set to false, the line will not be drawn to the screen.
	 */
	private void addLine(double x, double y, boolean visible) {
		Line line = new Line();

		line.setStartX(x);
		line.setStartY(y);
		line.setEndX(x + this.length);
		line.setEndY(y);

		// Only draw the line if visible is true.
		// Otherwise, skip this and just add it to musicLineList so that it can
		// be used to place notes between lines.
		if (visible) {
			pane.getChildren().add(line);
		}

    	musicLineList.add(line);
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
