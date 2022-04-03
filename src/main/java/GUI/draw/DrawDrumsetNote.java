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
	private double spacing;

	public DrawDrumsetNote(Pane pane, Note note, double top, double spacing, double startX, double startY) {
		super();
		this.note = note;
		this.startX = startX;
		this.top = top - 25;
		this.startY = startY+3;
		this.pane = pane;
		this.spacing = spacing;
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

	public void drawRest() {
//		Line line = new Line(this.startX, this.startY, this.startX + 5, this.startY + 7);
//		pane.getChildren().add(line);

		javafx.scene.shape.SVGPath rest = new javafx.scene.shape.SVGPath();
		rest.setContent("m254.4 20.8c1.5 1.6 13 15.7 13 15.7s-6.4 6.1-6.4 12.5c0 7.5 8.6 14.3 8.6 14.3l-.9 1.1c-3.3-1.9-8.9-2.1-11.4.8-3.1 3.6 3.9 9.1 3.9 9.1l-.8 1.1c-2.4-1.8-12.6-11.4-8.3-16.1 2.6-2.9 5.8-3.8 10.3-1.4l-12.1-12.5c7-8.6 8.2-11.1 8.2-13.4 0-4.8-3.4-8.2-5.1-10.4-.6-.9-1.7-1.6-1-2.2.7-.5 1.1.3 2 1.4z");

		rest.setLayoutX(this.startX - 250);
		rest.setLayoutY(this.startY - 40);

		rest.setScaleX(0.5);
		rest.setScaleY(0.5);

		pane.getChildren().add(rest);
//		line = new Line(this.startX, this.startY + 7, this.startX - 5, this.startY + 10);
//		line.setStrokeWidth(5);
//		pane.getChildren().add(line);

//		line = new Line(this.startX - 5, this.startY + 20, this.startX + 10, this.startY + 35);
//		pane.getChildren().add(line);
	}

	public void drawGrace() {
		// The note is drawn with an ellipse
		Ellipse ellipse;
		ellipse = new Ellipse(getStartX()-15, getStartY()-3, 6.0, 4.5);
		ellipse.setRotate(330);
		ellipse.setId("drum-note-o");
		ellipse.toFront();

		Line stem = new Line(getStartX()-10, getStartY()-5, getStartX()-10, getStartY()-20);
		stem.setStrokeWidth(1.5);
		Line flag = new Line(getStartX()-10, getStartY()-20, getStartX()-3, getStartY()-15);
		flag.setStrokeWidth(1.5);

    	pane.getChildren().add(ellipse);
    	pane.getChildren().add(stem);
    	pane.getChildren().add(flag);
	}

	public void drawSingleBeam() {
		// Beamed eighth notes have one beam connecting them
		Rectangle beam = new Rectangle(getStartX()+8, this.top-1, this.spacing, 5);
		pane.getChildren().add(beam);
	}

	public void drawDoubleBeam() {
		// Beamed 16th notes have two beams connecting them
		// Draw first beam
		Rectangle beam = new Rectangle(getStartX()+8, this.top-1, this.spacing, 5);
		pane.getChildren().add(beam);

		// Draw second beam below the first beam
		beam = new Rectangle(getStartX()+8, this.top+7, this.spacing, 5);
		pane.getChildren().add(beam);
	}

	public void drawFlag() {
		if (note.getType().equals("eighth")) {
			Line flag = new Line(getStartX()+8, this.top, getStartX()+20, this.top + 20);
			flag.setStrokeWidth(1.5);

	    	pane.getChildren().add(flag);
		} else if (note.getType().equals("16th")) {
			Line flag = new Line(getStartX()+8, this.top, getStartX()+20, this.top + 20);
			flag.setStrokeWidth(1.5);

	    	pane.getChildren().add(flag);

			flag = new Line(getStartX()+8, this.top+15, getStartX()+20, this.top + 35);
			flag.setStrokeWidth(1.5);

	    	pane.getChildren().add(flag);
		}
	}

	public void draw() {
		if (note.getGrace() != null) {
			this.drawGrace();
		} else if (note.getRest() != null) {
			this.drawRest();
		} else {
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

	

}
