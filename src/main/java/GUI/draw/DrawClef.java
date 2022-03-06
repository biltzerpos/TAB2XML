package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.measure.attributes.Clef;

//This class draw an exctracted clef on a given pane
public class DrawClef {

	private Clef clef;
	@FXML
	private Pane pane;
	private double x;
	private double y;

	// Constructor 1
	public DrawClef(Pane pane, Clef clef, double x, double y) {
		super();
		this.clef = clef;
		this.pane = pane;
		this.x = x;
		this.y = y;
	}

	// constructor 2
	public DrawClef() {

	}

	// the method called for the drawing the clef on Pane.
	public void draw() {
		String name = this.clef.getSign();
		if (name == "TAB") {
			drawVertical(name);
		}
	}

	/*
	 * The method that prints a given string in vertical format for example if
	 * string = TAB then we have: 
	 * T 
	 * A 
	 * B
	 */
	private void drawVertical(String name) {
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			String s = Character.toString(c);
			Text text = new Text(this.x, this.y, s);
			text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 18));
			pane.getChildren().add(text);
			y += 15;
		}
	}

	// Getters and Setters
	public Clef getClef() {
		return clef;
	}

	public void setClef(Clef clef) {
		this.clef = clef;
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
