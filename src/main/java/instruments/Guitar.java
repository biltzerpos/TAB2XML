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
import models.measure.note.notations.Tied;
import GUI.draw.*;

public class Guitar {

	private ScorePartwise scorePartwise;
	@FXML
	private Pane pane;
	private List<Measure> measureList;
	private double x;
	private double y;
	private DrawMusicLines d;
	private HashMap<Measure, Double> xCoordinates;
	private HashMap<Measure, Double> yCoordinates;

	public Guitar() {
	}

	public Guitar(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.x = 0;
		this.y = 0;
		xCoordinates = new HashMap<>();
		yCoordinates = new HashMap<>();
		this.d = new DrawMusicLines(this.pane);
	}

	/*
	 * This method is where the actual drawing of the guitar elements happen
	 */

	public void drawGuitar() {
		for (int i = 0; i < measureList.size(); i++) {
			Measure measure = measureList.get(i);

			if (measure.getAttributes().getClef() != null) {
				d.draw(x, y);
				Clef c = extractClef(measure);
				drawMeasureCleft(c);
				x += 50;
			}

			drawMeasureNotes(measure);

			xCoordinates.put(measure, x);
			yCoordinates.put(measure, y);
			DrawBar bar = new DrawBar(this.pane, x, y);
			bar.draw();
			// System.out.println("Measure:" + measure + "X:" + x + "Y:" + y + pane);
		}
	}

	// This method extracts a clef from a given measure
	public Clef extractClef(Measure m) {
		Clef clef = m.getAttributes().getClef();
		return clef;
	}

	// returns true if the guitar note has chord element
	public Boolean noteHasChord(Note n) {
		Boolean result = n.getChord() == null ? false : true;
		return result;
	}

	// return true if guitar note has technical attribute
	public Boolean noteHasTechnical(Note n) {
		Boolean result = n.getNotations().getTechnical() != null ? true : false;
		return result;

	}

	public void highlightMeasureArea(Measure measure) {
		double x = 0;
		double y = 0;

		ObservableList<?> children = pane.getChildren();
		ArrayList<Rectangle> removeRect = new ArrayList<Rectangle>();
		for (Iterator<?> iterator = children.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object instanceof Rectangle) {
				removeRect.add((Rectangle) object);
			}
		}

		for (Iterator<Rectangle> iterator = removeRect.iterator(); iterator.hasNext();) {
			Rectangle rect = (Rectangle) iterator.next();
			pane.getChildren().remove(rect);
		}
		x = 0;
		y = 0;
		// now iterate again to highlight it in red
		for (int i = 0; i < measureList.size(); i++) {
			double w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
			double yf = getYCoordinatesForGivenMeasure(measureList.get(i));
			if (yf > y) {
				// we have moved on to new Line
				x = 0;
				w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
				y = yf;
			}
			if (measure.equals(measureList.get(i))) {
				Rectangle rectangle = new Rectangle(x, yf, w, 50);
				rectangle.setFill(Color.TRANSPARENT);
				rectangle.setStyle("-fx-stroke: red;");
				pane.getChildren().add(rectangle);
			}
			x = getXCoordinatesForGivenMeasure(measureList.get(i));
			y = yf;
		}
	}

	// getter: returns a list of measures
	public List<Measure> getMeasureList() {
		return measureList;
	}

	// return X coordinates for given measure
	public double getXCoordinatesForGivenMeasure(Measure measure) {
		return xCoordinates.get(measure);
	}

	// return Y coordinates for given measure
	public double getYCoordinatesForGivenMeasure(Measure measure) {
		return yCoordinates.get(measure);
	}

	// Sets measureList to a new measure
	public void setMeasureList(List<Measure> measureList) {
		this.measureList = measureList;
	}

	// This method draws a given clef
	private void drawMeasureCleft(Clef c) {
		DrawClef dc = new DrawClef(this.pane, c, x + 5, y + 15);
		dc.draw();
	}

	// this method draws the notes in a measure
	private void drawMeasureNotes(Measure measure) {
		// list of notes in each measure
		List<Note> noteList = measure.getNotesBeforeBackup();
		// the next 3 lines allow drawing lines for the note type
		double py = getLastLineCoordinateY();
		DrawNoteType noteType = new DrawNoteType(pane, noteList, x, py);
		noteType.drawDuration();
		// iterate through each note in the noteList
		for (int j = 0; j < noteList.size(); j++) {
			Note note = noteList.get(j);
			// if x-coordinate is in bound (less than 900), drawing happens in the same line
			// otherwise we go to the next line
			if (x < 900) {
				// if a note has technical we use drawNote with techincal to draw fret values on
				// string
				if (noteHasTechnical(note)) {
					drawNoteWithTechnical(note, noteList);
				}
				// if the guitar class has anything else we can add else and else-if arguments
				// here
			} else {
				x = 0;
				y += 150;
				// if a note has technical we use drawNote with techincal to draw fret values on
				// string
				if (noteHasTechnical(note)) {
					drawNoteWithTechnical(note, noteList);
				}
				// if the guitar class has anything else we can add else and else-if arguments
				// here
			}

		}
	}

	// Given a Note with techinical attributes, this method draws it using drawNote
	// method of Gui.draw.
	private void drawNoteWithTechnical(Note note, List<Note> noteList) {
		int string = note.getNotations().getTechnical().getString();
		// if the note belongs to a chord then they are drawn on the same line
		if (!noteHasChord(note)) {
			d.draw(x, y);
			double positionY = getLineCoordinateY(string);
			DrawNote noteDrawer = new DrawNote(this.pane, note, x + 25, positionY + 3 + y);
			x += 50;
			noteDrawer.drawFret();

		} else {
			double positionY = getLineCoordinateY(string);
			DrawNote noteDrawer = new DrawNote(this.pane, note, x - 25, positionY + 3 + y);
			noteDrawer.drawFret();
		}
	}

	// gets the Y coordinate of specific group of music lines based on given string
	// integer.
	private double getLineCoordinateY(int string) {
		return this.d.getMusicLineList().get(string - 1).getStartY(string - 1);

	}

	private double getLastLineCoordinateY() {
		return this.d.getMusicLineList().get(5).getStartY(6);

	}
	public Boolean noteHasTie(Note n) {
		Boolean result = n.getNotations().getTieds() == null ? false : true;
		return result;
	}

	// This method plays the notes
	public void playGuitarNote() {
		Player player = new Player();
		Pattern vocals = new Pattern();
		String noteSteps = "";
		int voice = 0;

		for (int i = 0; i < measureList.size(); i++) {
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();

			for (int j = 0; j < noteList.size(); j++) {
				String ns = new String();
				Note note = noteList.get(j);
				int octave = note.getPitch().getOctave();
				String oct = Integer.toString(octave);
				String dur = getDuration(note);
				voice = note.getVoice();

				if (!noteHasChord(note) && !noteHasTie(note)) {
					ns = note.getPitch().getStep() + oct + dur;
					noteSteps += " " + ns;
				} else if(noteHasChord(note)){
					noteSteps += "+" + note.getPitch().getStep() + oct + dur;
				} else if(noteHasTie(note)){
					noteSteps += "- " + note.getPitch().getStep() + oct + dur;
				}
			}
		}

		vocals.add(noteSteps);
		System.out.println(vocals.toString());
		vocals.setInstrument("GUITAR");
		vocals.setVoice(voice);
		vocals.setTempo(120);
		player.play(vocals);

	}

	// returns string representation of a duration for a given note
	private String getDuration(Note note) {
		String res = "";
		int duration = note.getDuration();
		if (duration == 8) {
			res = "i";
		}
		if (duration == 64) {
			res = "w";
		}
		return res;
	}

}