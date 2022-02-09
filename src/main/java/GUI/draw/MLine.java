package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MLine extends Line{
	
	@FXML private Pane pane;
	private int lineTag;
	private double startY;
	private double startX;
	private double endX; 
	private double endY; 
	
	public MLine() {}
	public MLine(Pane pane, double startX, double startY, double endX, double endY, int tag) {
    	this.pane = pane;
		this.startX = startX; 
    	this.startY = startY; 
    	this.endX = endX;
    	this.endY = endY; 
    	this.lineTag = tag;
    	createLines();
        
	}
	
	public void createLines() {
		Line line = new Line();
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
		pane.getChildren().add(line);
	}
	
	public int getLineTag() {
		return lineTag;
	}
	public double getStartX (int tag) {
		return this.startX;
	}
	public double getStartY (int tag) {
		return this.startY;
	}
	

}
