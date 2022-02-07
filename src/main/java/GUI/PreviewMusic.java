package GUI;

import java.io.IOException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import GUI.draw.DrawClef;
import GUI.draw.DrawMusicLines;
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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.Stage;
import models.measure.attributes.Clef;

import java.util.List;
import parser.Parser;
import converter.Converter;

import models.measure.Measure;
import models.measure.note.Note;
import models.measure.note.Pitch;
import models.measure.note.notations.Notations;
import models.measure.note.notations.technical.Technical;

public class PreviewMusic extends Application{
   
	private MainViewController mvc;
	@FXML private Pane pane;
	public Window convertWindow;
    


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
    	//Feel free to play around with the code. 
    	/*FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/drawMeasure.fxml"));
		AnchorPane ms = loader.load();
		
		anchorPane.getChildren().add( ms);
		
		FXMLLoader noteLoader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/MusicNotes/wholeNote.fxml"));
		AnchorPane note = noteLoader.load();
		
		anchorPane.getChildren().add(note);*/
		
		// for note E 
		//		----------------------
		//		 	O
		//		----------------------
		//
		//		----------------------
		//
		//		----------------------
		//
		//		----------------------
		//
		//		----------------------
		// for note E for example the pseudo code would something like thi:
		//if (note = 'E'){ anchorPane.addToGrid(whole note, (0, 0))}
    	
    	//drawing 10 lines of music sheet
    	
    	new DrawMusicLines(pane, 0, 0);
    	drawNote("0", 100, 53);
    	drawNote("2", 150, 43);
    	drawNote("2", 200, 33);
    	drawNote("1", 250, 23);
    	drawNote("0", 300, 13);
    	drawNote("0", 350, 3);
    	drawNote("0", 400, 13);
    	drawNote("1", 450, 23);
    	
    	drawBar(500, 0);
    	
    	drawNote("0", 550, 53);
    	drawNote("2", 550, 43);
    	drawNote("2", 550, 33);
    	drawNote("1", 550, 23);
    	drawNote("0", 550, 13);
    	drawNote("0", 550, 3);
    	drawBar(600, 0);
    	new DrawMusicLines(pane, 600, 0);
    	

    	drawClef("TAB", 10, 15);

    	Parser parser = new Parser();

    	parser.parse(mvc.converter.getMusicXML());

    	/*
    	 * EXAMPLE FOR ACCESSING ELEMENTS FROM MEASURE
    	 * 
    	 * This will print out the structure of the measures in the console.
    	 * Note that if a more complex tablature is used, the complex elements will not be parsed.
    	 */
    	System.out.println("Root:");
    	for (int i1 = 0; i1 < parser.getMeasures().size(); i1++) {
    		Measure measure = parser.getMeasures().get(i1);
			System.out.println("\tMeasure " + i1 + ":");

    		for (int i2 = 0; i2 < measure.getNotesBeforeBackup().size(); i2++) {
    			Note note = measure.getNotesBeforeBackup().get(i2);
    			System.out.println("\t\tNote " + i2 + ":");

    			System.out.println("\t\t\tPitch:");
    			System.out.println("\t\t\t\tStep: " + note.getPitch().getStep());
    			System.out.println("\t\t\t\tAlter: " + note.getPitch().getAlter());
    			System.out.println("\t\t\t\tOctave: " + note.getPitch().getOctave());

    			System.out.println("\t\t\tDuration: " + note.getDuration());

    			System.out.println("\t\t\tVoice: " + note.getVoice());

    			System.out.println("\t\t\tType: " + note.getType());

    			System.out.println("\t\t\tNotations:");
    			System.out.println("\t\t\t\tTechnical:");
    			System.out.println("\t\t\t\t\tString: " + note.getNotations().getTechnical().getString());
    			System.out.println("\t\t\t\t\tFret: " + note.getNotations().getTechnical().getFret());
    		}
    	}

    	double y = 0;
    	for (int i = 0; i < parser.getMeasures().size(); i++)
    	{
    		DrawMusicLines d = new DrawMusicLines(pane, y);
    		y = y+100;
    	}
>>>>>>> refs/heads/develop_group06
    	Clef clef = new Clef("TAB", 5);
    	DrawClef c = new DrawClef(pane, clef);
    	
    	//ends here
    	
    	
    }
 

	private void drawBar(double positionX, double positionY) {
    	Line bar1 = new Line();
    	bar1.setStartX(positionX);
    	bar1.setStartY(positionY);
    	bar1.setEndX(positionX);
    	bar1.setEndY(positionY + 50);
    	pane.getChildren().add(bar1);
    	
    }
    private void drawNote(String note, double positionX, double positionY ) {
    	Text text = new Text(positionX, positionY, note );
    	Rectangle textBack = new Rectangle(positionX - 2, positionY - 12, 15, 15);
    	textBack.setFill(Color.WHITE);
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	
    	pane.getChildren().add(textBack);
    	pane.getChildren().add(text);
    	
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
