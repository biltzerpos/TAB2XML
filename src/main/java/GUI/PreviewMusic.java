package GUI;

import java.io.File;


import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
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
    
    @FXML
    private Button printButton;
    
    final BooleanProperty printButtonPressed = new SimpleBooleanProperty(false);
    
    @FXML
    public <printButtonPressed> void printHandle() {
    	printButton.setOnAction( aEvent -> {
    	    printButtonPressed.set(true);
    	});
    	
    	Printer printer = Printer.getDefaultPrinter();
        PageLayout layout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterJob printSheet = PrinterJob.createPrinterJob();
       
        if (printSheet != null && printSheet.showPrintDialog(pane.getScene().getWindow())) {
          boolean printed = printSheet.printPage(layout, pane);
          if (printed) {
        	  printSheet.endJob();
          }
        }
    }


    public void playHandle() {
    	System.out.println("Play button is clicked");
    }
    public void handleGotoMeasure() {}
}
