package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

//Creates a Line with tag between 1-6
public class MLine extends Line {

	@FXML
	private Pane pane;
	private int lineTag;
	private double startY;
	private double startX;
	private double endX;
	private double endY;

	public MLine() {
	}

	public MLine(Pane pane, double startX, double startY, double endX, double endY, int tag) {
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.lineTag = tag;
		createLines();

	}

	// Method to draw line on pane
	private void createLines() {
		Line line = new Line();
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
		pane.getChildren().add(line);
	}

	// returns the x coordinates of a line with specific tag
	public double getStartX(int tag) {
		return this.startX;
	}

	// returns the Y coordnicate of a line with specific tag
	public double getStartY(int tag) {
		return this.startY;
	}

	// Getter and setters
	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public int getLineTag() {
		return lineTag;
	}

	public void setLineTag(int lineTag) {
		this.lineTag = lineTag;
	}

}
