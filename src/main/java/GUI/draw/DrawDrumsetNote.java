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

public class DrawDrumsetNote {

	private double startX;
	private double startY;
	private Note note;
	@FXML private Pane pane;

	public DrawDrumsetNote(Pane pane, Note note, double startX, double startY) {
		super();
		this.note = note;
		this.startX = startX;
		this.startY = startY;
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

	public void drawX() {
		// Drawing the "x" with two lines
		double xCenterX = getStartX() + 3;
		double xCenterY = getStartY() - 3;
		Line topLeftToBottomRight = new Line(xCenterX-4, xCenterY+4, xCenterX+4, xCenterY-4);
		Line topRightToBottomLeft = new Line(xCenterX+4, xCenterY+4, xCenterX-4, xCenterY-4);
		topLeftToBottomRight.setStrokeWidth(1.5);
		topRightToBottomLeft.setStrokeWidth(1.5);

		// Drawing the stem
		Line stem = new Line(getStartX()+8, getStartY()-8, getStartX()+8, getStartY()-35);
		stem.setStrokeWidth(1.5);

		drawFlag();

    	pane.getChildren().add(topLeftToBottomRight);
    	pane.getChildren().add(topRightToBottomLeft);
    	pane.getChildren().add(stem);

    	Blend blend = new Blend(); 
		ColorInput topInput = new ColorInput(getStartX()-3, getStartY()-7, 14, 8, Color.WHITE); 
		blend.setTopInput(topInput); 
		blend.setMode(BlendMode.OVERLAY);
	}

	public void drawO() {

		Ellipse ellipse = new Ellipse(getStartX()+3, getStartY()-2, 6.0, 4.5);
		ellipse.setRotate(330);
		ellipse.toFront();

		Line stem = new Line(getStartX()+8, getStartY()-5, getStartX()+8, getStartY()-35);
		stem.setStrokeWidth(1.5);

		drawFlag();

		Blend blend = new Blend();
		ColorInput topInput = new ColorInput(getStartX()-3, getStartY()-7, 14, 8, Color.WHITE);
		blend.setTopInput(topInput);
		blend.setMode(BlendMode.OVERLAY);

    	pane.getChildren().add(ellipse);
    	pane.getChildren().add(stem);
    	ellipse.setEffect(blend);
	}
	
	
}
