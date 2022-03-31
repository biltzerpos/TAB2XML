package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.QuadCurve;

public class DrawSlur {
	
	private double startX;
	private double endX;
	private double startY; 
	@FXML private Pane pane;
	private int place; 
	
	public DrawSlur() {}

	public DrawSlur(Pane pane) {
		this.pane = pane; 
		
	}
	
	//Getters/setters
	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}


	public double getEndX() {
		return endX;
	}


	public void setEndX(double endX) {
		this.endX = endX;
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

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public void draw() {
		// TODO Auto-generated method stub
		QuadCurve quadCurve = new QuadCurve();

		// Adding properties to the Quad Curve
		double x1 = this.startX;
		double y1 = this.startY;
		double x2 = this.endX;
		double y2 = this.startY;
		double controlX = (x2+x1)/2;
		double controlY = y1+(10*getPlace());
		
		quadCurve = getCurve(x1, y1, x2, y2, controlX, controlY);
		quadCurve.setViewOrder(-1);
		pane.getChildren().add(quadCurve);
	
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
