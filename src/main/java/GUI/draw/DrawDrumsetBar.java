package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class DrawDrumsetBar {

	@FXML private Pane pane; 
	private double startX; 
	private double startY;
	private double separator;

	public DrawDrumsetBar() {}

	/**
	 * Constructor for the DrawDrumsetBar class.
	 *
	 * @param pane      - The pane for which the bar will be drawn on.
	 * @param separator - The length between two music lines
	 */
	public DrawDrumsetBar(Pane pane, double separator) {
		super();
		this.pane = pane;
		this.separator = separator;
	}

	/**
	 * Draw a bar at the specified position.
	 *
	 * @param x - The x-coordinate at which to draw the bar
	 * @param y - The y-coordinate at which to draw the bar
	 */
	public void draw(double x, double y) {
		Line barLine = new Line();

		// We draw the bar line starting at coordinates (x, y + 10) because the first music line for
		// drums is white (invisible to the user), so the barline should start at the second line
		// (which is at y + 10).
		barLine.setStartX(x);
		barLine.setStartY(y + this.separator);
		barLine.setEndX(x);
		barLine.setEndY(y + 5 * this.separator);

    	pane.getChildren().add(barLine);
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

}
