package design2;

import GUI.MainViewController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class CanvasGen extends Canvas {
	private GraphicsContext gc;
	private int fontSize = 40;
	
	private MainViewController mvc;

	public CanvasGen(double height, double width, MainViewController mvc) {
		// Configure Canvas
		this.setWidth(height);
		this.setHeight(width);
		gc = this.getGraphicsContext2D();
		gc.setFont(new Font("Bravura", fontSize)); // fontSize changes size of every drawn element

//		 this.setStyle("-fx-padding: 10;" +
//		            "-fx-border-style: solid inside;" +
//		            "-fx-border-width: 2;" +
//		            "-fx-border-insets: 5;" +
//		            "-fx-border-radius: 5;" +
//		            "-fx-border-color: white;" +
//		            "-fx-background-color: white");
		this.mvc = mvc;
	}

	public void draw() {
		// fillText(Java source code, x-coordinate, y-coordinate);
		// For Java source unicode: https://www.fileformat.info/index.htm

		gc.fillText("\uD834\uDD1E", 10, 50); // G CLEF
		gc.fillText("\uD834\uDD5D", 60, 150); // whole
//		gc.fillText("\uD834\uDD5E", 50, 50); // half
//		gc.fillText("\uD834\uDD5F", 70, 50); // quarter
//		gc.fillText("\uD834\uDD60", 90, 50); // eighth
//		gc.fillText("\uD834\uDD61", 110, 50); // sixteenth
//		gc.fillText("\uD834\uDD62", 130, 50); // thirtysixth



    }

	public String getNoteType(int duration) {
		String noteType = "";

		switch(duration) {
		case 64:
			noteType = "\uD834\uDD5D";
			break;
		case 32:
			noteType = "\uD834\uDD5E";
			break;
		case 16:
			noteType = "\uD834\uDD5F";
			break;
		case 8:
			noteType = "\uD834\uDD60";
			break;
		case 4:
			noteType = "\uD834\uDD61";
			break;
		case 2:
			noteType = "\uD834\uDD62";
			break;
		case 1:
			noteType = "\uD834\uDD63";
			break;
		case 1 / 2:
			noteType = "\uD834\uDD64";
			break;
		}
		return noteType;
	}

}
