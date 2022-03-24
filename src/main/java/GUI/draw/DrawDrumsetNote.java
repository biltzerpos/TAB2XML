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
import javafx.scene.shape.CubicCurve;

import models.measure.note.Note;
import models.measure.Measure;

public class DrawDrumsetNote {

	private double top;
	private double startX;
	private double startY;
	private Note note;
	@FXML private Pane pane;

	public DrawDrumsetNote(Pane pane, Note note, double top, double startX, double startY) {
		super();
		this.note = note;
		this.startX = startX;
		this.top = top - 25;
		this.startY = startY+3;
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

	public void drawX() {
		// Center coordinates of the "x"
		double xCenterX = getStartX() + 3;
		double xCenterY = getStartY() - 3;

		// Draw to crossing line to make the "x"
		Line topLeftToBottomRight = new Line(xCenterX-4, xCenterY+4, xCenterX+4, xCenterY-4);
		Line topRightToBottomLeft = new Line(xCenterX+4, xCenterY+4, xCenterX-4, xCenterY-4);

		topLeftToBottomRight.setId("drum-note-x-2");
		topRightToBottomLeft.setId("drum-note-x-1");

		if (note.getType().equals("half") || note.getType().equals("whole")) {
			// If the note is a half note or a whole note, then we draw the an outline of the "x".
			// We do this by drawing a black "x" and adding a smaller white "x" on top of it.

			// Make the black "x" wider and add it to the screen
			topLeftToBottomRight.setStrokeWidth(3.5);
			topRightToBottomLeft.setStrokeWidth(3.5);
	    	pane.getChildren().add(topLeftToBottomRight);
	    	pane.getChildren().add(topRightToBottomLeft);

	    	// Create and add the  white "x"
			topLeftToBottomRight = new Line(xCenterX-4, xCenterY+4, xCenterX+4, xCenterY-4);
			topRightToBottomLeft = new Line(xCenterX+4, xCenterY+4, xCenterX-4, xCenterY-4);
			topLeftToBottomRight.setStroke(Color.WHITE);
			topRightToBottomLeft.setStroke(Color.WHITE);
			topLeftToBottomRight.setStrokeWidth(1.5);
			topRightToBottomLeft.setStrokeWidth(1.5);
	    	pane.getChildren().add(topLeftToBottomRight);
	    	pane.getChildren().add(topRightToBottomLeft);
		} else {
			// If the note is not a half note or a whole note, then just draw the "x"
			topLeftToBottomRight.setStrokeWidth(1.5);
			topRightToBottomLeft.setStrokeWidth(1.5);
	    	pane.getChildren().add(topLeftToBottomRight);
	    	pane.getChildren().add(topRightToBottomLeft);
		}

		// Only draw the stem if the note is not a whole note
		if (!note.getType().equals("whole")) {
			// Drawing the stem
			Line stem = new Line(getStartX()+8, getStartY()-8, getStartX()+8, this.top);
			stem.setStrokeWidth(1.5);
	    	pane.getChildren().add(stem);
		}

    	Blend blend = new Blend(); 
		ColorInput topInput = new ColorInput(getStartX()-3, getStartY()-7, 14, 8, Color.WHITE); 
		blend.setTopInput(topInput); 
		blend.setMode(BlendMode.OVERLAY);
	}

	public void drawO() {
		// The note is drawn with an ellipse
		Ellipse ellipse;
		if (!note.getType().equals("whole")) {
			// If the not is not a whole not, then it is rotated slightly
			ellipse = new Ellipse(getStartX()+3, getStartY()-2, 6.0, 4.5);
			ellipse.setRotate(330);
		} else {
			// Whole notes are horizontal
			ellipse = new Ellipse(getStartX()+3, getStartY()-3, 6.0, 5.0);
		}
		ellipse.setId("drum-note-o");
		ellipse.toFront();

		Blend blend = new Blend();
		ColorInput topInput = new ColorInput(getStartX()-3, getStartY()-7, 14, 8, Color.WHITE);
		blend.setTopInput(topInput);
		blend.setMode(BlendMode.OVERLAY);

    	pane.getChildren().add(ellipse);
    	ellipse.setEffect(blend);

    	if (note.getType().equals("half")) {
    		// If the note is a half note, then the note is an outline of the note.
    		// We do this by drawing a black ellipse and a smaller white ellipse on top of it.
    		// The black ellipse was already drawn, so we just need to draw the white ellipse.

    		// Draw white ellipse to screen
    		ellipse = new Ellipse(getStartX()+3, getStartY()-2, 4.5, 1.5);
    		ellipse.setFill(Color.WHITE);
    		ellipse.setRotate(330);
    		ellipse.toFront();
        	pane.getChildren().add(ellipse);

        	// Half notes have stems, so draw stem
    		Line stem = new Line(getStartX()+8, getStartY()-5, getStartX()+8, this.top);
    		stem.setStrokeWidth(1.5);
        	pane.getChildren().add(stem);
    	} else if (note.getType().equals("whole")) {
    		// Whole notes are an outline of the note, but they are outlined differently than half notes.
    		// The black ellipse was already drawn, so we just draw a white ellipse on top of it to give it an outlines effect.

    		// Draw white ellipse to the screen
    		ellipse = new Ellipse(getStartX()+3, getStartY()-3, 2.5, 4.0);
    		ellipse.setFill(Color.WHITE);
    		ellipse.setRotate(345);
    		ellipse.toFront();
        	pane.getChildren().add(ellipse);
    	} else {
    		// All other notes have stems, so draw stem
    		Line stem = new Line(getStartX()+8, getStartY()-5, getStartX()+8, this.top);
    		stem.setStrokeWidth(1.5);
        	pane.getChildren().add(stem);
    	}

	}

	public void drawBeam() {
		if (note.getType().equals("eighth")) {
			// Beamed eighth notes have one beam connecting them
			Rectangle beam = new Rectangle(getStartX()+8, this.top-1, 50, 5);
			pane.getChildren().add(beam);
		} else if (note.getType().equals("16th")) {
			// Beamed 16th notes have two beams connecting them

			// Draw first beam
			Rectangle beam = new Rectangle(getStartX()+8, this.top-1, 50, 5);
			pane.getChildren().add(beam);

			// Draw second beam below the first beam
			beam = new Rectangle(getStartX()+8, this.top+7, 50, 5);
			pane.getChildren().add(beam);
		}
	}

	public void drawFlag() {
		if (note.getType().equals("eighth")) {
			CubicCurve cubicCurve = new CubicCurve(
				getStartX()+8, getStartY()-35,
				getStartX()+8, getStartY()-30,
				getStartX()+20, getStartY()-25,
				getStartX()+18, getStartY()-15
			);
	    	pane.getChildren().add(cubicCurve);
		} else if (note.getType().equals("16th")) {
			CubicCurve cubicCurve1 = new CubicCurve(
				getStartX()+8, getStartY()-35,
				getStartX()+8, getStartY()-30,
				getStartX()+20, getStartY()-25,
				getStartX()+18, getStartY()-15
			);
	    	pane.getChildren().add(cubicCurve1);

			CubicCurve cubicCurve2 = new CubicCurve(
				getStartX()+8, getStartY()-30,
				getStartX()+8, getStartY()-25,
				getStartX()+20, getStartY()-20,
				getStartX()+18, getStartY()-10
			);
	    	pane.getChildren().add(cubicCurve2);
		}
	}

	public void draw() {
		if (note.getDuration() == null) {
			return;
		}

		// If note head exists and is an x, then draw "x", otherwise draw "o"
		if (note.getNotehead() != null && note.getNotehead().getType().equals("x")) {
			this.drawX();
		}
		else {
			this.drawO();
		}

		// If the note is octave 5 and step A, then it is above the staff and a small line must be drawn in front of it
		if (note.getUnpitched().getDisplayOctave() == 5 && note.getUnpitched().getDisplayStep().equals("A")) {
			pane.getChildren().add(new Line(getStartX()-5, getStartY()-3, getStartX()+11, getStartY()-3));
		}
	}

	

}
