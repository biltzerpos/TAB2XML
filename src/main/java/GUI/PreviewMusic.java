package GUI;

import java.io.IOException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import GUI.draw.DrawBar;
import GUI.draw.DrawMusicLines;
import GUI.draw.DrawNote;
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
	    List<Measure> measures = scorePartwise.getParts().get(0).getMeasures();

	    // Set initial positions
        double x = 0;
        double y = 0;

        for (Measure measure : measures) {

        	// Get the list of notes for each measure
        	List<Note> notes = measure.getNotesBeforeBackup();

        	// Initialize music lines drawer
			DrawMusicLines d = new DrawMusicLines(pane, x, y);

    		for (Note note : notes) {
    			// Get the string of the current note
    			int string = note.getNotations().getTechnical().getString();

    			// If note is not a chord, then draw new music lines, draw note, and increment x
    			if (note.getChord() == null) {
    				d = new DrawMusicLines(pane, x, y);
	    			d.draw();

	            	double positionX =  d.getMusicLineList().get(string-1).getStartX(string-1);
	            	double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);

	            	DrawNote noteDrawer = new DrawNote(pane, note, positionX+25, positionY+3 );
	            	noteDrawer.draw();

	    			x += 50;

	    		// If note is a chord, then just draw the note without drawing new music lines or incrementing x
    			} else {
	            	double positionX =  d.getMusicLineList().get(string-1).getStartX(string-1);
	            	double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);

	            	DrawNote noteDrawer = new DrawNote(pane, note, positionX+25, positionY+3 );
	            	noteDrawer.draw();
    			}
    		}

    		// Draw a simple bar at end of each measure
    		DrawBar barDrawer = new DrawBar(pane, x, y);
    		barDrawer.draw();
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
