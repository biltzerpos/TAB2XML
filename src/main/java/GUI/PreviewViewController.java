package GUI;

import custom_exceptions.TXMLException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import nu.xom.ParsingException;
import org.jfugue.integration.MusicXmlParser;
import org.jfugue.integration.MusicXmlParserListener;
import org.jfugue.midi.MidiParser;
import visualizer.visualizer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PreviewViewController extends Application {
	@FXML Canvas sheetCanvas;
	private MainViewController mvc;
	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}
	public void update() throws TXMLException {
		visualizer sheet = new visualizer(mvc.converter.getScore());
		sheet.draw(sheetCanvas);
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
