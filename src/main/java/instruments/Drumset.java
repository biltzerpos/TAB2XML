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
	@FXML
	private Pane pane;
	private List<Measure> measureList;
	private Clef clef;
	private HashMap<Measure, Double> xCoordinates;
	private HashMap<Measure, Double> yCoordinates;

	private double x;
	private double y;

	private double spacing;

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

		this.spacing = 40;
	}

	/**
	 * Draw a group of notes that should be beamed together. This will handle
	 * chords, beaming, and positioning of notes. This also draws the music lines.
	 *
	 * @param notes - List of notes that should be beamed together.
	 */
	private void drawGroupedNotes(List<Note> notes) {
		Note currentNote, nextNote;
		DrawDrumsetNote noteDrawer;
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);

		double yPositionMeasure, xPositionNote, yPositionNote;

		// Draw initial music lines
		d.draw(this.x, this.y);

		for (int i = 0; i < notes.size(); i++) {
			// Get current note
			currentNote = notes.get(i);

			// Get the next not that is not a chord.
			nextNote = null;
			for (int j = i + 1; j < notes.size() && nextNote == null; j++) {
				// Set the next note only if it is not a chord
				if (notes.get(j).getChord() == null) {
					nextNote = notes.get(j);
				}
			}

			// Draw music lines only if the current note is not a chord
			// (if the current note is a chord, then the music lines will have
			// already been drawn by the previous note).
			if (currentNote.getChord() == null) {
				d.draw(this.x, this.y);
			}

			// y-position of the top of the measure
			yPositionMeasure = this.y;

			// x-position of the note
			// If it is a chord, we subtract 25 to align it with the previous note,
			// otherwise, we add 25 to draw it on its own column.
			xPositionNote = currentNote.getChord() == null ? this.x + this.spacing / 2 : this.x - this.spacing / 2;

			// y-position of note
			// Get the y-position of the note based on its octave and step
			// and the position of the music lines it will be drawn on.
			yPositionNote = d.getYPositionFromOctaveAndStep(currentNote.getUnpitched().getDisplayOctave(),
					currentNote.getUnpitched().getDisplayStep());

			// Set note drawer for the current note
			noteDrawer = new DrawDrumsetNote(this.pane, currentNote, yPositionMeasure, xPositionNote, yPositionNote);

			if (currentNote.getChord() != null || currentNote.getGrace() != null) {
				// If the note is a chord or grace note, then the beam will already have been
				// drawn, so just draw the note
				noteDrawer.draw();
			} else if (currentNote.getType().equals("eighth") && nextNote != null) {
				// If the current note is an eighth note and the next note is not null,
				// draw a beam for the next note to be connected to.
				// The beam must be a single beam because it is an eighth note.
				// (Note that the next note being null also can mean that the rest of the notes
				// in the group are chords.)
				noteDrawer.draw();
				noteDrawer.drawSingleBeam();
			} else if (currentNote.getType().equals("16th") && nextNote != null) {
				// If the current note is a 16th note and the next note is not null and is a
				// 16th note,
				// draw a beam for the next note to be connected to.
				// (Note that the next note being null also can mean that the rest of the notes
				// in the group are chords.)
				noteDrawer.draw();

				if (nextNote.getType().equals("eighth")) {
					// Draw a single beam because the next note is an eighth note
					noteDrawer.drawSingleBeam();
				} else if (nextNote.getType().equals("16th")) {
					// Draw a double beam because the current note and next note are both 16th notes
					noteDrawer.drawDoubleBeam();
				}
			} else {
				// This probably means this is the last note in the group, just draw the note
				// (if it is beamed, then the beam will already have been drawn).
				noteDrawer.draw();
			}

			// If the current note is not a chord, increment x position
			this.x += currentNote.getChord() == null && currentNote.getGrace() == null ? this.spacing : 0;
		}
	}

	/**
	 * Draw a group of notes that should NOT be beamed together (draw flags
	 * instead). This will handle chords, flags, and positioning of notes. This also
	 * draws the music lines.
	 *
	 * @param notes - List of notes (that will not be beamed together).
	 */
	private void drawUngroupedNotes(List<Note> notes) {
		Note currentNote;
		DrawDrumsetNote noteDrawer;
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);

		double yPositionMeasure, xPositionNote, yPositionNote;

		// Draw initial music lines
		d.draw(this.x, this.y);

		for (int i = 0; i < notes.size(); i++) {
			currentNote = notes.get(i);

			// Draw music lines only if the current note is not a chord
			// (if the current note is a chord, then the music lines will have
			// already been drawn by the previous note).
			if (currentNote.getChord() == null) {
				d.draw(this.x, this.y);
			}

			// y-position of the top of the measure
			yPositionMeasure = this.y;

			// x-position of the note
			// If it is a chord, we subtract 25 to align it with the previous note,
			// otherwise, we add 25 to draw it on its own column.
			xPositionNote = currentNote.getChord() == null ? this.x + this.spacing / 2 : this.x - this.spacing / 2;

			// y-position of note
			// Get the y-position of the note based on its octave and step
			// and the position of the music lines it will be drawn on.
			yPositionNote = d.getYPositionFromOctaveAndStep(currentNote.getUnpitched().getDisplayOctave(),
					currentNote.getUnpitched().getDisplayStep());

			// Set note drawer for the current note
			noteDrawer = new DrawDrumsetNote(this.pane, currentNote, yPositionMeasure, xPositionNote, yPositionNote);

			// Draw the note
			noteDrawer.draw();

			// Draw the flag only if the current note is not a chord
			// (if the current note is a chord, then the flag will have already
			// been drawn by the previous note).
			if (currentNote.getChord() != null) {
				noteDrawer.drawFlag();
			}

			// If the current note is a chord, increment x position
			this.x += currentNote.getChord() == null ? this.spacing : 0;
		}
	}

	/**
	 * Draws a measure of notes.
	 *
	 * This separates the measure into groups of notes, decides for each group of
	 * notes whether to beam them together or not, and draws each group.
	 *
	 * @param measure - The measure to draw.
	 */
	private void drawMeasure(Measure measure) {
		// The divisions of a measure is the duration of a quarter note
		int divisions = measure.getAttributes().getDivisions();

		// Get the notes in the measure
		List<Note> noteList = measure.getNotesBeforeBackup();

		int i = 0;
		Note currentNote;

		// The current group of notes
		List<Note> group = new ArrayList<>();
		// The sum of the duration of the current group of notes
		int durationSum = 0;

		while (i < noteList.size()) {
			// Add the first note of the measure to the group
			currentNote = noteList.get(i);
			group.add(currentNote);
			durationSum += currentNote.getDuration();
			i++;

			// When durationSum % divisions == 0, that means we stopped at a beat (quarter
			// note).
			// Otherwise, keep iterating until we find a beat.
			while (durationSum != 0 && durationSum % divisions != 0) {
				currentNote = noteList.get(i);
				group.add(currentNote);
				// Only add to the duration sum if it not a chord
				// (because they do not increase the duration of the group since they are played
				// as a chord).
				durationSum += currentNote.getChord() == null && currentNote.getGrace() == null
						? currentNote.getDuration()
						: 0;
				i++;
			}

			// If the last note in the group is followed by chord notes, they will not be
			// found by the above loop.
			// This loop makes sure they are added to the group.
			while (i < noteList.size() && noteList.get(i).getChord() != null) {
				group.add(noteList.get(i));
				i++;
			}

			// If durationSum == divisions, then the group is one beat (quarter note) (so
			// draw the group together).
			// Else, the group extends over more than one beat (so draw the group as
			// individual notes because it may be too complex to draw).
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

	/**
	 * Draw the sheet music for drums.
	 */
	public void draw() {

		// Initialize x and y coordinates
		this.x = 0;
		this.y = 0;

		double width = this.pane.getMaxWidth();

		// Draw the initial music lines
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);
		d.draw(this.x, this.y);

		DrawClef drumclef = new DrawClef(this.pane, clef, x + 25, 0);
		drumclef.drawDrumClef1();
		drumclef.drawDrumClef2();

		// Iterate through the list of measures
		for (Measure measure : measureList) {
			// If the current line is getting too long, then make a new line
			// and draw a drum clef for the new line.
			if (this.x + this.getMeasureLength(measure) > width) {
				this.x = 0;
				this.y += 100;

				drumclef = new DrawClef(this.pane, clef, x + 25, this.y);
				drumclef.drawDrumClef1();
				drumclef.drawDrumClef2();
			}

			// Draw the current measure
			this.drawMeasure(measure);

			xCoordinates.put(measure, this.x);
			yCoordinates.put(measure, this.y - 30);

			// Draw bar line after every measure
			DrawDrumsetBar bar = new DrawDrumsetBar(this.pane);
			bar.draw(this.x, this.y);
		}
	}

	/**
	 * Calculates the length of the measure based on the spacing and the
	 * number of notes that are not chords or grace notes.
	 *
	 * @return The length of the measure in pixels.
	 */
	private double getMeasureLength(Measure measure) {
		List<Note> notes = measure.getNotesBeforeBackup();
		int length = 0;

		for (Note note : notes) {
			if (note.getChord() == null && note.getGrace() == null) {
				length += this.spacing;
			}
		}

		return length;
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

		if (Id == "P1-I50") {
			fullName = "Crash_Cymbal_1";
		} else if (Id == "P1-I36") {
			fullName = "Bass_Drum";
		} else if (Id == "P1-I39") {
			fullName = "Acoustic_Snare";
		} else if (Id == "P1-I43") {
			fullName = "Closed_Hi_Hat";
		} else if (Id == "P1-I47") {
			fullName = "Open_Hi_Hat";
		} else if (Id == "P1-I52") {
			fullName = "Ride_Cymbal_1";
		} else if (Id == "P1-I54") {
			fullName = "Ride_Bell";
		} else if (Id == "P1-I53") {
			fullName = "Chinese_Cymbal_1";
		} else if (Id == "P1-I48") {
			fullName = "Lo_Mid_Tom";
		} else if (Id == "P1-I46") {
			fullName = "Lo_Tom";
		} else if (Id == "P1-I44") {
			fullName = "High_Floor_Tom";
		} else if (Id == "P1-I42") {
			fullName = "Low_Floor_Tom";
		} else if (Id == "P1-I45") {
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
				} else {
					drumNote += "+" + ns + dur;
				}
			}
		}

		vocals.add(drumNote);
		// System.out.println(vocals.toString());
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