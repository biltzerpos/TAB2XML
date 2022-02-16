package GUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//import java.io.File;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

//import org.fxmisc.richtext.CodeArea;
//import org.fxmisc.richtext.LineNumberFactory;

import custom_exceptions.TXMLException;
import javafx.application.Application;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pdfbox.pdfbuilder;

public class PreviewSheetMusicController extends Application {

//	public File saveFile;
    private MainViewController mvc;
	public Highlighter highlighter;
	private pdfbuilder pdf;

	@FXML public ImageView imageview;
	@FXML TextField gotoMeasureField;
	@FXML Button goToline;

	public PreviewSheetMusicController() {

	}

	@FXML
	public void initialize() {
		pdf = new pdfbuilder();
	}

    public void setMainViewController(MainViewController mvcInput) {
    	mvc = mvcInput;
    }

    public void update() throws IOException, TXMLException {
//    	pdf.sheetpdf(mvc.converter.getScore().getModel().getParts().get(0));
//    	imageview.setImage(pdf.getImage(0));
//    	imageview.setX(78);
//    	imageview.setY(99);
    	
//    	 InputStream stream = new FileInputStream(System);
//         Image image = new Image(stream);
//         //Creating the image view
//         ImageView imageView = new ImageView();
//         //Setting image to the image view
//         imageView.setImage(image);
	}

//	@FXML
//	private void saveMXLButtonHandle() {
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