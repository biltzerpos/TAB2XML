package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

// This class draws a simple vertical bar
public class DrawBassBar {

	@FXML
	private Pane pane;
	private double startX;
	private double startY;

	// Constructor 1
	public DrawBassBar() {
	}

	// Constructor 2
	public DrawBassBar(Pane pane, double startX, double startY) {
		super();
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
	}

	// method that is called for the drawing
	public void draw() {
		Line bar1 = new Line();
		bar1.setStartX(getStartX());
		bar1.setStartY(getStartY());
		bar1.setEndX(getStartX());
		bar1.setEndY(getStartY() + 30);
		pane.getChildren().add(bar1);
	}

	// Getters and setters
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
