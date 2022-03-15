package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class CanvasTabController extends Canvas {
	private GraphicsContext gc;
	private int fontSize = 40;
	Font f;
			
	public CanvasTabController(double height, double width) {
		// Configure Canvas
		this.setWidth(height);
		this.setHeight(width);
		gc = this.getGraphicsContext2D();
	    try {
			f = Font.loadFont(new FileInputStream(new File("NotoMusic-Regular.ttf")), fontSize);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gc.setFont(f); // fontSize changes size of every drawn element 
		draw();

//		 this.setStyle("-fx-padding: 10;" +
//		            "-fx-border-style: solid inside;" +
//		            "-fx-border-width: 2;" +
//		            "-fx-border-insets: 5;" +
//		            "-fx-border-radius: 5;" +
//		            "-fx-border-color: white;" +
//		            "-fx-background-color: white");
	}
	
	private void draw() {
		// fillText(Java source code, x-coordinate, y-coordinate);
		// For Java source unicode: https://www.fileformat.info/index.htm
		
		gc.fillText("\uD834\uDD1E", 10, 60); // G CLEF
		gc.fillText("\uD834\uDD5D", 60, 150); // whole
//		gc.fillText("\uD834\uDD5E", 50, 50); // half
//		gc.fillText("\uD834\uDD5F", 70, 50); // quarter
//		gc.fillText("\uD834\uDD60", 90, 50); // eighth
//		gc.fillText("\uD834\uDD61", 110, 50); // sixteenth
//		gc.fillText("\uD834\uDD62", 130, 50); // thirtysixth
		
//		String noteType="";
//		switch(n.type) {
//		case "whole":
//			noteType="\uD834\uDD5D";
//			break;
//		case "half":
//			noteType="\uD834\uDD5E";
//			break;
//		case "quarter":
//			noteType="\uD834\uDD5F";
//			break;
//		case "eighth":
//			noteType="\uD834\uDD60";
//			break;
//		case "sixteenth":
//			noteType="\uD834\uDD61";
//			break;
//		case "thirtysixth":
//			noteType="\uD834\uDD62";
//			break;
//		}

    }

}
