package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class DrawBar {
	@FXML private Pane pane; 
	private double startX; 
	private double startY;
	
	public DrawBar(Pane pane, double startX, double startY) {
		super();
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
	} 
	
	public void draw() {
		Line bar1 = new Line();
    	bar1.setStartX(getStartX());
    	bar1.setStartY(getStartY());
    	bar1.setEndX(getStartX());
    	bar1.setEndY(getStartY() + 50);
    	pane.getChildren().add(bar1);
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
