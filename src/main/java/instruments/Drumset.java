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
import GUI.draw.DrawClef;
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

	private double x;
	private double y;

	public Drumset(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.clef = this.scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
		xCoordinates = new HashMap<>();
		yCoordinates = new HashMap<>();

		this.x = 0;
		this.y = 0;
	}

	private void drawGroupedNotes(List<Note> notes) {
		Note currentNote, nextNote;
		DrawDrumsetNote noteDrawer;
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);

		d.draw(this.x, this.y);

		for (int i = 0; i < notes.size(); i++) {
			currentNote = notes.get(i);
			nextNote = null;
			for (int j = i + 1; j < notes.size(); j++) {
				if (notes.get(j).getChord() == null) {
					nextNote = notes.get(j);
				}
			}

			d.draw(this.x, this.y);
			noteDrawer = new DrawDrumsetNote(
				this.pane,
				currentNote,
				this.y,
				currentNote.getChord() == null ? this.x + 25 : this.x - 25,
				d.getYPositionFromOctaveAndStep(
					currentNote.getUnpitched().getDisplayOctave(),
					currentNote.getUnpitched().getDisplayStep()
				)
			);

			if (currentNote.getChord() != null) {
				noteDrawer.draw();
			} else if (currentNote.getType().equals("eighth") && nextNote != null) {
				noteDrawer.draw();
				noteDrawer.drawBeam();
			} else if (currentNote.getType().equals("16th") && nextNote != null && nextNote.getType().equals("16th")) {
				noteDrawer.draw();
				noteDrawer.drawBeam();
			} else {
				noteDrawer.draw();
			}

			this.x += currentNote.getChord() == null ? 50 : 0;
		}
	}

	private void drawUngroupedNotes(List<Note> notes) {
		System.out.println("-----");
		for (Note note : notes) {
			System.out.println(note.getUnpitched().getDisplayStep());
		}
		System.out.println("-----");
		Note currentNote;
		DrawDrumsetNote noteDrawer;
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);

		d.draw(this.x, this.y);

		for (int i = 0; i < notes.size(); i++) {
			currentNote = notes.get(i);

			if (currentNote.getChord() == null) {
				d.draw(this.x, this.y);
			}

			noteDrawer = new DrawDrumsetNote(
				this.pane,
				currentNote,
				this.y,
				currentNote.getChord() == null ? this.x + 25 : this.x - 25,
				d.getYPositionFromOctaveAndStep(
					currentNote.getUnpitched().getDisplayOctave(),
					currentNote.getUnpitched().getDisplayStep()
				)
			);

			noteDrawer.draw();
			if (currentNote.getChord() != null) {
				noteDrawer.drawFlag();
			}

			this.x += currentNote.getChord() == null ? 50 : 0;
		}
	}

	private void drawMeasure(Measure measure) {

		int divisions = measure.getAttributes().getDivisions();

		List<Note> noteList = measure.getNotesBeforeBackup();
		int i = 0;
		Note currentNote;

		List<Note> group = new ArrayList<>();
		int durationSum = 0;

		while (i < noteList.size()) {
			System.out.println(i);
			currentNote = noteList.get(i);
			group.add(currentNote);
			durationSum += currentNote.getChord() == null ? currentNote.getDuration() : 0;
			i++;

			// When durationSum % divisions == 0, that means we stopped at a beat
			while (durationSum != 0 && durationSum % divisions != 0) {
				currentNote = noteList.get(i);
				group.add(currentNote);
				durationSum += currentNote.getChord() == null ? currentNote.getDuration() : 0;
				i++;
			}

			// If the last note in the group is followed by chord notes, they will not be found by the above loop.
			// This loop makes sure they are added to the group.
			while (i < noteList.size() && noteList.get(i).getChord() != null) {
				group.add(noteList.get(i));
				i++;
			}

			// If durationSum == divisions, then the group is one beat (so draw the group together).
			// Else, the group extends over more than one beat (so draw the group as individual notes
			// because it may be too complex to draw).
			if (durationSum == divisions) {
				this.drawGroupedNotes(group);
			} else {
				this.drawUngroupedNotes(group);
			}

			// Reset the group and duration sum for the next group of notes
			group.clear();
			durationSum = 0;
		}

	}

	public void draw() {

		// Initialize x and y coordinates
		this.x = 0;
		this.y = 0;

//		// Draw scale for testing
//		for (int i = 0; i < 500; i += 5) {
//			int xEnd = i % 50 == 0
//				? 20
//				: i % 10 == 0
//					? 10
//					: 5;
//			pane.getChildren().add(new javafx.scene.shape.Line(0, i, xEnd, i));
//		}

		// Draw the initial music lines
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);
		d.draw(this.x, this.y);

		DrawClef drumclef = new DrawClef(this.pane, clef, x+25, 0);
		drumclef.drawDrumClef1();
		drumclef.drawDrumClef2();

		// Iterate through the list of measures
		for (Measure measure : measureList) {

			if (this.x > 849) {
				this.x = 0;
				this.y += 100;
			}

			this.drawMeasure(measure);

			xCoordinates.put(measure, this.x);
			yCoordinates.put(measure, this.y - 30);
			// Draw bar line after every measure
			DrawDrumsetBar bar = new DrawDrumsetBar(this.pane);
			bar.draw(this.x, this.y);
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
				// ONLY REMOVE RECTANGLES IF THEY ARE RED,
				// OTHERWISE THE NOTE BEAMS WILL BE REMOVES BECAUSE THEY ARE ALSO RECTANGLES
				if (((Rectangle) object).getStyle().equals("-fx-stroke: red;")) {
					System.out.println("Rectangle Added to removal list.");
					removeRect.add((Rectangle) object);
				}
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
				Rectangle rectangle = new Rectangle(x, yf, w, 80);
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
		} else if(Id == "P1-I36"){
			fullName = "Bass_Drum";
		} else if(Id == "P1-I39"){
			fullName = "Acoustic_Snare";
		} else if(Id == "P1-I43"){
			fullName = "Closed_Hi_Hat";
		} else if(Id == "P1-I47"){
			fullName = "Open_Hi_Hat";
		} else if(Id == "P1-I52"){
			fullName = "Ride_Cymbal_1";
		} else if(Id == "P1-I54"){
			fullName = "Ride_Bell";
		} else if(Id == "P1-I53"){
			fullName = "Chinese_Cymbal_1";
		} else if(Id == "P1-I48"){
			fullName = "Lo_Mid_Tom";
		} else if(Id == "P1-I46"){
			fullName = "Lo_Tom";
		} else if(Id == "P1-I44"){
			fullName = "High_Floor_Tom";
		} else if(Id == "P1-I42"){
			fullName = "Low_Floor_Tom";
		} else if(Id == "P1-I45"){
			fullName = "Pedal_Hi_Hat";
		} else {
			fullName = "Bass_Drum"; // Make it default for now
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
		} else if (type.equals("32nd")) {
			res = "t";
		} else if (type.equals("64th")) {
			res = "x";
		} else if (type.equals("128th")) {
			res = "o";
		} else {
			res = "q"; // Make it default for now
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