package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

// This class draws a simple vertical bar (for Guitar)
public class DrawBar {

	@FXML
	private Pane pane;
	private double startX;
	private double startY;
	private double len; 

	// Constructor 1
	public DrawBar() {
	}

	// Constructor 2
	public DrawBar(Pane pane, double startX, double startY, double len) {
		super();
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
		this.len = len; 
	}

	// method that is called for the drawing
	public void draw() {
		Line bar1 = new Line();
		bar1.setStartX(getStartX());
		bar1.setStartY(getStartY());
		bar1.setEndX(getStartX());
		bar1.setEndY(getStartY() + len);
		pane.getChildren().add(bar1);
	}
	public void drawEndBar() {
		Line bar1 = new Line();
		bar1.setStartX(getStartX()-5);
		bar1.setStartY(getStartY());
		bar1.setEndX(getStartX()-5);
		bar1.setEndY(getStartY() + len);
		pane.getChildren().add(bar1);
		
		Line bar2 = new Line();
		bar2.setStartX(getStartX());
		bar2.setStartY(getStartY());
		bar2.setEndX(getStartX());
		bar2.setEndY(getStartY() + len);
		bar2.setStrokeWidth(3);
		pane.getChildren().add(bar2);
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
