package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PreviewViewController extends Application {
	private MainViewController mvc;
	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}
	public void update() {
	}
		@FXML
	private void gotoMeasureHandler(){

	}
	@FXML
	private void exportPDFHandler(){

	}
	@Override
	public void start(Stage primaryStage) throws Exception {}
}
