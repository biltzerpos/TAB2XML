package GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InstrumentNotSupported extends Application {
	
	private MainViewController mvc;
	
	
	public InstrumentNotSupported() {
	}

	@FXML
	public void initialize() {
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	// method the reacts to click on ok button
	public void update() throws IOException {
		mvc.convertWindow.hide();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
