package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.shape.Line;

public class MLine extends Line{
	
	@FXML private Line line = new Line();
	private int lineTag;
	
	public MLine(double startX, double startY, double endX, double endY, int tag) {
    	this.line.setStartX(startX); 
    	this.line.setStartY(startY);         
    	this.line.setEndX(endX); 
    	this.line .setEndY(endY);
    	this.lineTag = tag;
        
	}

	
	public Line getLine() {
		return line;
	}


	public void setLine(Line line) {
		this.line = line;
	}


	public int getLineTag() {
		return lineTag;
	}

	public void setLineTag(int lineTag) {
		this.lineTag = lineTag;
	}
	

}
