package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.measure.note.Note;

public class DrawNote {
	
	private int fret;
	private double startX; 
	private double startY;
	@FXML private Pane pane;
	
	public DrawNote(Pane pane, int fret, double startX, double startY) {
		super();
		this.fret = fret;
		this.startX = startX;
		this.startY = startY;
		this.pane = pane;
	}
	
	public void draw() {
		Text text = new Text(getStartX(), getStartY(), Integer.toString(this.fret));
    	Rectangle textBack = new Rectangle(getStartX() -3, getStartY() - 12, 15, 15);
    	textBack.setFill(Color.WHITE);
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	
    	pane.getChildren().add(textBack);
    	pane.getChildren().add(text);
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
