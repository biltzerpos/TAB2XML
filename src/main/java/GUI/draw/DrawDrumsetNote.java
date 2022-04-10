package GUI.draw;

import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import models.measure.note.Note;

public class DrawDrumsetNote {

	private double top;
	private double x;
	private double y;
	private Note note;
	@FXML private Pane pane;
	private double spacing;

	// Quarter rest SVG courtesy of Wikimedia Commons
	// https://commons.wikimedia.org/wiki/File:QuarterRest.svg
	private final String quarterRestSVG = "m254.4 20.8c1.5 1.6 13 15.7 13 15.7s-6.4 6.1-6.4 12.5c0 7.5 8.6 14.3 8.6 14.3l-.9 1.1c-3.3-1.9-8.9-2.1-11.4.8-3.1 3.6 3.9 9.1 3.9 9.1l-.8 1.1c-2.4-1.8-12.6-11.4-8.3-16.1 2.6-2.9 5.8-3.8 10.3-1.4l-12.1-12.5c7-8.6 8.2-11.1 8.2-13.4 0-4.8-3.4-8.2-5.1-10.4-.6-.9-1.7-1.6-1-2.2.7-.5 1.1.3 2 1.4z";

	// Eighth rest SVG courtesy of Wikimedia Commons
	// https://commons.wikimedia.org/wiki/File:Eighth_rest.svg
	private final String eighthRestSVG = "M 531.098,74.847 C 530.578,74.945 530.18,75.304 530,75.8 C 529.961,75.96 529.961,75.999 529.961,76.218 C 529.961,76.519 529.98,76.679 530.121,76.917 C 530.32,77.316 530.738,77.636 531.215,77.753 C 531.715,77.894 532.551,77.773 533.508,77.456 L 533.746,77.374 L 532.57,80.624 L 531.414,83.87 C 531.414,83.87 531.453,83.89 531.516,83.933 C 531.633,84.011 531.832,84.07 531.973,84.07 C 532.211,84.07 532.512,83.933 532.551,83.812 C 532.551,83.773 533.109,81.878 533.785,79.628 L 534.98,75.503 L 534.941,75.445 C 534.844,75.324 534.645,75.285 534.523,75.382 C 534.484,75.421 534.422,75.503 534.383,75.562 C 534.203,75.863 533.746,76.398 533.508,76.597 C 533.289,76.777 533.168,76.796 532.969,76.718 C 532.789,76.62 532.73,76.519 532.609,75.98 C 532.492,75.445 532.352,75.202 532.051,75.003 C 531.773,74.824 531.414,74.765 531.098,74.847 z";

	// Sixteenth rest SVG courtesy of Wikimedia Commons
	// https://commons.wikimedia.org/wiki/File:16th_rest.svg
	private final String sixteenthRestSVG = "M 544.191,74.847 C 543.672,74.945 543.273,75.304 543.098,75.8 C 543.055,75.96 543.055,75.999 543.055,76.218 C 543.055,76.519 543.074,76.679 543.215,76.917 C 543.414,77.316 543.832,77.636 544.313,77.753 C 544.809,77.894 545.605,77.792 546.563,77.476 C 546.703,77.417 546.82,77.374 546.82,77.394 C 546.82,77.417 545.926,80.324 545.887,80.425 C 545.785,80.683 545.445,81.16 545.148,81.46 C 544.871,81.738 544.73,81.8 544.512,81.699 C 544.332,81.601 544.273,81.499 544.152,80.96 C 544.051,80.562 543.973,80.343 543.813,80.187 C 543.395,79.726 542.676,79.667 542.121,80.027 C 541.859,80.206 541.66,80.484 541.543,80.785 C 541.5,80.941 541.5,80.984 541.5,81.202 C 541.5,81.499 541.523,81.66 541.66,81.898 C 541.859,82.296 542.277,82.617 542.758,82.734 C 542.977,82.796 543.535,82.796 543.914,82.734 C 544.23,82.675 544.609,82.577 544.988,82.456 C 545.148,82.398 545.289,82.359 545.289,82.378 C 545.289,82.378 543.336,88.734 543.297,88.831 C 543.297,88.851 543.453,88.972 543.613,89.011 C 543.773,89.074 543.934,89.074 544.094,89.011 C 544.25,88.972 544.41,88.874 544.41,88.812 C 544.43,88.792 545.227,85.785 546.203,82.136 L 547.977,75.503 L 547.938,75.445 C 547.859,75.324 547.699,75.304 547.559,75.363 C 547.48,75.402 547.48,75.402 547.242,75.761 C 547.043,76.081 546.762,76.417 546.602,76.577 C 546.383,76.757 546.266,76.796 546.066,76.718 C 545.887,76.62 545.824,76.519 545.707,75.98 C 545.586,75.445 545.445,75.202 545.148,75.003 C 544.871,74.824 544.512,74.765 544.191,74.847 z";

	// Thirty-second rest SVG courtesy of Wikimedia Commons
	// https://commons.wikimedia.org/wiki/File:32nd_rest.svg
	private final String thirtySecondRestSVG = "M 553.789,69.863 C 553.273,69.964 552.871,70.324 552.695,70.82 C 552.652,70.98 552.652,71.019 552.652,71.238 C 552.652,71.456 552.652,71.538 552.695,71.656 C 552.832,72.097 553.113,72.433 553.551,72.632 C 553.848,72.792 553.988,72.812 554.406,72.812 C 554.926,72.812 555.363,72.734 556.063,72.515 C 556.242,72.452 556.379,72.413 556.379,72.413 C 556.398,72.413 556.219,73.113 555.98,73.949 C 555.684,75.124 555.563,75.503 555.523,75.62 C 555.363,75.921 555.023,76.378 554.805,76.577 C 554.605,76.757 554.488,76.796 554.289,76.718 C 554.109,76.62 554.047,76.519 553.93,75.98 C 553.828,75.581 553.75,75.363 553.59,75.202 C 553.172,74.745 552.453,74.687 551.898,75.046 C 551.637,75.222 551.438,75.503 551.32,75.8 C 551.277,75.96 551.277,75.999 551.277,76.218 C 551.277,76.519 551.301,76.679 551.438,76.917 C 551.637,77.316 552.055,77.636 552.535,77.753 C 552.754,77.816 553.313,77.816 553.691,77.753 C 554.008,77.695 554.387,77.593 554.766,77.476 C 554.945,77.417 555.086,77.374 555.086,77.374 C 555.086,77.394 554.289,80.425 554.246,80.484 C 554.09,80.824 553.77,81.242 553.531,81.48 C 553.273,81.738 553.133,81.781 552.914,81.699 C 552.734,81.601 552.672,81.499 552.555,80.96 C 552.453,80.562 552.375,80.343 552.215,80.187 C 551.797,79.726 551.078,79.667 550.523,80.027 C 550.262,80.206 550.063,80.484 549.945,80.785 C 549.902,80.941 549.902,80.984 549.902,81.202 C 549.902,81.421 549.902,81.499 549.945,81.62 C 550.082,82.058 550.363,82.398 550.801,82.597 C 551.121,82.757 551.238,82.777 551.676,82.777 C 551.996,82.777 552.098,82.777 552.355,82.734 C 552.715,82.675 553.094,82.558 553.512,82.437 L 553.77,82.335 L 553.77,82.398 C 553.75,82.476 552.074,88.773 552.055,88.812 C 552.035,88.894 552.395,89.05 552.613,89.05 C 552.832,89.05 553.152,88.913 553.172,88.812 C 553.191,88.792 554.148,84.667 555.344,79.648 C 557.477,70.562 557.477,70.542 557.438,70.48 C 557.375,70.402 557.277,70.363 557.156,70.363 C 557.016,70.382 556.957,70.441 556.816,70.679 C 556.539,71.16 556.219,71.577 556.043,71.718 C 555.922,71.796 555.82,71.796 555.664,71.738 C 555.484,71.636 555.422,71.538 555.305,70.999 C 555.184,70.46 555.043,70.222 554.746,70.023 C 554.469,69.843 554.109,69.785 553.789,69.863 z";

	public DrawDrumsetNote(Pane pane, Note note, double top, double spacing, double x, double y) {
		super();
		this.note = note;
		this.x = x;
		this.top = top - 25;
		this.y = y + 3;
		this.pane = pane;
		this.spacing = spacing;
	}

	/**
	 * Draws a cymbal note.
	 */
	public void drawX() {
		// Center coordinates of the "x"
		double xCenterX = this.x + 3;
		double xCenterY = this.y - 3;

		// Draw to crossing line to make the "x"
		Line topLeftToBottomRight = new Line(xCenterX - 4, xCenterY + 4, xCenterX + 4, xCenterY - 4);
		Line topRightToBottomLeft = new Line(xCenterX + 4, xCenterY + 4, xCenterX - 4, xCenterY - 4);

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
			topLeftToBottomRight = new Line(xCenterX - 4, xCenterY + 4, xCenterX + 4, xCenterY - 4);
			topRightToBottomLeft = new Line(xCenterX + 4, xCenterY + 4, xCenterX - 4, xCenterY - 4);
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
			Line stem = new Line(this.x + 8, this.y - 8, this.x + 8, this.top);
			stem.setStrokeWidth(1.5);
	    	pane.getChildren().add(stem);
		}

    	Blend blend = new Blend(); 
		ColorInput topInput = new ColorInput(this.x - 3, this.y - 7, 14, 8, Color.WHITE); 
		blend.setTopInput(topInput); 
		blend.setMode(BlendMode.OVERLAY);
	}

	/**
	 * Draws a drum (non-cymbal) note.
	 */
	public void drawO() {
		// The note is drawn with an ellipse
		Ellipse ellipse;
		if (!note.getType().equals("whole")) {
			// If the not is not a whole not, then it is rotated slightly
			ellipse = new Ellipse(this.x + 3, this.y - 2, 6.0, 4.5);
			ellipse.setRotate(330);
		} else {
			// Whole notes are horizontal
			ellipse = new Ellipse(this.x + 3, this.y - 3, 6.0, 5.0);
		}
		ellipse.setId("drum-note-o");
		ellipse.toFront();

		Blend blend = new Blend();
		ColorInput topInput = new ColorInput(this.x - 3, this.y - 7, 14, 8, Color.WHITE);
		blend.setTopInput(topInput);
		blend.setMode(BlendMode.OVERLAY);

    	pane.getChildren().add(ellipse);
    	ellipse.setEffect(blend);

    	if (note.getType().equals("half")) {
    		// If the note is a half note, then the note is an outline of the note.
    		// We do this by drawing a black ellipse and a smaller white ellipse on top of it.
    		// The black ellipse was already drawn, so we just need to draw the white ellipse.

    		// Draw white ellipse to screen
    		ellipse = new Ellipse(this.x + 3, this.y - 2, 4.5, 1.5);
    		ellipse.setFill(Color.WHITE);
    		ellipse.setRotate(330);
    		ellipse.toFront();
        	pane.getChildren().add(ellipse);

        	// Half notes have stems, so draw stem
    		Line stem = new Line(this.x + 8, this.y - 5, this.x + 8, this.top);
    		stem.setStrokeWidth(1.5);
        	pane.getChildren().add(stem);
    	} else if (note.getType().equals("whole")) {
    		// Whole notes are an outline of the note, but they are outlined differently than half notes.
    		// The black ellipse was already drawn, so we just draw a white ellipse on top of it to give it an outlines effect.

    		// Draw white ellipse to the screen
    		ellipse = new Ellipse(this.x + 3, this.y - 3, 2.5, 4.0);
    		ellipse.setFill(Color.WHITE);
    		ellipse.setRotate(345);
    		ellipse.toFront();
        	pane.getChildren().add(ellipse);
    	} else {
    		// All other notes have stems, so draw stem
    		Line stem = new Line(this.x + 8, this.y - 5, this.x + 8, this.top);
    		stem.setStrokeWidth(1.5);
        	pane.getChildren().add(stem);
    	}
	}

	/**
	 * Draws a rest note (either whole, half, quarter, eighth, sixteenth, or thirty-second).
	 */
	public void drawRest() {
		Shape rest;

		if (this.note.getType().equals("whole")) {
			Rectangle halfRest = new Rectangle(this.x, this.y - 3, 10, 5);
			rest = halfRest;
		} else if (this.note.getType().equals("half")) {
			Rectangle halfRest = new Rectangle(this.x, this.y - 8, 10, 5);
			rest = halfRest;
		} else if (this.note.getType().equals("quarter")) {
			SVGPath quarterRest = new SVGPath();
			quarterRest.setContent(this.quarterRestSVG);
			quarterRest.setLayoutX(this.x - 260);
			quarterRest.setLayoutY(this.y - 50);
			quarterRest.setScaleX(0.5);
			quarterRest.setScaleY(0.5);
			rest = quarterRest;
		} else if (this.note.getType().equals("eighth")) {
			SVGPath eighthRest = new SVGPath();
			eighthRest.setContent(this.eighthRestSVG);
			eighthRest.setLayoutX(this.x - 525);
			eighthRest.setLayoutY(this.y - 80);
			eighthRest.setScaleX(2);
			eighthRest.setScaleY(2);
			rest = eighthRest;
		} else if (this.note.getType().equals("16th")) {
			SVGPath sixteenthRest = new SVGPath();
			sixteenthRest.setContent(this.sixteenthRestSVG);
			sixteenthRest.setLayoutX(this.x - 540);
			sixteenthRest.setLayoutY(this.y - 85);
			sixteenthRest.setScaleX(1.5);
			sixteenthRest.setScaleY(1.5);
			rest = sixteenthRest;
		} else {
			SVGPath thirtysecondRest = new SVGPath();
			thirtysecondRest.setContent(this.thirtySecondRestSVG);
			thirtysecondRest.setLayoutX(this.x - 550);
			thirtysecondRest.setLayoutY(this.y - 85);
			thirtysecondRest.setScaleX(1.5);
			thirtysecondRest.setScaleY(1.5);
			rest = thirtysecondRest;
		}

		pane.getChildren().add(rest);
	}

	/**
	 * Draws a tie as a curve between two notes.
	 *
	 * @param coordList - A list containing two arrays with x and y coordinates of the notes to be tied.
	 */
	public void drawTie(List<Double[]> coordList) {
		double x1 = coordList.get(0)[0] + 12;
		double y1 = coordList.get(0)[1] + (this.note.getNotehead() == null ? 5 : -5);
		double x2 = coordList.get(1)[0] - 5;
		double y2 = coordList.get(1)[1] + (this.note.getNotehead() == null ? 5 : -5);

		double controlX = (x2 + x1) / 2;
		double controlY = this.note.getNotehead() == null ? y1 + 10 : y1 - 10;

		QuadCurve quadCurve = new QuadCurve(x1, y1, controlX, controlY, x2, y2);

		quadCurve.setFill(Color.TRANSPARENT);
		quadCurve.setStroke(Color.BLACK);
		quadCurve.setStrokeWidth(3);
		quadCurve.setViewOrder(-1);

		pane.getChildren().add(quadCurve);
	}

	/**
	 * Draws a slur as a curve between two notes.
	 *
	 * @param coordList - A list containing two arrays with x and y coordinates of the notes to be tied.
	 */
	public void drawSlur(List<Double[]> coordList) {
		double x1 = coordList.get(0)[0] - 15;
		double y1 = coordList.get(0)[1] + (this.note.getNotehead() == null ? 8 : -8);
		double x2 = coordList.get(1)[0];
		double y2 = coordList.get(1)[1] + (this.note.getNotehead() == null ? 8 : -8);

		double controlX = (x2 + x1) / 2;
		double controlY = this.note.getNotehead() == null ? y1 + 7 : y1 - 7;

		QuadCurve quadCurve = new QuadCurve(x1, y1, controlX, controlY, x2, y2);

		quadCurve.setFill(Color.TRANSPARENT);
		quadCurve.setStroke(Color.BLACK);
		quadCurve.setStrokeWidth(2);
		quadCurve.setViewOrder(-1);

		pane.getChildren().add(quadCurve);
	}

	/**
	 * Draws a grace note.
	 */
	public void drawGrace() {
		// The note is drawn with an ellipse
		Ellipse ellipse;
		ellipse = new Ellipse(this.x - 15, this.y - 3, 6.0, 4.5);
		ellipse.setRotate(330);
		ellipse.setId("drum-note-o");
		ellipse.toFront();

		Line stem = new Line(this.x - 10, this.y - 5, this.x - 10, this.y - 20);
		stem.setStrokeWidth(1.5);
		Line flag = new Line(this.x - 10, this.y - 20, this.x - 3, this.y - 15);
		flag.setStrokeWidth(1.5);

    	pane.getChildren().add(ellipse);
    	pane.getChildren().add(stem);
    	pane.getChildren().add(flag);
	}

	/**
	 * Draws tremelo on a note.
	 */
	public void drawTremelo() {
		Line line = new Line(this.x  + 5, this.y - 15, this.x + 10, this.y - 18);
		line.setStrokeWidth(5);
		pane.getChildren().add(line);
	}

	/**
	 * Draws a dot next to a note.
	 */
	public void drawDot() {
		Text dot = new Text(this.x + 8, this.y + 2, ".");
		dot.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 25));
		pane.getChildren().add(dot);
	}

	/**
	 * Draws a single beam next to a note.
	 */
	public void drawSingleBeam() {
		// Beamed eighth notes have one beam connecting them
		Rectangle beam = new Rectangle(this.x + 8, this.top - 1, this.spacing, 5);
		pane.getChildren().add(beam);
	}

	/**
	 * Draws a double beam next to a note.
	 */
	public void drawDoubleBeam() {
		// Beamed 16th notes have two beams connecting them
		// Draw first beam
		this.drawSingleBeam();

		// Draw second beam below the first beam
		Rectangle beam = new Rectangle(this.x + 8, this.top + 7, this.spacing, 5);
		pane.getChildren().add(beam);
	}

	/**
	 * Draws a triple beam next to a note.
	 */
	public void drawTripleBeam() {
		// Beamed 32nd notes have three beams connecting them
		// Draw first and second beams
		this.drawDoubleBeam();

		// Draw third beam below the second beam
		Rectangle beam = new Rectangle(this.x + 8, this.top + 15, this.spacing, 5);
		pane.getChildren().add(beam);
	}

	/**
	 * Draws a note flag on a note (either eighth, sixteenth, or thirty-second).
	 */
	public void drawFlag() {
		if (note.getType().equals("eighth")) {
			Line flag = new Line(this.x + 8, this.top, this.x + 20, this.top + 20);
			flag.setStrokeWidth(1.5);

	    	pane.getChildren().add(flag);
		} else if (note.getType().equals("16th")) {
			Line flag = new Line(this.x + 8, this.top, this.x + 20, this.top + 20);
			flag.setStrokeWidth(1.5);

	    	pane.getChildren().add(flag);

			flag = new Line(this.x + 8, this.top + 15, this.x + 20, this.top + 35);
			flag.setStrokeWidth(1.5);

	    	pane.getChildren().add(flag);
		} else if (note.getType().equals("32nd")) {
			Line flag = new Line(this.x + 8, this.top, this.x + 20, this.top + 20);
			flag.setStrokeWidth(1.5);
	
	    	pane.getChildren().add(flag);
	
			flag = new Line(this.x + 8, this.top + 15, this.x + 20, this.top + 35);
			flag.setStrokeWidth(1.5);
	
	    	pane.getChildren().add(flag);

			flag = new Line(this.x + 8, this.top + 30, this.x + 20, this.top + 50);
			flag.setStrokeWidth(1.5);
	
	    	pane.getChildren().add(flag);
		}
	}

	/**
	 * Draws a drum note. This handles cymbal/non-cymbal, grace, and rest notes, along with dots and tremelo.
	 */
	public void draw() {
		if (note.getGrace() != null) {
			this.drawGrace();
		} else if (note.getRest() != null) {
			this.drawRest();
			if (note.getDots() != null) {
				this.drawDot();
			}
		} else {
			// If note head exists and is an x, then draw "x", otherwise draw "o"
			if (note.getNotehead() != null && note.getNotehead().getType().equals("x")) {
				this.drawX();
			}
			else {
				this.drawO();
			}

			// If the current note has a tremelo property, draw tremelo
			if (note.getNotations() != null && note.getNotations().getOrnaments() != null && note.getNotations().getOrnaments().getTremolo() != null) {
				this.drawTremelo();
			}

			if (note.getDots() != null) {
				this.drawDot();
			}

			// If the note is octave 5 and step A, then it is above the staff and a small line must be drawn in front of it
			if (note.getUnpitched().getDisplayOctave() == 5 && note.getUnpitched().getDisplayStep().equals("A")) {
				pane.getChildren().add(new Line(this.x - 5, this.y - 3, this.x + 11, this.y - 3));
			}
		}
	}

	public double getStartX() {
		return x;
	}

	public void setStartX(double x) {
		this.x = x;
	}

	public double getStartY() {
		return y;
	}

	public void setStartY(double y) {
		this.y = y;
	}

}
