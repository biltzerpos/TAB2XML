package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class DrawDrumsetBar {

	@FXML private Pane pane; 
	private double startX; 
	private double startY;

	public DrawDrumsetBar() {}

	public DrawDrumsetBar(Pane pane) {
		super();
		this.pane = pane;
	}

	public void draw(double x, double y) {
		Line barLine = new Line();

		// We draw the bar line starting at coordinates (x, y + 10) because the first music line for
		// drums is white (invisible to the user), so the barline should start at the second line
		// (which is at y + 10).
		barLine.setStartX(x);
		barLine.setStartY(y + 10);
		barLine.setEndX(x);
		barLine.setEndY(y + 50);

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
