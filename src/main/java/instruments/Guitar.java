package instruments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Dot;
import models.measure.note.Note;
import models.measure.note.notations.Slur;
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
	private double spacing;
	private int LineSpacing;
	private int noteTypeCounter;
	private DrawNote noteDrawer;
	private int fontSize;
	private int staffSpacing;
	private DrawSlur slurDrawer; 

	public Guitar() {
	}

	public Guitar(ScorePartwise scorePartwise, Pane pane, int noteSpacing, int font, int StaffSpacing, int LineSpacing) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.x = 0;
		this.y = 0;
		this.LineSpacing = LineSpacing;
		this.fontSize = font; 
		this.staffSpacing = StaffSpacing;
		xCoordinates = new HashMap<>();
		yCoordinates = new HashMap<>();
		this.spacing = noteSpacing;
		this.noteDrawer = new DrawNote();
		this.noteDrawer.setFont(this.fontSize); 
		this.noteDrawer.setGraceFontSize(this.fontSize-4);
		this.d = new DrawMusicLines(this.pane, noteSpacing, staffSpacing);
		this.slurDrawer = new DrawSlur();
		this.slurDrawer.setPane(this.pane);
	}

	/*
	 * This method is where the actual drawing of the guitar elements happen
	 */

	public void drawGuitar() {
		Clef clef = getClef();
		double width = this.pane.getMaxWidth();

		for (int i = 0; i < measureList.size(); i++) {
			int spaceRequired = 0;
			Measure measure = measureList.get(i);

			// clef of first line
			if (x == 0) {
				d.draw(x, y);
				double clefSpacing = this.staffSpacing + (this.staffSpacing/2); 
				DrawClef dc = new DrawClef(this.pane, clef, x , y + clefSpacing);
				dc.setFontSize(this.fontSize+6);
				dc.draw(clefSpacing);
				x += spacing;
				spaceRequired += getSpacing();
			}
			List<Note> noteList = measure.getNotesBeforeBackup();
			spaceRequired += countNoteSpacesRequired(noteList);
			if (width >= spaceRequired) {
				drawMeasureNotes(measure);
				width = width - spaceRequired;

			} else {
				while (width > 0) {
					d.draw(x, y);
					width -= spacing;
					x += spacing;
				}

				this.x = 0.0;
				this.y += this.LineSpacing;
				width = this.pane.getMaxWidth();

				d.draw(x, y);
				double clefSpacing = this.staffSpacing + (this.staffSpacing/2); 
				DrawClef dc = new DrawClef(this.pane, clef, x , y + clefSpacing);
				dc.setFontSize(this.fontSize+6);
				dc.draw(clefSpacing);
				x += spacing;
				spaceRequired += getSpacing();

				drawMeasureNotes(measure);
				width = width - spaceRequired;

			}

			xCoordinates.put(measure, x);
			yCoordinates.put(measure, y);
			double len = getLastLineCoordinateY() - getFirstLineCoordinateY(); 
			DrawBar bar = new DrawBar(this.pane, x, y, len);
			bar.draw();
			// System.out.println("Measure:" + measure + "X:" + x + "Y:" + y + pane);
		}

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
			//drawSlur(note, noteList);

		} else if (noteHasChord(note)) {
			if (noteHasGrace(note)) {
				drawChordsWithGraceNotes(note, noteList);
			} else {
				drawChordWithoutGrace(note, noteList);

			}
			//drawSlur(note, noteList);
		}
		drawSlur(note, noteList);
	}

	// draws bend elements if they exists
	private void drawBend(Note note) {
		if (noteHasBend(note)) {
			double firstMusicLine = getFirstLineCoordinateY() + y;
			DrawBend db = new DrawBend(pane, note, x, y, firstMusicLine);
			db.draw();
		}
	}

	// gets the Y coordinate of specific group of music lines based on given string
	// integer.
	private double getLineCoordinateY(int string) {
		return this.d.getMusicLineList().get(string - 1).getStartY(string - 1);
	}

	// returns the y coordinate the first music line of the 6-line group
	private double getFirstLineCoordinateY() {
		return this.d.getMusicLineList().get(0).getStartY(1);
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
		//drawSlur(note, noteList);
	}

	// draw regular notes (no grace, no chords)
	private void drawNoteWithoutGrace(Note note, List<Note> noteList) {
		int string = note.getNotations().getTechnical().getString();

		double positionY = getLineCoordinateY(string) + 3;// +getLineCoordinateY(string+1))/2;

		d.draw(x, y);
		noteDrawer.setPane(pane);
		noteDrawer.setNote(note);
		noteDrawer.setStartX(x + spacing / 2);
		noteDrawer.setStartY(positionY + y);
		x += spacing;
		noteDrawer.drawFret();

		drawBend(note);
		drawType(note, noteList);
		//drawSlur(note, noteList);

	}

	// Regular chords
	private void drawChordWithoutGrace(Note note, List<Note> noteList) {
		int string = note.getNotations().getTechnical().getString();
		double positionY = getLineCoordinateY(string);
		noteDrawer.setPane(pane);
		noteDrawer.setNote(note);
		noteDrawer.setStartX(noteDrawer.getStartX());
		noteDrawer.setStartY(positionY + 3 + y);
		noteDrawer.drawFret();
		drawBend(note);
		/*
		 * double py = getLastLineCoordinateY(); DrawNoteType type = new
		 * DrawNoteType(pane, note, noteDrawer.getStartX() + 7, py + y);
		 * type.drawType(); drawType(note, noteList);
		 */
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
		drawBend(note);
		//drawSlur(note, noteList);
		/*
		 * double py = getLastLineCoordinateY(); DrawNoteType type = new
		 * DrawNoteType(pane, note, noteDrawer.getStartX() + 7, py + y);
		 * type.drawType();
		 */
	}

	private void drawType(Note note, List<Note> noteList) {
		String current = note.getType();
		String lastType = "";
		int index = noteList.indexOf(note);
		// int actualCurrent = note.getTimeModification().getActualNotes();
		int actualLast = 0;
		for (int i = index; i > 0; i--) {
			Note last = noteList.get(i - 1);
			if (last != null && !noteHasChord(last) && !noteHasGrace(last)) {
				lastType = last.getType();
				if (noteHasActual(last)) {
					actualLast = last.getTimeModification().getActualNotes();
				}
				break;
			}
		}
		double py = getLastLineCoordinateY();
		double shortStick = 15;
		DrawNoteType type = new DrawNoteType(pane, noteDrawer.getStartX() + 4, py + y, shortStick);

		switch (current) {
		case "half":
			type.drawShortLine();
			drawDot(note, type, py+shortStick);
			break;
		case "quarter":
			type.drawLongLine();
			drawDot(note, type, py);
			break;
		case "eighth":
			type.drawLongLine();
			if (lastType == "eighth" && actualLast <= 0) {
				if (this.noteTypeCounter > 0) {
					type.drawBeam(-spacing);
					this.noteTypeCounter--;
				} else {
					this.noteTypeCounter = 3;
				}
			} else if (lastType == "16th") {
				type.drawBeam(-spacing);
			} else {
				type.drawBeam(spacing / 4);
			}
			break;
		case "16th":
			type.drawLongLine();
			if (lastType == "16th") {
				type.drawBeam(-spacing);
				type.setStartY(py + y - 5);
				type.drawBeam(-spacing);
			} else if (lastType == "eighth") {
				if (this.noteTypeCounter > 0) {
					type.drawBeam(-spacing);
					this.noteTypeCounter--;
				} else {
					this.noteTypeCounter = 3;
				}
			} else {
				type.drawBeam(spacing / 4);
				type.setStartY(py + y - 5);
				type.drawBeam(spacing / 4);
			}

			break;
		}

		if (noteHasActual(note)) {
			int currentActual = note.getTimeModification().getActualNotes();
			if (currentActual == actualLast) {
				// type.setStartX(noteDrawer.getStartX());
				type.drawActual(actualLast, spacing, fontSize);
			}
		}

	}

	private void drawDot(Note note, DrawNoteType type, double py) {
		// TODO Auto-generated method stub
		if (noteHasDot(note)) {
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

	private boolean noteHasActual(Note note) {
		Boolean res = note.getTimeModification() == null ? false : true;
		return res;
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
	
	private void drawSlur(Note note, List<Note> noteList) {
		if(noteHasSlur(note)) {
			int string = note.getNotations().getTechnical().getString();
			double positionY = getLineCoordinateY(string);
			List<Slur> slurList = note.getNotations().getSlurs();
			int lastNum = 0; 
			int index = noteList.indexOf(note);
			for (int i = index; i>0; i--) {
				Note last = noteList.get(i); 
				if(noteHasSlur(last)) {
					List<Slur> lastSlurList = last.getNotations().getSlurs();
					if(lastSlurList.size() == 1) {
						Slur s = lastSlurList.get(0); 
						lastNum = s.getNumber(); 
						break; 
					}
				}
			}
			if(slurList.size() == 1) {
				Slur s = slurList.get(0);
				String currentType = s.getType();
				int currentNum = s.getNumber(); 
				if(currentType == "start") {
					slurDrawer.setStartX(noteDrawer.getStartX());
					String place = s.getPlacement(); 
					if(place == "below") {
						slurDrawer.setStartY(positionY + 10);
						slurDrawer.setPlace(1);
					}
					else {
						slurDrawer.setStartY(positionY - 10);
						slurDrawer.setPlace(-1);
					}
				}
				if(currentType == "stop") {
					slurDrawer.setEndX(noteDrawer.getStartX());
					if(lastNum == currentNum) {
					slurDrawer.draw();}
				}
			}
		}
		
	}


	private boolean noteHasSlur(Note note) {
		Boolean res = note.getNotations().getSlurs() == null ? false: true;
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
				Rectangle rectangle = new Rectangle(x, yf, w, 50);
				rectangle.setFill(Color.TRANSPARENT);
				rectangle.setStyle("-fx-stroke: red;");
				pane.getChildren().add(rectangle);
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
		return this.d.getMusicLineList().get(5).getStartY(6);

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

	public int getLineSpacing() {
		return LineSpacing;
	}

	public void setLineSpacing(int lineSpacing) {
		LineSpacing = lineSpacing;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getStaffSpacing() {
		return staffSpacing;
	}

	public void setStaffSpacing(int staffSpacing) {
		this.staffSpacing = staffSpacing;
	}

	public void setSpacing(double spacing) {
		this.spacing = spacing;
	}

	// End getters and setters
}