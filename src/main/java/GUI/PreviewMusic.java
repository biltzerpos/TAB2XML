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
import models.measure.barline.BarLine;

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
    	Parser parser = new Parser();
    	parser.parse(mvc.converter.getMusicXML());
    	List<Measure> measureList = parser.getMeasures();
    	double x = 0; 
    	double y = 0; 
    	for (int i = 0; i< measureList.size(); i++)
    	{
    		Measure measure = measureList.get(i);
    		List<Note> noteList = measure.getNotesBeforeBackup();
    		
    		for (int j = 0; j < noteList.size(); j++)
    		{
    			Note note = noteList.get(j);
    			DrawMusicLines d = new DrawMusicLines(pane, x, y);
    			int string = note.getNotations().getTechnical().getString();
    			int fret = note.getNotations().getTechnical().getFret();
    			d.draw();
    			x += 50;
            	double positionX =  d.getMusicLineList().get(string-1).getStartX(string-1);
            	double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);
            	drawNote(Integer.toString(fret), positionX+25, positionY+3);
            	
        		
        	}		
    	}
    	
    }
 


	private void drawBar() {
    	Line bar1 = new Line();
    	//bar1.setStartX(positionX);
    	//bar1.setStartY(positionY);
    	//bar1.setEndX(positionX);
    	//bar1.setEndY(positionY + 50);
    	pane.getChildren().add(bar1);
    	
    }
    private void drawNote(String note, double positionX, double positionY ) {
    	Text text = new Text(positionX, positionY, note );
    	Rectangle textBack = new Rectangle(positionX -3, positionY - 12, 15, 15);
    	textBack.setFill(Color.WHITE);
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    	
    	pane.getChildren().add(textBack);
    	pane.getChildren().add(text);
    	
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
