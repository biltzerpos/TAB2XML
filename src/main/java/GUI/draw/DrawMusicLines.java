package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class DrawMusicLines {
	@FXML private Pane pane;
	private double startY;
	private double startX;
	private final double lenght = 50.0;

	public DrawMusicLines(Pane pane, double x, double y) {
		super();
		this.pane = pane;
		this.startY = y;
		this.startX = x;
		
    	for (int i = 0; i < 6; i++) {
        	MLine l = new MLine(this.startX, this.startY, this.lenght, this.startY, i+1);
        	Line line = l.getLine();
        	pane.getChildren().add(line);
        	this.startY = this.startY + 10;
        	System.out.println(l.getLineTag());
    	}
	}
	
	
	

	
	
}
