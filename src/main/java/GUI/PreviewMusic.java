package GUI;

import java.io.File;
import java.io.IOException;
import java.util.List;

import GUI.draw.DrawClef;
import GUI.draw.DrawMusicLines;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.measure.attributes.Clef;

public class PreviewMusic extends Application{
   
	private MainViewController mvc;
	@FXML private Pane pane;
    


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
    	
    	//drawing 10 lines of music sheet
    	/*double y = 0;
    	for (int i = 0; i < 10; i++)
    	{
    		drawMusicLine(y);
    		y = y+100;
    	}
    	
    	drawNote("0", 100, 53);
    	drawNote("2", 150, 43);
    	drawNote("2", 200, 33);
    	drawNote("1", 250, 23);
    	drawNote("0", 300, 13);
    	drawNote("0", 350, 3);
    	drawNote("0", 400, 13);
    	drawNote("1", 450, 23);
    	
    	drawBar(500, 0);
    	
    	drawNote("0", 550, 53);
    	drawNote("2", 550, 43);
    	drawNote("2", 550, 33);
    	drawNote("1", 550, 23);
    	drawNote("0", 550, 13);
    	drawNote("0", 550, 3);
    	
    	drawClef("TAB", 10, 15);*/
    	
    	double y = 0;
    	for (int i = 0; i < 10; i++)
    	{
    		DrawMusicLines d = new DrawMusicLines(pane, y);
    		y = y+100;
    	}
    	Clef clef = new Clef("TAB", 5);
    	DrawClef c = new DrawClef(pane, clef);
    	
    	//ends here
    	
    	
    }
 

	private void drawBar(double positionX, double positionY) {
    	Line bar1 = new Line();
    	bar1.setStartX(positionX);
    	bar1.setStartY(positionY);
    	bar1.setEndX(positionX);
    	bar1.setEndY(positionY + 50);
    	pane.getChildren().add(bar1);
    	Line bar2 = new Line();
    	bar2.setStartX(positionX + 5);
    	bar2.setStartY(positionY);
    	bar2.setEndX(positionX + 5);
    	bar2.setEndY(positionY + 50);
    	pane.getChildren().add(bar2);
    }
    private void drawNote(String note, double positionX, double positionY ) {
    	Text text = new Text(positionX, positionY, note );
    	Rectangle textBack = new Rectangle(positionX - 2, positionY - 12, 15, 15);
    	textBack.setFill(Color.WHITE);
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	
    	pane.getChildren().add(textBack);
    	pane.getChildren().add(text);
    	
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
