package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.measure.note.Note;

public class DrawNote {

	private double startX;
	private double startY;
	@FXML
	private Pane pane;
	private Note note;
	private int font;

	/**
	 * constructor
	 */
	public DrawNote() {
	}

	/**
	 * Constructor for guitar.
	 */
	public DrawNote(Pane pane, Note note, double startX, double startY, int font) {
		super();
		this.note = note;
		this.startX = startX;
		this.startY = startY;
		this.pane = pane;
		this.font = font;
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
	 */
	public void drawFret() {
		int fret = note.getNotations().getTechnical().getFret();
		Text text = new Text(getStartX(), getStartY(), Integer.toString(fret));
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, this.font));
		// for background
		Blend blend = new Blend();
		ColorInput topInput = new ColorInput();
		if (fret < 10) {
			topInput.setX(getStartX() - (font / 6));
			topInput.setY(getStartY() - (font - 2));
			topInput.setHeight(font);
			topInput.setWidth(font);
			topInput.setPaint(Color.WHITE);
		} else {
			topInput.setX(getStartX() - (font / 6));
			topInput.setY(getStartY() - (font - 2));
			topInput.setHeight(font);
			topInput.setWidth(font + font / 2);
			topInput.setPaint(Color.WHITE);
		}
		blend.setTopInput(topInput);
		blend.setMode(BlendMode.OVERLAY);

		text.setEffect(blend);
		text.setViewOrder(-1);

		pane.getChildren().add(text);
	}

	public void drawGuitarGrace() {
		int f = font - 2;
		int fret = note.getNotations().getTechnical().getFret();
		Text text = new Text(getStartX(), getStartY(), Integer.toString(fret));
		text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, f));
		// for background
		Blend blend = new Blend();
		ColorInput topInput = new ColorInput();
		if (fret < 10) {
			topInput.setX(getStartX() - (f / 6));
			topInput.setY(getStartY() - (f - 2));
			topInput.setHeight(f);
			topInput.setWidth(f);
			topInput.setPaint(Color.WHITE);
		} else {
			topInput.setX(getStartX() - (f / 6));
			topInput.setY(getStartY() - (f - 2));
			topInput.setHeight(f);
			topInput.setWidth(f + f / 2);
			topInput.setPaint(Color.WHITE);
		}
		blend.setTopInput(topInput);
		blend.setMode(BlendMode.OVERLAY);

		text.setEffect(blend);
		text.setViewOrder(-1);

		pane.getChildren().add(text);
	}

	public void drawX() {
		// Drawing the "x" with two lines
		double xCenterX = getStartX() + 3;
		double xCenterY = getStartY() - 3;
		Line topLeftToBottomRight = new Line(xCenterX - 4, xCenterY + 4, xCenterX + 4, xCenterY - 4);
		Line topRightToBottomLeft = new Line(xCenterX + 4, xCenterY + 4, xCenterX - 4, xCenterY - 4);
		topLeftToBottomRight.setStrokeWidth(1.5);
		topRightToBottomLeft.setStrokeWidth(1.5);

		// Drawing the stem
		Line stem = new Line(getStartX() + 8, getStartY() - 8, getStartX() + 8, getStartY() - 35);
		stem.setStrokeWidth(1.5);

		pane.getChildren().add(topLeftToBottomRight);
		pane.getChildren().add(topRightToBottomLeft);
		pane.getChildren().add(stem);

		Blend blend = new Blend();
		ColorInput topInput = new ColorInput(getStartX() - 3, getStartY() - 7, 14, 8, Color.WHITE);
		blend.setTopInput(topInput);
		blend.setMode(BlendMode.OVERLAY);
	}

	public void drawO() {

		Ellipse ellipse = new Ellipse(getStartX() + 3, getStartY() - 2, 6.0, 4.5);
		ellipse.setRotate(330);
		ellipse.toFront();

		Line stem = new Line(getStartX() + 8, getStartY() - 5, getStartX() + 8, getStartY() - 35);
		stem.setStrokeWidth(1.5);

		Blend blend = new Blend();
		ColorInput topInput = new ColorInput(getStartX() - 3, getStartY() - 7, 14, 8, Color.WHITE);
		blend.setTopInput(topInput);
		blend.setMode(BlendMode.OVERLAY);

		pane.getChildren().add(ellipse);
		pane.getChildren().add(stem);
		ellipse.setEffect(blend);
	}

	public void drawDrumClef1() {

		Rectangle r1 = new Rectangle();
		r1.setWidth(3);
		r1.setHeight(20);
		r1.setTranslateX(5);
		r1.setTranslateY(20);
		pane.getChildren().add(r1);

	}

	public void drawDrumClef2() {

		Rectangle r1 = new Rectangle();
		r1.setWidth(3);
		r1.setHeight(20);
		r1.setTranslateX(10);
		r1.setTranslateY(20);
		pane.getChildren().add(r1);

	}

	// Getters and setters
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

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public int getFont() {
		return font;
	}

	public void setFont(int font) {
		this.font = font;
	}

}
