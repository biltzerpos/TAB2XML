package instruments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.barline.BarLine;
import models.measure.note.Dot;
import models.measure.note.Note;
import GUI.BassHighlight;
import GUI.GuitarHighlight;
import GUI.draw.*;

public class Bass {

	private ScorePartwise scorePartwise;
	@FXML
	private Pane pane;
	private List<Measure> measureList;
	private double x;
	private double y;
	private DrawBassLines drawBassLines;
	private HashMap<Measure, Double> xCoordinates;
	private HashMap<Measure, Double> yCoordinates;
	private double spacing;
	private int LineSpacing;
	private int noteTypeCounter;
	private String repetitions;
	private DrawBassNote noteDrawer;
	
	private int fontSize;
	private int staffSpacing;
	private DrawSlur slurDrawer;

	public Bass() {
	}

	public Bass(ScorePartwise scorePartwise, Pane pane, int noteSpacing, int font, int staffSpacing,
			int LineSpacing) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.x = 0;
		this.y = 0;
		this.repetitions = null;
		this.LineSpacing = LineSpacing; //send it in as 150
		this.fontSize = font; //send it in as 12
		this.staffSpacing = staffSpacing; //send it as 50
		
		xCoordinates = new HashMap<>();
		yCoordinates = new HashMap<>();
		this.spacing = noteSpacing; //send it in as 50
		this.noteDrawer = new DrawBassNote();
		this.noteDrawer.setFont(this.fontSize);
		this.noteDrawer.setGraceFontSize(this.fontSize - 4);
		this.drawBassLines = new DrawBassLines(this.pane, noteSpacing, staffSpacing);
		this.slurDrawer = new DrawSlur();
		this.slurDrawer.setPane(this.pane);
		
	}

	/*
	 * This method is where the actual drawing of the guitar elements happen
	 */

	public void drawBass() {
		Clef clef = getClef();
		double width = this.pane.getMaxWidth();

		for (int i = 0; i < measureList.size(); i++) {
			int spaceRequired = 0;
			int repeatIndex = 0;
			Measure measure = measureList.get(i);
			//if barlines size = 2, meaning same measure has open and close repeat
			String repDirection = getRepeatDirection(measure, repeatIndex);

			// clef of first line
			if (x == 0) {
				drawBassLines.draw(x, y);
				DrawBassClef dc = new DrawBassClef(this.pane, clef, x + 5, y + 15);
				dc.draw();
				x += spacing;
				spaceRequired += getSpacing();
			}
			List<Note> noteList = measure.getNotesBeforeBackup();
			spaceRequired += countNoteSpacesRequired(noteList);
			if (width >= spaceRequired) {
				if (repDirection != null && repDirection.equals("forward")) {
					DrawBassRepeat br = new DrawBassRepeat(pane, x + 15, y);
					br.drawForward();
					repeatIndex++;
				}
				drawMeasureNotes(measure);
				width = width - spaceRequired;
			} else {

				this.x = 0.0;
				this.y += this.LineSpacing;
				width = this.pane.getMaxWidth();

				drawBassLines.draw(x, y);
				DrawBassClef dc = new DrawBassClef(this.pane, clef, x + 5, y + 15);
				dc.draw();
				x += spacing;
				spaceRequired += getSpacing();
				if (repDirection != null && repDirection.equals("forward")) {
					DrawBassRepeat br = new DrawBassRepeat(pane, x + 15, y);
					br.drawForward();
					repeatIndex++;
				}

				drawMeasureNotes(measure);
				width = width - spaceRequired;

			}

			xCoordinates.put(measure, x);
			yCoordinates.put(measure, y);
			DrawBassBar bar = new DrawBassBar(this.pane, x, y);
			bar.draw();
			repDirection = getRepeatDirection(measure, repeatIndex);
			if (repDirection != null && repDirection.equals("backward")) {
				DrawBassRepeat br = new DrawBassRepeat(pane, x, y);
				br.drawBackward();
				br.drawRepeatText(repetitions);
			}
			// System.out.println("Measure:" + measure + "X:" + x + "Y:" + y + pane);
		}
	}

	private String getRepeatDirection(Measure measure, int index) {
		List<BarLine> barLines = measure.getBarlines();
		int barlinesSize = 0;
		String direction = null;
		if(barLines != null) {
			barlinesSize = barLines.size();
			if(barlinesSize == 2) {
				direction = measure.getBarlines().get(index).getRepeat().getDirection();
				if(direction.equals("backward")) {
					repetitions = measure.getBarlines().get(index).getRepeat().getTimes();
				}
			} else {
				direction = measure.getBarlines().get(0).getRepeat().getDirection();
				if(direction.equals("backward")) {
					repetitions = measure.getBarlines().get(0).getRepeat().getTimes();
				}
			}
		}
		return direction;
	}

	// This method extracts a clef from a given measure
	public Clef getClef() {
		Clef clef = measureList.get(0).getAttributes().getClef();
		return clef;
	}

	// method to count the space required for notes in noteList
	private int countNoteSpacesRequired(List<Note> noteList) {
		int numOfSpace = 0;
		for (int i = 0; i < noteList.size(); i++) {
			Note n = noteList.get(i);
			if (!noteHasChord(n)) {
				numOfSpace++;
			}
		}
		int space = (int) (numOfSpace * getSpacing());

		return space;
	}

	// returns true if the guitar note has chord element
	public Boolean noteHasChord(Note n) {
		Boolean result = n.getChord() == null ? false : true;
		return result;
	}

	private void drawMeasureNotes(Measure measure) {
		this.noteTypeCounter = 3;
		List<Note> noteList = measure.getNotesBeforeBackup();
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (noteHasTechnical(note)) {
				drawNoteWithTechnical(note, noteList);
			}

		}
	}

	public Boolean noteHasTechnical(Note n) {
		Boolean result = n.getNotations().getTechnical() != null ? true : false;
		return result;
	}

	private void drawNoteWithTechnical(Note note, List<Note> noteList) {
		if (!noteHasChord(note)) {
			// Draw grace notes
			if (noteHasGrace(note)) {
				drawGraceNotes(note, noteList);
			} else {
				drawNoteWithoutGrace(note, noteList);

			}

		} else if (noteHasChord(note)) {
			if (noteHasGrace(note)) {
				drawChordsWithGraceNotes(note, noteList);
			} else {
				drawChordWithoutGrace(note, noteList);

			}
		}

	}

	// draws bend elements if they exists
	private void drawBend(Note note) {
		if (noteHasBend(note)) {
			double firstMusicLine = getFirstLineCoordinateY() + y;
			DrawBassBend db = new DrawBassBend(pane, note, x, y, firstMusicLine);
			db.draw();
		}
	}

	// gets the Y coordinate of specific group of music lines based on given string
	// integer.
	private double getLineCoordinateY(int string) {
		MLine mline = drawBassLines.getMusicLineList().get(string - 1);
		double startY = mline.getStartY(string - 1);
		return startY;
	}

	// returns the y coordinate the first music line of the 6-line group
	private double getFirstLineCoordinateY() {
		return this.drawBassLines.getMusicLineList().get(0).getStartY(1);
	}

	// returns true if note has a chord tag
	private boolean noteHasBend(Note note) {
		Boolean res = note.getNotations().getTechnical().getBend() == null ? false : true;
		return res;
	}

	// returns true if note has a grace tag
	private boolean noteHasGrace(Note note) {
		Boolean res = note.getGrace() == null ? false : true;
		return res;
	}

	// count the number of graces in a row
	private int countGraceSpace(Note n, List<Note> noteList) {
		int index = noteList.indexOf(n);
		int res = 0;
		for (int i = index; i < noteList.size() - 1; i++) {
			Note current = noteList.get(i);
			Note next = noteList.get(i + 1);
			if (noteHasGrace(current) && !noteHasGrace(next)) {
				res++;

			} else if (noteHasGrace(current) && noteHasGrace(next)) {
				if (!noteHasChord(next)) {
					res++;
				}
			} else {
				break;
			}
		}
		return res;
	}

	// Draws the grace notes
	private void drawGraceNotes(Note note, List<Note> noteList) {
		int string = note.getNotations().getTechnical().getString();
		int num = countGraceSpace(note, noteList);
		double xPosition = x + spacing / 2;
		int fret = note.getNotations().getTechnical().getFret();
		double graceSpacing = 0;
		if (fret < 10) {
			graceSpacing = xPosition - (spacing / (4 / num));
		} else {
			graceSpacing = xPosition - (spacing / (4 / num) + num);
		}
		double positionY = getLineCoordinateY(string);
		noteDrawer.setPane(pane);
		noteDrawer.setNote(note);
		noteDrawer.setStartX(graceSpacing);
		noteDrawer.setStartY(positionY + 3 + y);
		noteDrawer.drawGuitarGrace();
	}

	// draw regular notes (no grace, no chords)
	private void drawNoteWithoutGrace(Note note, List<Note> noteList) {
		int string = note.getNotations().getTechnical().getString();

		double positionY = getLineCoordinateY(string) + 3;// +getLineCoordinateY(string+1))/2;

		drawBassLines.draw(x, y);
		noteDrawer.setPane(pane);
		noteDrawer.setNote(note);
		noteDrawer.setStartX(x + spacing / 2);
		noteDrawer.setStartY(positionY + y);
		x += spacing;
		noteDrawer.drawFret();

		drawBend(note);
		drawType(note, noteList);

	}

	// Regular chords
	private void drawChordWithoutGrace(Note note, List<Note> noteList) {
		int string = note.getNotations().getTechnical().getString();
		double positionY = getLineCoordinateY(string) + y;
		noteDrawer.setPane(pane);
		noteDrawer.setNote(note);
		noteDrawer.setStartX(noteDrawer.getStartX());
		noteDrawer.setStartY(positionY + 3 + y);
		noteDrawer.drawFret();
		drawBend(note);
		double py = getLastLineCoordinateY();
		DrawBassNoteType type = new DrawBassNoteType(pane, note, noteDrawer.getStartX() + 7, py + y);
		type.drawType();
		drawType(note, noteList);
	}

	// draw grace notes that have chords
	private void drawChordsWithGraceNotes(Note note, List<Note> noteList) {
		// TODO Auto-generated method stub
		int string = note.getNotations().getTechnical().getString();
		double positionY = getLineCoordinateY(string);
		noteDrawer.setPane(pane);
		noteDrawer.setNote(note);
		noteDrawer.setStartX(noteDrawer.getStartX());
		noteDrawer.setStartY(positionY + 3 + y);
		noteDrawer.drawGuitarGrace();
		;
		drawBend(note);
		double py = getLastLineCoordinateY();
		DrawBassNoteType type = new DrawBassNoteType(pane, note, noteDrawer.getStartX() + 7, py + y);
		type.drawType();
	}

	private void drawType(Note note, List<Note> noteList) {
		String nextType = "";
		int index = noteList.indexOf(note);
		double py = getLastLineCoordinateY() + 35;
		DrawBassNoteType type = new DrawBassNoteType(pane, note, noteDrawer.getStartX() + 4, py + y);
		type.drawType();
		String current = note.getType();

		Note next = null;
		for (int j = index; j < noteList.size() - 1; j++) {
			next = noteList.get(j + 1);
			if (!noteHasGrace(next)) {
				nextType = next.getType();
				break;
			}

		}

		if (current.equals("eighth") && nextType.equals("eighth")) {
			if (this.noteTypeCounter > 0) {
				type.drawBeam(noteDrawer.getStartX() + 4, py + y, spacing);
				this.noteTypeCounter--;
			} else {
				this.noteTypeCounter = 3;
			}
		} else if (current == "16th") {
			switch (nextType) {
			case "16th":
				if (!noteHasChord(next)) {
					type.drawBeam(noteDrawer.getStartX() + 4, py + y, spacing);
					type.drawBeam(noteDrawer.getStartX() + 4, py + y - 5, spacing);
				}
				break;
			case "eighth":
				type.drawBeam(noteDrawer.getStartX() + 4, py + y, spacing);
				break;
			default:
				if (next != null)
					type.drawBeam(noteDrawer.getStartX() + 4, py + y, spacing / 4);
			}
		} else if (current == "quarter" && noteHasDot(note)) {
			int count = countDotNumber(note);
			double xCenter = noteDrawer.getStartX() + 10;
			double yCenter = py + y + 10;
			double radius = spacing / 25;
			while (count > 0) {
				type.drawDot(xCenter, yCenter, radius);
				xCenter += 2 * radius + radius;
				count--;
			}
		}
	}

	private int countDotNumber(Note note) {
		// TODO Auto-generated method stub
		int res = 0;
		List<Dot> dotList = note.getDots();
		for (int i = 0; i < dotList.size(); i++) {
			res++;
		}

		return res;
	}

	// returns true if note has a dot
	private boolean noteHasDot(Note note) {
		// TODO Auto-generated method stub
		Boolean res = note.getDots() == null ? false : true;
		return res;
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
				Rectangle rectangle = new Rectangle(x, yf, w, 35);
				rectangle.setFill(Color.TRANSPARENT);
				rectangle.setStyle("-fx-stroke: red;");
				pane.getChildren().add(rectangle);
				Object b4 = pane.getParent().getParent().getParent().getParent();
				if(b4 instanceof ScrollPane) {
					ScrollPane sp = (ScrollPane)b4;
					double rectBounds = rectangle.getBoundsInLocal().getMaxY();
					double thisBounds = pane.getBoundsInLocal().getMaxY();
					double val = rectBounds/thisBounds;
					sp.setVvalue(val);
				}
			}
			x = getXCoordinatesForGivenMeasure(measureList.get(i));
			y = yf;
		}
	}

	// return X coordinates for given measure
	public double getXCoordinatesForGivenMeasure(Measure measure) {
		return xCoordinates.get(measure);
	}

	// return Y coordinates for given measure
	public double getYCoordinatesForGivenMeasure(Measure measure) {
		return yCoordinates.get(measure);
	}

	private double getLastLineCoordinateY() {
		return this.drawBassLines.getMusicLineList().get(5).getStartY(6);

	}

	public Boolean noteHasTie(Note n) {
		Boolean result = n.getNotations().getTieds() == null ? false : true;
		return result;
	}

	public Boolean noteHasRest(Note n) {
		Boolean result = n.getRest() == null ? false : true;
		return result;
	}

	// This method plays the notes
	public void playBassNote() {
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
				ns = note.getPitch().getStep() + oct + dur;
				// System.out.println(" gra: " + gra + " dot: " + dot + " res: " + res + " alt:
				// " + alt);

				if (!noteHasChord(note) && !noteHasTie(note)) {
					noteSteps += " " + ns;
				} else if (noteHasChord(note)) {
					noteSteps += "+" + ns;
				} else if (noteHasTie(note)) {
					noteSteps += "-" + ns;
				} else if (noteHasRest(note)) {
					noteSteps += " " + note.getPitch().getStep() + "R" + oct + dur;
					;
				}
			}
		}

		vocals.add(noteSteps);
		// System.out.println(vocals.toString());
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
	
	public void highlightNote() {
		
		Note currentNote;
		double xPositionNote=0;
		double yPositionNote=0;
		x = 0;
		y = 0;
		ArrayList<Rectangle> r = new ArrayList<Rectangle>();
		ArrayList<Double> noteDuration = new ArrayList<Double>();
		
		for (int i = 0; i < measureList.size(); i++) {
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();
			double w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
			double yf = getYCoordinatesForGivenMeasure(measureList.get(i));
			if (yf > y) {
				x = 0;
				w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
				y = yf;
			}
			
			for (int j = 0; j < noteList.size(); j++) {
				currentNote = noteList.get(j);
				yPositionNote = getYCoordinatesForGivenMeasure(measureList.get(i));
				xPositionNote = getXCoordinatesForGivenMeasure(measureList.get(i));
				if (!noteHasChord(currentNote)) {
					if (noteHasGrace(currentNote)) {
						int num = countGraceSpace(currentNote, noteList);
						xPositionNote = noteDrawer.getStartX() + spacing / 2;
						int fret = currentNote.getNotations().getTechnical().getFret();
						double graceSpacing = 0;
						if (fret < 10) {
							graceSpacing = xPositionNote - (spacing / (4 / num));
						} else {
							graceSpacing = xPositionNote - (spacing / (4 / num) + num);
						}
						xPositionNote = graceSpacing;
					}
					else {
							xPositionNote = x+spacing/2;		
					}
				}
				else if (noteHasChord(currentNote)) {
					if (noteHasGrace(currentNote)) {
						xPositionNote = noteDrawer.getStartX();
					} else {
						xPositionNote = noteDrawer.getStartX();
					}
				}
				Rectangle rectangle = new Rectangle();
				rectangle.setWidth(15);
				rectangle.setHeight(70);
				
				//rectangle.setStyle("-fx-stroke: red;");
				rectangle.setTranslateX(xPositionNote-2);
				rectangle.setTranslateY(yPositionNote-15);
				rectangle.setFill(Color.TRANSPARENT);
				pane.getChildren().add(rectangle);
				r.add(rectangle);                
				this.x += currentNote.getChord() == null && currentNote.getGrace() == null ? this.spacing : 0;
				if (currentNote.getDuration() !=null) {
					int duration= currentNote.getDuration();
					double duration2 =1000.0/((double)duration);
					noteDuration.add(duration2);
				}

}}

BassHighlight note = new BassHighlight(this, r, noteDuration);
note.start();

ObservableList children = pane.getChildren();
	ArrayList<Rectangle> removeRect = new ArrayList<Rectangle>();
	for (Iterator iterator = children.iterator(); iterator.hasNext();) {
		Object object = (Object) iterator.next();
		if (object instanceof Rectangle) {
			if (((Rectangle) object).getStyle().equals("-fx-stroke: TRANSPARENT;")) {
				removeRect.add((Rectangle) object);
			}
		}
	}

	for (Iterator iterator = removeRect.iterator(); iterator.hasNext();) {
		Rectangle rect = (Rectangle) iterator.next();
		pane.getChildren().remove(rect);
	}

}

	public Boolean playing;
	
	public void starthighlight() {
		 this.playing = true;
	}
	public void stophighlight() {
		 this.playing=false;
	}
	// Getters and setters

	public ScorePartwise getScorePartwise() {
		return scorePartwise;
	}

	public void setScorePartwise(ScorePartwise scorePartwise) {
		this.scorePartwise = scorePartwise;
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public List<Measure> getMeasureList() {
		return measureList;
	}

	public void setMeasureList(List<Measure> measureList) {
		this.measureList = measureList;
	}

	public double getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	// End getters and setters
}