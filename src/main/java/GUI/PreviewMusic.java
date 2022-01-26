package GUI;

import java.io.File;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PreviewMusic {
    private MainViewController mvc;


	public PreviewMusic() {}

	@FXML 
	public void initialize() {
	}

    public void setMainViewController(MainViewController mvcInput) {
    	mvc = mvcInput;
    }
}
