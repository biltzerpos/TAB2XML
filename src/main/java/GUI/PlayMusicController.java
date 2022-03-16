package GUI;

import java.io.FileNotFoundException;
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
import org.jfugue.player.Player;

import converter.Instrument;
import custom_exceptions.TXMLException;
import design2.CanvasGen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import player.DrumInstrument;
import player.StringInstruments;
import utility.Settings;

public class PlayMusicController extends Application {

	//	public File saveFile;

	private MainViewController mvc;
	public Highlighter highlighter;

	private DrumInstrument drumPlayer;
	private StringInstruments stringsPlayer;

	//	private boolean playCondition = false;

	@FXML
	public CodeArea mxlText;

	//	@FXML
	//	Button playMusicNotes;

	@FXML
	TextField gotoMeasureField;

	// Music Player Attributes

	@FXML
	private BorderPane borderPane;

	@FXML
	private Button playButton;

	@FXML
	private Button pauseButton;

	@FXML
	private Button rewindButton;

	@FXML
	private Button stopButton;

	@FXML
	private Pane centerpane;

	@FXML
    private Canvas canvas;

	public PlayMusicController() {

	}

	public void display() throws FileNotFoundException, TXMLException {
		canvas = new CanvasGen(canvas.getHeight(), canvas.getWidth(), this.mvc);
		centerpane.getChildren().add(canvas);

	}

	@FXML
	public void initialize() throws FileNotFoundException, TXMLException {
		//		mxlText.setParagraphGraphicFactory(LineNumberFactory.get(mxlText));
		pauseButton.setDisable(true);
		rewindButton.setDisable(true);
		stopButton.setDisable(true);
		display();
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
//		((CanvasGen) canvas).mvc = mvcInput;
	}

	// Closes previewer and goes back to TAB2XML input panel
	@FXML
	private void homeBtn(ActionEvent event) {
		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
			drumPlayer.player.getManagedPlayer().finish();
		}
		else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
			stringsPlayer.player.getManagedPlayer().finish();
		}
		Stage stage = (Stage) borderPane.getScene().getWindow();
		stage.close();
	}

	public void update() throws TXMLException, IOException {
		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
			drumPlayer = new DrumInstrument();
			drumPlayer.setupDrums(mvc.converter.getScore().getModel().getParts().get(0).getMeasures(),
					mvc.converter.getScore());
		} else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
			stringsPlayer = new StringInstruments();
			stringsPlayer.setupStringInstruments(mvc.converter.getScore());
		}
		((CanvasGen) canvas).draw(this.mvc.converter.getScore());

		//		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
		//			DrumInstrument drumPlayer = new DrumInstrument();
		//			drumPlayer.playDrums(mvc.converter.getScore().getModel().getParts().get(0).getMeasures(),
		//					mvc.converter.getScore());
		//		} else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
		//			MusicXmlParser parser = new MusicXmlParser();
		//			MidiParserListener midilistener = new MidiParserListener();
		//			parser.addParserListener(midilistener);
		//			parser.parse(mvc.converter.getMusicXML());
		//
		//			Sequencer sequencer = MidiSystem.getSequencer();
		//			sequencer.open();
		//
		//			Sequence sequence = midilistener.getSequence();
		//			Track track = sequence.createTrack();
		//
		//			ShortMessage sm = new ShortMessage();
		//			sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 24, 0);
		//			track.add(new MidiEvent(sm, 1));
		//			System.out.println("Size of track: " + track.size());
		//
		//			sequencer.setSequence(sequence);
		//			sequencer.setTempoInBPM(280);
		//
		//			sequencer.start();
		//
		//			StringInstruments stringsPlayer = new StringInstruments();
		//			stringsPlayer.playStringInstruments(mvc.converter.getScore());
		//		}
	}

	// Media Controls

	@FXML
	void playBtn(ActionEvent event) throws TXMLException, InvalidMidiDataException, MidiUnavailableException, InterruptedException {
		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
			drumPlayer.play();
		}
		else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
			stringsPlayer.play();
		}
		pauseButton.setDisable(false);
		rewindButton.setDisable(false);
		stopButton.setDisable(false);
	}

	@FXML
	void pauseBtn(ActionEvent event) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
			drumPlayer.pause();
		}
		else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
			stringsPlayer.pause();
		}
	}

	@FXML
	private void rewindBtn(ActionEvent event) {
		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
			drumPlayer.rewind();
		}
		else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
			stringsPlayer.rewind();
		}
	}

	@FXML
	private void stopBtn(ActionEvent event) throws MidiUnavailableException {
		if (Settings.getInstance().getInstrument() == Instrument.DRUMS) {
			drumPlayer.stop();
		}
		else if (Settings.getInstance().getInstrument() == Instrument.GUITAR) {
			stringsPlayer.stop();
		}
		pauseButton.setDisable(true);
		rewindButton.setDisable(true);
		stopButton.setDisable(true);
	}

	//	@FXML
	//	private void PlayerHandle() {
	//		if(playCondition != true) {
	//			playMusicNotes.setText("Play");
	//			playCondition = false;
	//		}
	//		else if(playCondition == true) {
	//			playMusicNotes.setText("Pause");
	//			playCondition = true;
	//		}
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
