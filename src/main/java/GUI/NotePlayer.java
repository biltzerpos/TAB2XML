package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class NotePlayer extends Application{
	
	
	public void play() {
    	System.out.println("Play button is clicked");
    	
    }
	
	public void pause() {
    	System.out.println("Pause button is clicked");
    	
    }
	
	public void exit() {
    	System.out.println("Exit button is clicked");
    	
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setMainViewController(PreviewMusic previewMusic) {
		// TODO Auto-generated method stub
		
	}

}
