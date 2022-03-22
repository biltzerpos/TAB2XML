package GUI.draw;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.measure.note.Note;

// This class draws note type under the music lines
public class DrawNoteType {

	@FXML
	private Pane pane;
	private double startX;
	private double startY;
	// private final double HalfNoteLength = 15;
	// private final double QuarterNoteLength = 30;
	// private Note note;
	private double shortStick;
	private double longStick;

	public DrawNoteType() {

	}

	public DrawNoteType(Pane pane, double startX, double startY, double shortStick) {
		super();
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
		/// this.note = note;
		this.shortStick = shortStick;
		this.longStick = 2 * shortStick;

	}

	/*
	 * public void drawType() {
	 * 
	 * if (note.getChord() == null) { // startX += 25;
	 * 
	 * String current = note.getType(); if (current == "half") { drawHalfNotes(); }
	 * else if (current == "quarter") { drawQuarterNotes(); } else if (current ==
	 * "eighth") { drawEighthNotes(); } else if (current == "16th") {
	 * draw16thNotes(); } // startX += 25;
	 * 
	 * } }
	 * 
	 * private void draw16thNotes() { double y1 = startY + 37; Line l = new Line();
	 * l.setStartX(startX); l.setEndX(startX); l.setStartY(y1); l.setEndY(y1 -
	 * QuarterNoteLength); pane.getChildren().add(l);
	 * 
	 * }
	 * 
	 * // shorter stick private void drawHalfNotes() { double y1 = startY + 37; Line
	 * l = new Line(); l.setStartX(startX); l.setEndX(startX); l.setStartY(y1);
	 * l.setEndY(y1 - HalfNoteLength); pane.getChildren().add(l);
	 * 
	 * }
	 * 
	 * // longer stick private void drawQuarterNotes() { double y1 = startY + 37;
	 * Line l = new Line(); l.setStartX(startX); l.setEndX(startX); l.setStartY(y1);
	 * l.setEndY(y1 - QuarterNoteLength); pane.getChildren().add(l);
	 * 
	 * }
	 * 
	 * // draw notes with beam private void drawEighthNotes() { double y1 = startY +
	 * 37; Line l = new Line(); l.setStartX(startX); l.setEndX(startX);
	 * l.setStartY(y1); l.setEndY(y1 - QuarterNoteLength);
	 * pane.getChildren().add(l);
	 * 
	 * }
	 * 
	 * // draw the beam itself
	 * 
	 * 
	 * // draw a dot public void drawDot(double centerX, double centerY, double r) {
	 * Circle circle = new Circle(centerX, centerY, r); circle.setFill(Color.BLACK);
	 * pane.getChildren().add(circle); }
	 */
	public void drawShortLine() {
		double y1 = startY + 37;
		Line l = new Line();
		l.setStartX(startX);
		l.setEndX(startX);
		l.setStartY(y1);
		l.setEndY(y1 - this.shortStick);
		pane.getChildren().add(l);
	}

	public void drawLongLine() {
		double y1 = startY + 37;
		Line l = new Line();
		l.setStartX(startX);
		l.setEndX(startX);
		l.setStartY(y1);
		l.setEndY(y1 - this.longStick);
		pane.getChildren().add(l);

	}

	public void drawBeam(double length) {
		double y1 = startY + 37;
		Line l = new Line();
		l.setStartX(startX);
		l.setEndX(startX + length);
		l.setStartY(y1);
		l.setEndY(y1);
		l.setStrokeWidth(3);
		pane.getChildren().add(l);
	}

	// draw a dot
	public void drawDot(double centerX, double centerY, double r) {
		Circle circle = new Circle(centerX, centerY, r);
		circle.setFill(Color.BLACK);
		pane.getChildren().add(circle);
	}

	// setters
	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
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

	public void drawActual(int actualLast, double length, int font) {
		// TODO Auto-generated method stub
		double y1 = startY + 50;
		double x = startX + length / 4;
		// int f = font - 4;
		// divide the spacing between in the form of |--int--|
		double totalLength = length + (length / 2);
		double len = (totalLength - font) / 2;
		double endX = x - len;

		Line l = new Line();
		l.setStartX(x);
		l.setEndX(endX);
		l.setStartY(y1);
		l.setEndY(y1);
		l.setStrokeWidth(1);
		pane.getChildren().add(l);

		Line v1 = new Line();
		v1.setStartX(x);
		v1.setEndX(x);
		v1.setStartY(y1);
		v1.setEndY(y1 - 9);
		pane.getChildren().add(v1);

		double xFont = endX - font;
		Text t = new Text(xFont, y1, Integer.toString(actualLast));
		t.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, font));
		pane.getChildren().add(t);

		double xEnd2 = xFont - len;
		Line l2 = new Line();
		l2.setStartX(xFont - font / 4);
		l2.setEndX(xEnd2);
		l2.setStartY(y1);
		l2.setEndY(y1);
		l2.setStrokeWidth(1);
		pane.getChildren().add(l2);

		Line v2 = new Line();
		v2.setStartX(xEnd2);
		v2.setEndX(xEnd2);
		v2.setStartY(y1);
		v2.setEndY(y1 - 9);
		pane.getChildren().add(v2);
	}

}
