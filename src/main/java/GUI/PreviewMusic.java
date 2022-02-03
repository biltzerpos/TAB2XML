package GUI;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class PreviewMusic extends Application{
    //merge practice 1
	private MainViewController mvc;
   // @FXML private Canvas canvas;
	@FXML private Canvas canvas;
    


	public PreviewMusic() {}

	@FXML 
	public void initialize() {

	}

    public void setMainViewController(MainViewController mvcInput) {
    	mvc = mvcInput;
    }
    // We can use this method to update the music sheet
    public void update() throws IOException
    {
    	//the following code is for the checking only and can be removed 
    	// the following prints two measures using the drawMeasure.fxml file
    	//Next steps, figuring out how to print multiple measures in such way that it fits in the width of the window
    	//Feel free to play around with the code. 
    	/*FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/drawMeasure.fxml"));
		AnchorPane ms = loader.load();
		
		anchorPane.getChildren().add( ms);
		
		FXMLLoader noteLoader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/MusicNotes/wholeNote.fxml"));
		AnchorPane note = noteLoader.load();
		
		anchorPane.getChildren().add(note);*/
		
		// for note E 
		//		----------------------
		//		 	O
		//		----------------------
		//
		//		----------------------
		//
		//		----------------------
		//
		//		----------------------
		//
		//		----------------------
		// for note E for example the pseudo code would something like thi:
		//if (note = 'E'){ anchorPane.addToGrid(whole note, (0, 0))}
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	drawMeasure(gc);
		
    	
    	//ends here
    	
		
    }
    private void drawMeasure(GraphicsContext gc) {
		// TODO Auto-generated method stub
    	// Set stroke color, width, and global transparency
    	gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(0, 0, 0, 3000 );
        
        
	}

	public void printHandle() {
    	System.out.println("Print button is clicked");
    	
    }
    public void playHandle() {
    	System.out.println("Play button is clicked");
    }
    public void handleGotoMeasure() {}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
