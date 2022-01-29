package GUI;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PreviewMusic extends Application{
    private MainViewController mvc;
    @FXML private AnchorPane anchorPane;



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
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/drawMeasure.fxml"));
		AnchorPane ms = loader.load();
		anchorPane.getChildren().add( ms);
		FXMLLoader loader1 = new FXMLLoader(getClass().getClassLoader().getResource("GUI/drawMeasure.fxml"));
		AnchorPane ms1 = loader1.load();
		anchorPane.getChildren().add( ms1);
		ms1.setLayoutX(ms1.getLayoutX()+400);
		
		//ends here
		
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
