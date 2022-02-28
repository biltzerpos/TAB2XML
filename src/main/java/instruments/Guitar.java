package instruments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import GUI.draw.Draw44Beat;
import GUI.draw.DrawBar;
import GUI.draw.DrawClef;
import GUI.draw.DrawMusicLines;
import GUI.draw.DrawNote;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;

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
			if (measure.getNumber() == 1) {
				checkDuration(measure);
			}

			if (x < 900) {
				drawMeasureNotes(measure);
			} else {
				x = 0;
				y += 100;
				drawMeasureNotes(measure);
			}
			xCoordinates.put(measure, x);
			yCoordinates.put(measure, y);
			DrawBar bar = new DrawBar(this.pane, x, y);
			bar.draw();
			System.out.println("Measure:" + measure + "X:" + x + "Y:" + y + pane);
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

		ObservableList children = pane.getChildren();
		ArrayList<Rectangle> removeRect = new ArrayList<Rectangle>();
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object instanceof Rectangle) {
				removeRect.add((Rectangle) object);
			}
		}

		for (Iterator iterator = removeRect.iterator(); iterator.hasNext();) {
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

	// returns a list of measures
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

	// if the notes in a measure are all 1/8( then beat is 4/4) draws 4/4 beat using
	// Draw44Beat
	private void checkDuration(Measure measure) {
		List<Note> noteList = measure.getNotesBeforeBackup();
		if (is44Beat(noteList)) {
			Draw44Beat dd = new Draw44Beat(this.pane, this.x, this.y + 20);
			dd.draw();
		}
		// We can add other beats here if necessary
	}

	// This method returns true if the all notes in a measure have 1/8 beat
	public boolean is44Beat(List<Note> noteList) {
		Boolean res = true;
		for (int i = 0; i < noteList.size(); i++) {
			Note n = noteList.get(i);
			int dur = n.getDuration();
			if (dur != 8) {
				res = false;
				break;
			}
		}
		return res;
	}

	// This method draws a given clef
	private void drawMeasureCleft(Clef c) {
		DrawClef dc = new DrawClef(this.pane, c, x + 5, y + 15);
		dc.draw();
	}

	// this method draws the notes in a measure given measure m
	private void drawMeasureNotes(Measure measure) {
		List<Note> noteList = measure.getNotesBeforeBackup();
		for (int j = 0; j < noteList.size(); j++) {
			Note note = noteList.get(j);
			// if a note has technical we use drawNote with techincal to draw fret values on
			// string
			if (noteHasTechnical(note)) {
				drawNoteWithTechnical(note);
			}
			// if the guitar class has anything else we can add else and else-if arguments
			// here
		}
	}

	// Given a Note with techinical attributes, this method draws it using drawNote
	// method of Gui.draw.
	private void drawNoteWithTechnical(Note note) {
		int fret = note.getNotations().getTechnical().getFret();
		int string = note.getNotations().getTechnical().getString();
		if (!noteHasChord(note)) {
			d.draw(x, y);
			double positionY = getLineCoordinateY(d, string);
			DrawNote noteDrawer = new DrawNote(this.pane, fret, x + 25, positionY + 3 + y);
			x += 50;
			noteDrawer.drawFret();
		} else {
			double positionY = getLineCoordinateY(d, string);
			DrawNote noteDrawer = new DrawNote(this.pane, fret, x - 25, positionY + 3 + y);
			noteDrawer.drawFret();
		}
	}

	// gets the Y coordinate of specific group of music lines based on given string
	// integer.
	private double getLineCoordinateY(DrawMusicLines d, int string) {
		return d.getMusicLineList().get(string - 1).getStartY(string - 1);

	}

	// This method plays the notes
	public void playGuitarNote() {
		Player player = new Player();
		// Pattern pattern = new Pattern();
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
				String dur = addDuration(note);
				voice = note.getVoice();

				if (!noteHasChord(note)) {
					ns = note.getPitch().getStep() + oct + dur;
					noteSteps += " " + ns;
				} else {

					noteSteps += "+" + note.getPitch().getStep() + oct + dur;

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

	private String addDuration(Note note) {
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
