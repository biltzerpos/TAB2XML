package GUI;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.xml.parsers.ParserConfigurationException;

//import java.io.File;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.jfugue.integration.MusicXmlParser;
import org.jfugue.midi.MidiParserListener;

import converter.Instrument;
import custom_exceptions.TXMLException;
import javafx.application.Application;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import player.DrumInstrument;
import utility.Settings;

public class PlayMusicController extends Application {

//	public File saveFile;
	private MainViewController mvc;
	public Highlighter highlighter;

	@FXML
	public CodeArea mxlText;
	@FXML
	TextField gotoMeasureField;
	@FXML
	Button goToline;

	public PlayMusicController() {

	}

	@FXML
	public void initialize() {
		mxlText.setParagraphGraphicFactory(LineNumberFactory.get(mxlText));
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	public void update() throws ParserConfigurationException, ValidityException, ParsingException, IOException,
			MidiUnavailableException, InvalidMidiDataException, TXMLException {
   
		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
			DrumInstrument drumPlayer = new DrumInstrument();
			drumPlayer.playDrums(mvc.converter.getScore().getModel().getParts().get(0).getMeasures(),
					mvc.converter.getScore());
		} else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
			MusicXmlParser parser = new MusicXmlParser();
			MidiParserListener midilistener = new MidiParserListener();
			parser.addParserListener(midilistener);
			parser.parse(mvc.converter.getMusicXML());

			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();

			Sequence sequence = midilistener.getSequence();
			Track track = sequence.createTrack();

			ShortMessage sm = new ShortMessage();
			sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 24, 0);
			track.add(new MidiEvent(sm, 1));
			System.out.println("Size of track: " + track.size());

			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(280);
			
			sequencer.start();
		}

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
