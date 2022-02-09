package GUI;

import java.io.IOException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import GUI.draw.DrawBar;
import GUI.draw.DrawMusicLines;
import GUI.draw.DrawNote;
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
    	//initialize parser
    	Parser parser = new Parser();
    	parser.parse(mvc.converter.getMusicXML());
    	//get the list of measure from parser
    	List<Measure> measureList = parser.getMeasures();
    	//set initial positions
    	double x = 0; 
    	double y = 0; 
    	for (int i = 0; i< measureList.size(); i++)
    	{
    		Measure measure = measureList.get(i);
    		// get the list of notes for each measure
    		List<Note> noteList = measure.getNotesBeforeBackup();
    		
    		for (int j = 0; j < noteList.size(); j++)
    		{
    			Note note = noteList.get(j);
    			DrawMusicLines d = new DrawMusicLines(pane, x, y);
    			int string = note.getNotations().getTechnical().getString();
    			d.draw();
    			x += 50;
            	double positionX =  d.getMusicLineList().get(string-1).getStartX(string-1);
            	double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);
            	DrawNote noteDrawer = new DrawNote(pane, note, positionX+25, positionY+3 );
            	noteDrawer.draw();
        		
        	}		
    		//draw a simple bar at end of each measure
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
