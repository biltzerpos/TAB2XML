package GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class InstrumentNotSupported extends Application {
	
	private MainViewController mvc;
	public Window convertWindow;
	
	public InstrumentNotSupported() {
	}

	@FXML
	public void initialize() {
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	// We can use this method to update the music sheet
	public void update() throws IOException {
		mvc.convertWindow.hide();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
