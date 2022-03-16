package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

//import java.io.File;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

//import org.fxmisc.richtext.CodeArea;
//import org.fxmisc.richtext.LineNumberFactory;

import custom_exceptions.TXMLException;
import design2.CanvasGen;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import previewer.pdfbuilder;

public class PreviewSheetMusicController extends Application {

	//	public File saveFile;
	private MainViewController mvc;
	public Highlighter highlighter;
	private pdfbuilder pdf;

	@FXML
	public ImageView imageview;
	@FXML
	TextField gotoMeasureField;
	@FXML
	Button goToline;

	@FXML
	private Canvas canvas;

	@FXML
	private Pane centerpane;

	public void display() throws FileNotFoundException, TXMLException {
		canvas = new CanvasGen(canvas.getHeight(), canvas.getWidth(), this.mvc);
		centerpane.getChildren().add(canvas);
	}
	
	public PreviewSheetMusicController() {

	}

	public void saveBtn() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		//		pdf.save();
	}

	@FXML
	public void initialize() throws FileNotFoundException, TXMLException {
//		pdf = new pdfbuilder();
		display();
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	public void update() throws IOException, TXMLException {
//		pdf.sheetpdf(mvc.converter.getScore());
		((CanvasGen) canvas).draw(this.mvc.converter.getScore());
		//	imageview.setImage(pdf.getImage(0));
		//	imageview.setPreserveRatio(true);
	}


	//	@FXML
	//	private void saveMXLButtonHandle() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	//		mvc.saveMXLButtonHandle();
	//	}

	//	//TODO add go to line button
	//	@FXML
	//	private void handleGotoMeasure() {
	//		int measureNumber = Integer.parseInt(gotoMeasureField.getText() );
	//		if (!goToMeasure(measureNumber)) {
	//			Alert alert = new Alert(Alert.AlertType.ERROR);
	//			alert.setContentText("Measure " + measureNumber + " could not be found.");
	//			alert.setHeaderText(null);
	//			alert.show();
	//		}
	//	}

	//    private boolean goToMeasure(int measureCount) {
	//    	//Pattern textBreakPattern = Pattern.compile("((\\R|^)[ ]*(?=\\R)){2,}|^|$");
	//    	Pattern mxlMeasurePattern = Pattern.compile("<measure number=\"" + measureCount + "\">");
	//        Matcher mxlMeasureMatcher = mxlMeasurePattern.matcher(mxlText.getText());
	//
	//        if (mxlMeasureMatcher.find()) {
	//        	int pos = mxlMeasureMatcher.start();
	//        	mxlText.moveTo(pos);
	//        	mxlText.requestFollowCaret();
	//        	Pattern newLinePattern = Pattern.compile("\\R");
	//        	Matcher newLineMatcher = newLinePattern.matcher(mxlText.getText().substring(pos));
	//        	for (int i = 0; i < 30; i++) newLineMatcher.find();
	//        	int endPos = newLineMatcher.start();
	//        	mxlText.moveTo(pos+endPos);
	//        	mxlText.requestFollowCaret();
	//        	//mxlText.moveTo(pos);
	//            mxlText.requestFocus();
	//            return true;
	//            }
	//        else return false;
	//    }

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}