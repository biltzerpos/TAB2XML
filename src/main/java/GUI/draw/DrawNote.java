package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.measure.note.Note;

public class DrawNote {
	
	private double startX; 
	private double startY;
	@FXML private Pane pane;
	private Note note;
	

	/**
	 * Constructor for guitar.
	 */
	public DrawNote(Pane pane, Note note, double startX, double startY) {
		super();
		this.note = note;
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

	/**
	 * this method draws note based on given Fret of guitar
	 * */
	public void drawFret() {
		int fret = note.getNotations().getTechnical().getFret();
		Text text = new Text(getStartX(), getStartY(), Integer.toString(fret));
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 12));
		text.toFront();
		//for background
		Blend blend = new Blend(); 
		ColorInput topInput = new ColorInput(getStartX()-3, getStartY()-10, 15, 15, Color.WHITE); 
		blend.setTopInput(topInput); 
		blend.setMode(BlendMode.OVERLAY);
			    	
		text.setEffect(blend);
		        
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
