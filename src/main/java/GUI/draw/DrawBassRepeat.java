package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

// This class draws a simple vertical bar (for Guitar)
public class DrawBassRepeat {

	@FXML
	private Pane pane;
	private double startX;
	private double startY;

	// Constructor 1
	public DrawBassRepeat() {
	}

	// Constructor 2
	public DrawBassRepeat(Pane pane, double startX, double startY) {
		super();
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
	}

	// method that is called for the drawing
	public void drawForward() {
		Line line1 = new Line();
		Line line2 = new Line();
		Circle circle1 = new Circle();
		Circle circle2 = new Circle();
		line1.setStartX(getStartX() - 15);
		line1.setStartY(getStartY() + 2);
		line1.setStroke(Color.BLACK);
		line1.setStrokeWidth(5.0f);
		line1.setEndX(getStartX() - 15);
		line1.setEndY(getStartY() + 43);
		circle1.setCenterX(getStartX() - 5);
		circle1.setCenterY(getStartY() + 7);
		circle1.setRadius(2.0f);
		line2.setStartX(getStartX() - 10);
		line2.setStartY(getStartY() + 2);
		line2.setStroke(Color.GRAY);
		line2.setStrokeWidth(2.0f);
		line2.setEndX(getStartX() - 10);
		line2.setEndY(getStartY() + 43);
		circle2.setCenterX(getStartX() - 5);
		circle2.setCenterY(getStartY() + 38);
		circle2.setRadius(2.0f);
		pane.getChildren().add(line1);
		pane.getChildren().add(line2);
		pane.getChildren().add(circle1);
		pane.getChildren().add(circle2);
	}

	public void drawBackward() {
		Line line1 = new Line();
		Line line2 = new Line();
		Circle circle1 = new Circle();
		Circle circle2 = new Circle();
		line1.setStartX(getStartX());
		line2.setStartX(getStartX() - 5);
		line1.setStartY(getStartY() + 2);
		line2.setStartY(getStartY() + 2);

		line1.setEndX(getStartX());
		line2.setEndX(getStartX() - 5);
		line1.setEndY(getStartY() + 43);
		line2.setEndY(getStartY() + 43);

		circle1.setCenterX(getStartX() - 10);
		circle2.setCenterX(getStartX() - 10);
		circle1.setCenterY(getStartY() + 7);
		circle2.setCenterY(getStartY() + 38);

		line1.setStroke(Color.BLACK);
		line1.setStrokeWidth(5.0f);
		circle1.setRadius(2.0f);
		line2.setStroke(Color.GRAY);
		line2.setStrokeWidth(2.0f);
		circle2.setRadius(2.0f);
		pane.getChildren().add(line1);
		pane.getChildren().add(line2);
		pane.getChildren().add(circle1);
		pane.getChildren().add(circle2);
	}

	// Getters and setters
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

}
