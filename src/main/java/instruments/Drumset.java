package instruments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;
import models.measure.note.Notehead;
import GUI.draw.DrawDrumsetBar;
import GUI.draw.DrawDrumsetMusicLines;
import GUI.draw.DrawNote;
import GUI.draw.DrawDrumsetNote;

public class Drumset {

	private ScorePartwise scorePartwise;
	@FXML private Pane pane;
	private List<Measure> measureList;
	private Clef clef;
	private HashMap<Measure, Double> xCoordinates;
	private HashMap<Measure, Double> yCoordinates;

	public Drumset(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.clef = this.scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
		xCoordinates = new HashMap<>();
		yCoordinates = new HashMap<>();

	}

	public void draw() {

		// Initialize x and y coordinates
		double x = 0;
		double y = 0;

		// Draw the initial music lines
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);
		d.draw(x,y);



		// Iterate through the list of measures
		for (Measure measure : measureList) {

			// Iterate through the notes in the current measure
			for (Note note : measure.getNotesBeforeBackup()) {

				Notehead symbol = note.getNotehead();

				String step = note.getUnpitched().getDisplayStep();
				int octave = note.getUnpitched().getDisplayOctave();

				// Get the y-position based on the octave and step
				double positionY = d.getYPositionFromOctaveAndStep(octave, step);

				if (note.getChord() == null) {
					// Only draw music lines if not a chord.
					// This is because if it is a chord, the music lines from the last chord will be used.
					// Also draw the music lines before drawing the note so that the note appears on top.
					d.draw(x,y);

					DrawDrumsetNote noteDrawer = new DrawDrumsetNote(this.pane, note, x+25, positionY+3);
					noteDrawer.drawDrumClef1();
					noteDrawer.drawDrumClef2();

					// If note head exists and is an x, then draw "x", otherwise draw "o"
					if (symbol != null && symbol.getType().equals("x")) {
						noteDrawer.drawX();
					}
					else {
						noteDrawer.drawO();
					}

					x+=50;
				}
				else {
					DrawDrumsetNote noteDrawer = new DrawDrumsetNote(this.pane, note, x-25, positionY+3 );

					// If note head exists and is an x, then draw "x", otherwise draw "o"
					if (symbol != null && symbol.getType().equals("x")) {
						noteDrawer.drawX();
					}
					else {
						noteDrawer.drawO();
					}
				}
			}
			xCoordinates.put(measure, x);
			yCoordinates.put(measure, y);
			// Draw bar line after every measure
			DrawDrumsetBar bar = new DrawDrumsetBar(this.pane);
			bar.draw(x, y);
		}
	}

	public void highlightMeasureArea(Measure measure) {
		double x = 0;
		double y = 0;

		ObservableList children = pane.getChildren();
		ArrayList<Rectangle> removeRect = new ArrayList<Rectangle>();
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object instanceof Rectangle) {
				System.out.println("Rectangle Added to removal list.");
				removeRect.add((Rectangle) object);
			}
		}

		for (Iterator iterator = removeRect.iterator(); iterator.hasNext();) {
			Rectangle rect = (Rectangle) iterator.next();
			System.out.println("Rectangle Removed as part of reset.");
			pane.getChildren().remove(rect);
		}
		System.out.println("Reset Done");
		x = 0;
		y = 0;
		// now iterate again to highlight it in red
		for (int i = 0; i < measureList.size(); i++) {
			System.out.println("X Coordinates:" + getXCoordinatesForGivenMeasure(measureList.get(i)));
			System.out.println("Y Coordinates:" + getYCoordinatesForGivenMeasure(measureList.get(i)));
			double w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
			double yf = getYCoordinatesForGivenMeasure(measureList.get(i));
			if (yf > y) {
				System.out.println("Jumped to new Line");
				// we have moved on to new Line
				x = 0;
				w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
				y = yf;
			}
			if (measure.equals(measureList.get(i))) {
				System.out.println("Four Rectangle values are:(" + x + "," + y + "," + w + "," + 50 + ")");
				System.out.println("X Coordinates to Highlight:" + getXCoordinatesForGivenMeasure(measure));
				System.out.println("Y Coordinates to Highlight:" + getYCoordinatesForGivenMeasure(measure));
				Rectangle rectangle = new Rectangle(x, yf, w, 50);
				rectangle.setFill(Color.TRANSPARENT);
				rectangle.setStyle("-fx-stroke: red;");
				pane.getChildren().add(rectangle);
			}
			x = getXCoordinatesForGivenMeasure(measureList.get(i));
			y = yf;
		}
	}


	public String getDrumNoteFullName(String Id) {
		String fullName = "";

		if(Id == "P1-I50") {
			fullName = "Crash_Cymbal_1";
		}else if(Id == "P1-I36"){
			fullName = "Bass_Drum";
		}else if(Id == "P1-I39"){
			fullName = "Acoustic_Snare";
		}else if(Id == "P1-I43"){
			fullName = "Closed_Hi_Hat";
		}else if(Id == "P1-I47"){
			fullName = "Open_Hi_Hat";
		}else if(Id == "P1-I52"){
			fullName = "Ride_Cymbal_1";
		}else if(Id == "P1-I54"){
			fullName = "Ride_Bell";
		}else if(Id == "P1-I53"){
			fullName = "Chinese_Cymbal_1";
		}else if(Id == "P1-I48"){
			fullName = "Lo_Mid_Tom";
		}else if(Id == "P1-I46"){
			fullName = "Lo_Tom";
		}else if(Id == "P1-I44"){
			fullName = "High_Floor_Tom";
		}else if(Id == "P1-I42"){
			fullName = "Low_Floor_Tom";
		}else if(Id == "P1-I45"){
			fullName = "Pedal_Hi_Hat";
		}

		return fullName;
	}
	private String addDuration(Note note) {
		String res = "";
		String type = note.getType();

		if (type.equals("whole")) {
			res = "w";
		} else if (type.equals("half")) {
			res = "h";
		} else if (type.equals("quarter")) {
			res = "q";
		} else if (type.equals("eighth")) {
			res = "i";
		} else if (type.equals("16th")) {
			res = "s";
		}else if (type.equals("32nd")) {
			res = "t";
		}

		return res;
	}

	// This method plays the notes
	public void playDrumNote() {
		Player player = new Player();
		Pattern vocals = new Pattern();
		String drumNote = "";
		int voice = 0;

		for (int i = 0; i < measureList.size(); i++) {
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();

			for (int j = 0; j < noteList.size(); j++) {
				String drumId = "";
				String ns = new String();
				Note note = noteList.get(j);
				voice = note.getVoice();
				drumId = note.getInstrument().getId();
				ns = "[" + getDrumNoteFullName(drumId) + "]";
				String dur = addDuration(note);

				if (note.getChord() == null) {
					drumNote += " V9 " + ns + dur;
				}else {
					drumNote += "+" + ns + dur;
				}
			}
		}

		vocals.add(drumNote);
		//System.out.println(vocals.toString());
		vocals.setVoice(voice);
		vocals.setTempo(120);
		player.play(vocals);
	}

	// return X coordinates for given measure
	public double getXCoordinatesForGivenMeasure(Measure measure) {
		return xCoordinates.get(measure);
	}

	// return Y coordinates for given measure
	public double getYCoordinatesForGivenMeasure(Measure measure) {
		return yCoordinates.get(measure);
	}

	public double getPositionYFromOctaveAndStep(int octave, String step) {
		return 0;
	}

	// returns a list of measures
	public List<Measure> getMeasureList() {
		return measureList;
	}

}