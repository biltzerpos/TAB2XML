package GUI;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import instruments.Guitar;
import instruments.Drumset;
import instruments.Bass;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Window;
import javafx.stage.Stage;
import models.ScorePartwise;
import models.measure.Measure;

public class PreviewMusic extends Application {

	private MainViewController mvc;
	@FXML
	private Pane pane;
	public Window convertWindow;
	@FXML
	private Button printButton;
	final BooleanProperty printButtonPressed = new SimpleBooleanProperty(false);
	private ScorePartwise scorePartwise;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	TextField gotoMeasureField;
	private Guitar guitar;
	private Drumset drum;
	private Bass bass;
	private MusicPlayer play;
	@FXML
	private Button closePreviewButton;
	@FXML
	private Slider noteSpacing;
	private int noteSpacingValue;
	@FXML
	private Slider FontSizeSlider;
	private int fontSize;
	@FXML
	private Slider StaffSpacingSlider;
	private int staffSpacingValue;
	@FXML
	private Slider musicLineSlider;
	private int musicLineSpacingValue;

	@FXML
	private Slider TempoSlider;
	@FXML
	private Button playButton;

	private Sequencer sequencer;
	private Sequence sequence;
	int num = 0;
	@FXML private ChoiceBox<String> fontChoice; 
	private String fontChoiceValue; 

	public PreviewMusic() throws MidiUnavailableException, InvalidMidiDataException {
		sequencer = MidiSystem.getSequencer();
		sequencer.open();
	}

	@FXML
	public void initialize() throws InvalidMidiDataException {
		playButton.setOnMousePressed((event) -> {
			num++;
			// System.out.println("Count: " + num);
		});
		TempoSlider.valueProperty().addListener((bservableValue, oldValue, newValue) -> {
			sequencer.setTempoFactor(newValue.floatValue() / 120f);
		});
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	// method to update the preview window
	public void update() throws IOException {

		/*
		 * Get the list of measures from the ScorePartwise object.
		 *
		 * We get the list of Parts, there should be only one Part in this list, so we
		 * get the first item, which is the Part, then we get the measures from that
		 * Part.
		 */
		scorePartwise = mvc.converter.getScorePartwise();
		/*
		 * The value for the menus are set here: Range of the font size 10 - 30 and font
		 *  Range of staff spacing: 10 - 30 Range of the note
		 * spacing: 20-60 Range of the music Line spacing: 120 - 300
		 */
		// initial Font choices:
		/*    Verdana, Helvetica, Times New Roman, Comic Sans MS, Impact
    			,Lucida Sans Unicode*/
		fontChoice.getItems().add("Verdana"); 
		fontChoice.getItems().add("Helvetica");
		fontChoice.getItems().add("Times New Roman");
		fontChoice.getItems().add("Comic Sans MS");
		fontChoice.getItems().add("Impact");
		fontChoice.getItems().add("Lucida Sans Unicode");
		fontChoice.setValue("Comic Sans MS");
		
		fontChoice.setOnAction((event) -> {
		    int selectedIndex = fontChoice.getSelectionModel().getSelectedIndex();
		    Object selectedItem = fontChoice.getSelectionModel().getSelectedItem();

		    System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
		    System.out.println("   ChoiceBox.getValue(): " + fontChoice.getValue());
		});

		StaffSpacingSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				StaffSpacingSlider.setValue((double) newValue);
				int newSpacing = (int) StaffSpacingSlider.getValue();
				double fs = 0; 
				//double ns = 0; 
				double ml = 0; 
				switch (newSpacing) {
				case 10:
					FontSizeSlider.setMax(12);
					FontSizeSlider.setMin(10);
					fs = getFontSize();
					if(10 <= fs && fs<= 12) {
						FontSizeSlider.setValue(fs);
					}
					else {
						FontSizeSlider.setValue(12);
					}

//					noteSpacing.setMax(60);
//					noteSpacing.setMin(20);
//					ns = getNoteSpacingValue();
//					noteSpacing.setValue(ns);

					musicLineSlider.setMin(120);
					ml = getMusicLineSpacingValue(); 
					if(ml >= 120) {
						musicLineSlider.setValue(ml);
					}
					else {
						musicLineSlider.setValue(120);
					}
					break;
				case 15:
					FontSizeSlider.setMax(16);
					FontSizeSlider.setMin(10);
					fs = getFontSize();
					if(10 <= fs && fs<= 16) {
						FontSizeSlider.setValue(fs);
					}
					else {
						FontSizeSlider.setValue(12);
					}

//					noteSpacing.setMax(60);
//					noteSpacing.setMin(20);
//					ns = getNoteSpacingValue();
//					noteSpacing.setValue(ns);

					musicLineSlider.setMin(170);
					ml = getMusicLineSpacingValue();
					if(ml >= 170) {
						musicLineSlider.setValue(ml);
					}
					else {
						musicLineSlider.setValue(170);
					}
					break;
				case 20:
					FontSizeSlider.setMax(22);
					FontSizeSlider.setMin(10);
					fs = getFontSize();
					if(10 <= fs && fs<= 22) {
						FontSizeSlider.setValue(fs);
					}
					else {
						FontSizeSlider.setValue(12);
					}

//					noteSpacing.setMax(60);
//					noteSpacing.setMin(20);
//					ns = getNoteSpacingValue();
//					noteSpacing.setValue(ns);

					musicLineSlider.setMin(170);
					ml = getMusicLineSpacingValue();
					if(ml >= 170) {
						musicLineSlider.setValue(ml);
					}
					else {
						musicLineSlider.setValue(170);
					}
					break;
				case 25:
					FontSizeSlider.setMax(26);
					FontSizeSlider.setMin(10);
					fs = getFontSize();
					if(10 <= fs && fs<= 26) {
						FontSizeSlider.setValue(fs);
					}
					else {
						FontSizeSlider.setValue(12);
					}

//					noteSpacing.setMax(60);
//					noteSpacing.setMin(20);
//					ns = getNoteSpacingValue();
//					noteSpacing.setValue(ns);

					musicLineSlider.setMin(220);
					ml = getMusicLineSpacingValue();
					if(ml >= 220) {
						musicLineSlider.setValue(ml);
					}
					else {
						musicLineSlider.setValue(220);
					}
					break;
					
				case 30:
					FontSizeSlider.setMax(30);
					FontSizeSlider.setMin(10);
					fs = getFontSize();
					if(10 <= fs && fs<= 30) {
						FontSizeSlider.setValue(fs);
					}
					else {
						FontSizeSlider.setValue(12);
					}

//					noteSpacing.setMax(60);
//					noteSpacing.setMin(20);
//					ns = getNoteSpacingValue();
//					noteSpacing.setValue(ns);

					musicLineSlider.setMin(220);
					ml = getMusicLineSpacingValue();
					if(ml >= 220) {
						musicLineSlider.setValue(ml);
					}
					else {
						musicLineSlider.setValue(220);
					}
					break;
				}
				
			}

		});

		musicLineSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				musicLineSlider.setValue((double) newValue);
				int newSpacing = (int) musicLineSlider.getValue();
				double fs = 0; 
				//double ns = 0; 
				double ss = 0; 
				switch (newSpacing) {
				case 120:
					StaffSpacingSlider.setMin(10);
					StaffSpacingSlider.setMax(10.00001);
					StaffSpacingSlider.setValue(10);
					
					FontSizeSlider.setMin(10);
					FontSizeSlider.setMax(10.00001);
					FontSizeSlider.setValue(10);
					
					break; 
				case 170:
					StaffSpacingSlider.setMin(10);
					StaffSpacingSlider.setMax(20);
					ss = getStaffSpacingValue(); 
					if( ss <= 20) {
					StaffSpacingSlider.setValue(ss);
					}else {
						StaffSpacingSlider.setValue(10);
					}
					
					FontSizeSlider.setMin(10);
					FontSizeSlider.setMax(20);
					fs = getFontSize(); 
					if(fs <= 20) {
						FontSizeSlider.setValue(fs);
					}else {
					FontSizeSlider.setValue(10);}
					
					break; 
				default:
					StaffSpacingSlider.setMin(10);
					StaffSpacingSlider.setMax(30);
					ss = getStaffSpacingValue(); 
					StaffSpacingSlider.setValue(ss);
					
					FontSizeSlider.setMin(10);
					FontSizeSlider.setMax(30);
					fs = getFontSize(); 
					
						FontSizeSlider.setValue(fs);
					break;
				}
			}});
		

		FontSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				FontSizeSlider.setValue((double) newValue);
				int newSpacing = (int) FontSizeSlider.getValue();
				double ml = 0; 
				double ns = 0; 
				double ss = 0; 
//				if(newSpacing <= 12) {
//					noteSpacing.setMin(20);
//					noteSpacing.setMax(25);
//					ns = getNoteSpacingValue(); 
//					if(ns <=25) {
//						noteSpacing.setValue(ns);
//					}else {
//						noteSpacing.setValue(25);
//					}
//				}
//				if(newSpacing > 12 && newSpacing <=25) {
//					StaffSpacingSlider.setMin(15);
//					StaffSpacingSlider.setMax(30);
//					ss = getStaffSpacingValue(); 
//					if( ss >=15 && ss <= 30) {
//					StaffSpacingSlider.setValue(ss);
//					}else {
//						StaffSpacingSlider.setValue(15);
//					}
//				}
//				if(newSpacing > 20) {
//					noteSpacing.setMin(30);
//					ns = getNoteSpacingValue(); 
//					if(ns >= 30) {
//						noteSpacing.setValue(ss);
//					}
//					else {
//						noteSpacing.setValue(30);
//					}
//				}
//				else {
//					noteSpacing.setMin(20);
//					noteSpacing.setMax(60);
//					ns = getNoteSpacingValue(); 
//					if(ns <=60) {
//						noteSpacing.setValue(ns);
//					}else {
//						noteSpacing.setValue(30);
//					}
//				}
				if(newSpacing > 14) {
					StaffSpacingSlider.setMin(15); 
					ss = StaffSpacingSlider.getValue(); 
					if(ss>=15) {
						StaffSpacingSlider.setValue(ss); 
					}
					else {
						StaffSpacingSlider.setValue(15); 
					}
				}
				if(newSpacing > 15) {
					musicLineSlider.setMin(220); 
					ml = musicLineSlider.getValue(); 
					if(ml >= 220) {
						musicLineSlider.setValue(ml); 
					}
					else {
						musicLineSlider.setValue(220);
					}
				}
				if(newSpacing>17) {
					StaffSpacingSlider.setMin(20); 
					ss = StaffSpacingSlider.getValue(); 
					if(ss>=20) {
						StaffSpacingSlider.setValue(ss); 
					}
					else {
						StaffSpacingSlider.setValue(20); 
					}
				}
				if(newSpacing > 20) {
					musicLineSlider.setMin(270); 
					ml = musicLineSlider.getValue(); 
					if(ml >= 270) {
						musicLineSlider.setValue(ml); 
					}
					else {
						musicLineSlider.setValue(270);
					}
				}
				if(newSpacing>23) {
					StaffSpacingSlider.setMin(25); 
					ss = StaffSpacingSlider.getValue(); 
					if(ss>=25) {
						StaffSpacingSlider.setValue(ss); 
					}
					else {
						StaffSpacingSlider.setValue(25); 
					}
					
					noteSpacing.setMin(35); 
					ns = noteSpacing.getValue(); 
					if(ns >= 35) {
						noteSpacing.setValue(ns); 
					}
					else {
						noteSpacing.setValue(35); 
					}
				}
				if(newSpacing > 25) {
					musicLineSlider.setMin(320); 
					ml = musicLineSlider.getValue(); 
					if(ml >= 320) {
						musicLineSlider.setValue(ml); 
					}
					else {
						musicLineSlider.setValue(320);
					}
				}
				if(newSpacing>27) {
					
					noteSpacing.setMin(40); 
					ns = noteSpacing.getValue(); 
					if(ns >= 40) {
						noteSpacing.setValue(ns); 
					}
					else {
						noteSpacing.setValue(40); 
					}
				}
			}});


		String instrument = getInstrument();
		if (instrument == "Guitar") {
			//setDefault();
			this.guitar = new Guitar(scorePartwise, pane, getNoteSpacingValue(), getFontSize(), getStaffSpacingValue(),
					getMusicLineSpacingValue(), getFontChoiceValue());
			this.guitar.drawGuitar();
		} else if (instrument == "Drumset") {
			this.drum = new Drumset(scorePartwise, pane, 50, 10, 10, 100,sequencer.getTempoInBPM());
			this.drum.draw();
		} else if (instrument == "Bass") {
			this.bass = new Bass(scorePartwise, pane, 50, 12, 10, 150);
			this.bass.drawBass();
		}
	}

	public Sequence getMusicString() throws InvalidMidiDataException {
		this.play = new MusicPlayer(scorePartwise);

		String instrument = getInstrument();

		if (instrument == "Guitar") {
			this.sequence = play.getGuitarString();
		} else if (instrument == "Drumset") {
			this.sequence = play.getDrumString();
		} else if (instrument == "Bass") {
			this.sequence = play.getBassString();
		}
		return this.sequence;
	}

	// Method that handles `play note` button
	@FXML
	public void playHandle() throws InvalidMidiDataException {
		String instrument = getInstrument();
		if (num == 1) {
			sequencer.setSequence(getMusicString());
			if (instrument == "Guitar") {
				this.guitar.starthighlight();
				this.guitar.highlightNote();
				
			} else if (instrument == "Drumset") {
				this.drum.starthighlight();
				this.drum.highlightNote();
				
			}
			else if (instrument == "Bass") {
				this.bass.starthighlight();
				this.bass.highlightNote();
				
			}
		}
		if (sequencer.getTickPosition() == sequencer.getTickLength()) {
			sequencer.setTickPosition(0);
			if (instrument == "Guitar") {
				this.guitar.starthighlight();
				this.guitar.highlightNote();
			} else if (instrument == "Drumset") {
				this.drum.starthighlight();
				this.drum.highlightNote(); 
			}
			else if (instrument == "Bass") {
				this.bass.starthighlight();
				this.bass.highlightNote();
				
			}
		}
		sequencer.start();

	}

	@FXML
	public void pauseMusic() throws MidiUnavailableException, InvocationTargetException {
		String instrument = getInstrument();
		if (sequencer.isRunning()) {
			sequencer.stop();
			if (instrument == "Guitar") {
				this.guitar.stophighlight();
				}
				else if (instrument == "Drumset") {
					this.drum.stophighlight();
				}
				else if (instrument == "Bass") {
					this.drum.stophighlight();
				}
		}

		System.out.println("Pause bottom is clicked");
	}

	@FXML
	public void restartMusicHandle() throws InvalidMidiDataException {
		sequencer.setSequence(getMusicString());
		sequencer.start();
		String instrument = getInstrument();
		if (instrument == "Guitar") {
			this.guitar.starthighlight();
			this.guitar.highlightNote();
		} 
		else if (instrument == "Drumset") {
			this.drum.starthighlight();
			this.drum.highlightNote();
		}
		else if (instrument == "Bass") {
			this.bass.starthighlight();
			this.bass.highlightNote();	
		}
	}

	public void closeSequencer() {
		this.sequencer.close();
		//System.out.println("closeSequencer");
	}

	// Method that handles the `print music sheet` button
	@FXML
	public <printButtonPressed> void printHandle() {

		WritableImage screenshot = anchorPane.snapshot(null, null);
		Printer printer = Printer.getDefaultPrinter();
		PageLayout layout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

		double pagePrintableWidth = layout.getPrintableWidth();
		double pagePrintableHeight = layout.getPrintableHeight();
		final double scaleX = pagePrintableWidth / (1.5 * screenshot.getWidth());
		final double scaleY = pagePrintableHeight / screenshot.getHeight();
		final ImageView print_node = new ImageView(screenshot);
		print_node.getTransforms().add(new Scale(scaleX, scaleX));

		PrinterJob printSheet = PrinterJob.createPrinterJob();

		if (printSheet != null && printSheet.showPrintDialog(pane.getScene().getWindow())) {

			double numberOfPages = Math.ceil(scaleX / scaleY);
			Translate gridTransform = new Translate(0, 0);
			print_node.getTransforms().add(gridTransform);
			for (int i = 0; i < numberOfPages; i++) {
				gridTransform.setY(-i * (pagePrintableHeight / scaleX));
				printSheet.printPage(layout, print_node);
			}

			printSheet.endJob();

		}

	}

	// Method that handle navigating to specific measure (1- size of measure list)
	// through 'Go' button
	public void handleGotoMeasure() {
		int measureNumber = Integer.parseInt(gotoMeasureField.getText());
		String instrument = getInstrument();
		// System.out.println("instrument:" + instrument);
		int count = 1;
		boolean measureFound = false;
		if (instrument == "Guitar") {
			if (measureNumber == 0) {
				this.guitar.clearHighlightMeasureArea();
				measureFound = true;
			} else {
				List<Measure> measureList = this.guitar.getMeasureList();
				for (Iterator<Measure> iterator = measureList.iterator(); iterator.hasNext();) {
					Measure measure = (Measure) iterator.next();
					if (measureNumber == count) {
						this.guitar.highlightMeasureArea(measure);
						measureFound = true;
						break;
					}
					count++;
				}
			}
		} else if (instrument == "Drumset") {
			if (measureNumber == 0) {
				this.drum.clearHighlightMeasureArea();
				measureFound = true;
			} else {
				List<Measure> measureList = this.drum.getMeasureList();
				for (Iterator<Measure> iterator = measureList.iterator(); iterator.hasNext();) {
					Measure measure = (Measure) iterator.next();
					if (measureNumber == count) {
						this.drum.highlightMeasureArea(measure);
						measureFound = true;
						break;
					}
					count++;
				}
			}
		} else if (instrument == "Bass") {
			if (measureNumber == 0) {
				this.bass.clearHighlightMeasureArea();
				measureFound = true;
			} else {
				List<Measure> measureList = this.bass.getMeasureList();
				for (Iterator<Measure> iterator = measureList.iterator(); iterator.hasNext();) {
					Measure measure = (Measure) iterator.next();
					if (measureNumber == count) {
						this.bass.highlightMeasureArea(measure);
						measureFound = true;
						break;
					}
					count++;
				}
			}
		}
		if (!measureFound) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Measure " + measureNumber + " could not be found.");
			alert.setHeaderText("Preview Music Sheet");
			alert.show();
		}
	}

	// Method that handles `close` button
	@FXML
	public void closePreviewHandle() {
		// mvc.editHandle();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close this Preview window?",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.setTitle("Close preview window");
		alert.setHeaderText("You are about to close the Preview window!");
		Optional<ButtonType> o = alert.showAndWait();

		if (o.get() == ButtonType.YES) {
			mvc.convertWindow.hide();
			sequencer.close();
		}
	}

	// return a string representation of the instrument
	private String getInstrument() {
		String instrument = scorePartwise.getPartList().getScoreParts().get(0).getPartName();
		return instrument;

	}

	@FXML
	private void ApplySettings() {
		pane.getChildren().clear();

		String instrument = getInstrument();
		if (instrument == "Guitar") {
			this.guitar = new Guitar(scorePartwise, pane, getNoteSpacingValue(), getFontSize(), getStaffSpacingValue(),
					getMusicLineSpacingValue(), getFontChoiceValue());
			this.guitar.drawGuitar();
		} else if (instrument == "Bass") {
			this.bass = new Bass(scorePartwise, pane, 50, 12, 10, 150);
			this.bass.drawBass();
		} else if (instrument.equals("Drumset")) {
			this.drum = new Drumset(scorePartwise, pane, this.getNoteSpacingValue(), this.getFontSize(), this.getStaffSpacingValue(), this.getMusicLineSpacingValue(),sequencer.getTempoInBPM());
			this.drum.draw();
		}

	}

	@FXML
	private void defaultSetting() {
		pane.getChildren().clear();
		String instrument = getInstrument();

		if (instrument == "Guitar") {
			//this.guitar = new Guitar(scorePartwise, pane, 30, 12, 10, 170);
			setDefaultSliders(); 
			this.guitar = new Guitar(scorePartwise, pane, getNoteSpacingValue(), getFontSize(), getStaffSpacingValue(),
					getMusicLineSpacingValue(), getFontChoiceValue());
			this.guitar.drawGuitar();
		} else if (instrument == "Bass") {
			this.bass = new Bass(scorePartwise, pane, 50, 12, 10, 150);
			this.bass.drawBass();
		} else if (instrument.equals("Drumset")) {
			this.drum = new Drumset(scorePartwise, pane, 50, 10, 10, 100,sequencer.getTempoInBPM());
			this.drum.draw();
		}
	}

	private void setDefaultSliders() {
		// TODO Auto-generated method stub
		//staff slider:
		this.StaffSpacingSlider.setMin(10);
		this.StaffSpacingSlider.setMax(30);
		this.StaffSpacingSlider.setValue(10);
		
		// noteSpacing slider
		this.noteSpacing.setMin(20);
		this.noteSpacing.setMax(60);
		this.noteSpacing.setValue(30);
		
		// musicLine slider
		this.musicLineSlider.setMin(120);
		this.musicLineSlider.setMax(420);
		this.musicLineSlider.setValue(170);
		
		//fontSizeSlider
		this.FontSizeSlider.setMin(10);
		this.FontSizeSlider.setMax(30);
		this.FontSizeSlider.setValue(12);
		
		// font choice
		this.fontChoice.setValue("Comic Sans MS");
	}

	public int getNoteSpacingValue() {
		this.noteSpacingValue = (int) noteSpacing.getValue();
		return noteSpacingValue;
	}

	public int getFontSize() {
		this.fontSize = (int) FontSizeSlider.getValue();
		return fontSize;
	}

	public int getStaffSpacingValue() {
		this.staffSpacingValue = (int) StaffSpacingSlider.getValue();
		return staffSpacingValue;
	}

	public int getMusicLineSpacingValue() {
		this.musicLineSpacingValue = (int) musicLineSlider.getValue();
		return musicLineSpacingValue;
	}

	public void setNoteSpacingValue(int noteSpacingValue) {
		this.noteSpacingValue = noteSpacingValue;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setStaffSpacingValue(int staffSpacingValue) {
		this.staffSpacingValue = staffSpacingValue;
	}

	public void setMusicLineSpacingValue(int musicLineSpacingValue) {
		this.musicLineSpacingValue = musicLineSpacingValue;
	}

	public ChoiceBox<String> getFontChoice() {
		return fontChoice;
	}

	public void setFontChoice(ChoiceBox<String> fontChoice) {
		this.fontChoice = fontChoice;
	}
	

	public String getFontChoiceValue() {
		this.fontChoiceValue = fontChoice.getValue(); 
		return fontChoiceValue;
	}

	public void setFontChoiceValue(String fontChoiceValue) {
		this.fontChoiceValue = fontChoiceValue;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}