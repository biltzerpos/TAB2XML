package GUI;

import java.io.IOException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import GUI.draw.DrawBar;
import GUI.draw.DrawMusicLines;
import GUI.draw.DrawNote;
import instruments.Guitar;
import custom_exceptions.TXMLException;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.Stage;

import parser.Parser;

import models.ScorePartwise;
import models.measure.Measure;
import models.measure.note.Note;

public class PreviewMusic extends Application{
   
	private MainViewController mvc;
	@FXML private Pane pane;
	public Window convertWindow;
    @FXML
    private Button printButton;
    final BooleanProperty printButtonPressed = new SimpleBooleanProperty(false);


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
    	// Get the ScorePartwise object directly
    	ScorePartwise scorePartwise = mvc.converter.getScorePartwise();
    	
    	/* Get the list of measures from the ScorePartwise object.
    	 *
    	 * We get the list of Parts, there should be only one Part in this list,
    	 * so we get the first item, which is the Part, then we get the measures from that Part.
    	 */

    	String instrument = scorePartwise.getPartList().getScoreParts().get(0).getPartName();
    	if(instrument == "Guitar") {
    		Guitar g = new Guitar(scorePartwise, pane);
    		g.draw();
    	}

	  
    }
    
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

	@FXML
    public void playHandle() {
    	Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/tabPlayer.fxml"));
			root = loader.load();
			NotePlayer controller = loader.getController();
			controller.setMainViewController(this);
			
			convertWindow = this.openNewWindow(root, "Play Music Notes");
		} catch (IOException e) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.log(Level.SEVERE, "Failed to create new Window.", e);
		}
    }
    private Window openNewWindow(Parent root, String windowName) {
    	Stage stage = new Stage();
		stage.setTitle(windowName);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(MainApp.STAGE);
		stage.setResizable(false);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		return scene.getWindow();
	}

	public void handleGotoMeasure() {}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
