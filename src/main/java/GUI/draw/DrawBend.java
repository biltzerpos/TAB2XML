package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.measure.note.Note;

public class DrawBend {

	private Note note;
	@FXML
	private Pane pane;
	private double startX;
	private double startY;
	private final double radiusX = 25;
	private final double radiusY = 30;
	private final double length = 100;
	private double firstML;

	public DrawBend(Pane pane, Note note, double x, double y, double firstML) {
		super();
		this.note = note;
		this.pane = pane;
		this.startX = x;
		this.startY = y;
		this.firstML = firstML;
	}

	public void draw() {
		QuadCurve quadCurve = new QuadCurve();

		// Adding properties to the Quad Curve
		double x1 = this.startX - 10;
		double y1 = this.startY + 20;
		double x2 = x1 + 20;
		double y2 = this.firstML - 10;
		double controlX = x2;
		double controlY = y1;
		
		quadCurve = getCurve(x1, y1, x2, y2, controlX, controlY);
		pane.getChildren().add(quadCurve);
		
		Text bend = new Text(quadCurve.getEndX()+5, quadCurve.getEndY()-5, getBendvalue());
		bend.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 10));
		pane.getChildren().add(bend);

		/*
		 * Text start = new Text(x1, y1, "start"); Text end = new Text(x2, y2, "end");
		 * Text control = new Text(controlX, controlY, "c");
		 * pane.getChildren().add(start); pane.getChildren().add(end);
		 * pane.getChildren().add(control);
		 */

	}

	private String getBendvalue() {
		double bend = this.note.getNotations().getTechnical().getBend().getBendAlter();
		String s = "";
		if (bend == 2.0) { 
			s = "1";
		}
		else if(bend == 1.0) {
			s = "1/2";
		}
		return s;
	}
	private QuadCurve getCurve(double x1, double y1, double x2, double y2, double controlX, double controlY) {
		QuadCurve quadCurve = new QuadCurve();
		quadCurve.setStartX(x1);
		quadCurve.setStartY(y1);
		quadCurve.setEndX(x2);
		quadCurve.setEndY(y2);
		quadCurve.setControlX(controlX);
		quadCurve.setControlY(controlY);

		quadCurve.setFill(Color.TRANSPARENT);
		quadCurve.setStroke(Color.BLACK);
		quadCurve.setStrokeWidth(1);
		return quadCurve;
		
	}

}
