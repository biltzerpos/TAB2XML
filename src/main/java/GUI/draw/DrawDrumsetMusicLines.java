package GUI.draw;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class DrawDrumsetMusicLines extends Node {

	@FXML private Pane pane;
	private double length;
	private double separator;
	private MLine musicLine;
	private List<Line> musicLineList;

	public DrawDrumsetMusicLines() {}

	public DrawDrumsetMusicLines(Pane pane) {
		super();
		this.pane = pane;
		this.musicLineList = new ArrayList<Line>();
		this.length = 40;
	}

	public DrawDrumsetMusicLines(Pane pane, double spacing, double separator) {
		super();
		this.pane = pane;
		this.musicLineList = new ArrayList<Line>();
		this.length = spacing;
		this.separator = separator;
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

		y += this.separator;

		for (int i = 1; i < 6; i++) {
			// Above every visible line, draw an invisible line.
			// We do this so that we can place notes in between lines easily.
			this.addLine(x, y, false);

        	y += this.separator;

        	// Draw the visible line
			this.addLine(x, y, true);

        	y += this.separator;
    	}

		// Make the last line invisible.
		// We do this so that we can draw notes below the staff without the user seeing the line.
		this.addLine(x, y, false);
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

	/**
	 * Returns the y-position of the note based on its octave and step.
	 *
	 * @param octave The octave of the note
	 * @param step   The step of the note
	 * @return       The y-position of the note
	 */
	public double getYPositionFromOctaveAndStep(int octave, String step) {
		double yPosition = 0.0;

		switch (octave) {
			case 5:
				switch (step) {
				case "A":
					yPosition = this.musicLineList.get(0).getStartY();
					break;
				case "G":
					yPosition = this.musicLineList.get(1).getStartY();
					break;
				case "F":
					yPosition = this.musicLineList.get(2).getStartY();
					break;
				case "E":
					yPosition = this.musicLineList.get(3).getStartY();
					break;
				case "D":
					yPosition = this.musicLineList.get(4).getStartY();
					break;
				case "C":
					yPosition = this.musicLineList.get(5).getStartY();
					break;
				}
				break;
			case 4:
				switch (step) {
				case "B":
					yPosition = this.musicLineList.get(6).getStartY();
					break;
				case "A":
					yPosition = this.musicLineList.get(7).getStartY();
					break;
				case "G":
					yPosition = this.musicLineList.get(8).getStartY();
					break;
				case "F":
					yPosition = this.musicLineList.get(9).getStartY();
					break;
				case "E":
					yPosition = this.musicLineList.get(10).getStartY();
					break;
				case "D":
					yPosition = this.musicLineList.get(11).getStartY();
					break;
				}
				break;
		}

		return yPosition;
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
