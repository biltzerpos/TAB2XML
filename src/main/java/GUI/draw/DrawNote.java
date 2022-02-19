package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DrawNote {
	
	private int fret;
	private double startX; 
	private double startY;
	@FXML private Pane pane;

	/**
	 * Constructor for guitar.
	 */
	public DrawNote(Pane pane, int fret, double startX, double startY) {
		super();
		this.fret = fret;
		this.startX = startX;
		this.startY = startY;
		this.pane = pane;
	}

	/**
	 * Constructor for drums.
	 */
	public DrawNote(Pane pane, double startX, double startY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.pane = pane;
	}

	/*
	 * this method draws note based on given Fret of guitar
	 * */
	public void drawFret() {
		// for the background of each note a cube is used
		Box box = new Box(13, 13, 1); 
		box.setTranslateX(getStartX()+3);
		box.setTranslateY(getStartY()-3);
		box.setTranslateZ(-15);
		PhongMaterial boxColor = new PhongMaterial();
		boxColor.setSpecularColor(Color.WHITE);
		boxColor.setDiffuseColor(Color.WHITE);
		boxColor.setSpecularPower(0);
		box.setMaterial(boxColor);
		pane.getChildren().add(box);
		
		//the actual notes based on fret value of guitar
		Text text = new Text(getStartX(), getStartY(), Integer.toString(this.fret));
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	text.toFront();
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

	public void drawX() {
		Text text = new Text(getStartX(), getStartY(), "x");
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	text.toFront();
    	pane.getChildren().add(text);
		
	}

	public void drawO() {
		Text text = new Text(getStartX(), getStartY(), "o");
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	text.toFront();
    	pane.getChildren().add(text);
		
	}
	

	
	

}
