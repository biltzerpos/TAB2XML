package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class DrawMusicLines {
	@FXML private Pane pane;
	private double startY;
	private final double lenght = 20.0;

	public DrawMusicLines(Pane pane, double position) {
		super();
		this.pane = pane;
		this.startY = position;
    	for (int i = 0; i < 6; i++) {
    		double x = 0.0;
        	MLine l = new MLine(x, this.startY, this.pane.getMaxWidth(), this.startY, i+1);
        	Line line = l.getLine();
        	pane.getChildren().add(line);
        	this.startY = this.startY + 10;
        	System.out.println(l.getLineTag());
    	}
	}
	
	
	

	
	
}
