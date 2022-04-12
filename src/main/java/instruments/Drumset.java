package instruments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;
import GUI.HighlightNote;
import GUI.draw.DrawClef;
import GUI.draw.DrawDrumsetBar;
import GUI.draw.DrawDrumsetMusicLines;
import GUI.draw.DrawDrumsetNote;

public class Drumset {
	private double tempo;
	private ScorePartwise scorePartwise;
	@FXML
	private Pane pane;
	private List<Measure> measureList;
	private Clef clef;
	private HashMap<Measure, Double> xCoordinates;
	private HashMap<Measure, Double> yCoordinates;

	private List<Rectangle> hightlightRectanlges;
	private List<Double> noteDurations;

	private double x;
	private double y;

	private List<Double[]> drumTieCoords;
	private List<Double[]> cymbalTieCoords;

	private List<Double[]> drumSlurCoords;
	private List<Double[]> cymbalSlurCoords;

	private double spacing;
	private double minimumSpacing;

	private double fontSize;
	private double staffSpacing;
	private double musicLineSpacing;

	/**
	 * Constructor for the Drumset class.
	 *
	 * @param scorePartwise   - The scorePartwise object to be drawn
	 * @param pane            - The pane to be draw to
	 * @param minimumSpacing  - The minimum spacing between notes
	 * @param fontSize        - The size of the notes
	 * @param musicLineSpaing - The space between music lines in a staff
	 * @param staffSpacing    - The space between staffs
	 */
	public Drumset(ScorePartwise scorePartwise, Pane pane, double minimumSpacing, double fontSize, double musicLineSpacing, double staffSpacing, double tempo) {
		super();

		this.scorePartwise = scorePartwise;
		
		this.pane = pane;

		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();

		this.clef = this.scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();

		this.xCoordinates = new HashMap<>();
		this.yCoordinates = new HashMap<>();

		this.hightlightRectanlges = new ArrayList<Rectangle>();
		this.noteDurations = new ArrayList<Double>();

		this.x = 0;
		this.y = 0;

		this.minimumSpacing = minimumSpacing + musicLineSpacing;
		this.spacing = this.minimumSpacing;

		this.fontSize = fontSize / 10 + (fontSize - 10) / 10;

		this.musicLineSpacing = musicLineSpacing - 10;

		this.staffSpacing = ((this.fontSize >= 1) ? 100 * this.fontSize : 75 + this.fontSize * 10)
			+ (musicLineSpacing - 10) * 5 + staffSpacing - 100;

		this.drumTieCoords = new ArrayList<Double[]>();
		this.cymbalTieCoords = new ArrayList<Double[]>();

		this.drumSlurCoords = new ArrayList<Double[]>();
		this.cymbalSlurCoords = new ArrayList<Double[]>();
		this.tempo=tempo;
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
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane, this.spacing, 5 * this.fontSize + this.musicLineSpacing);

		Rectangle currentHighlightRectangle;

		double yPositionMeasure, xPositionNote, yPositionNote;

		// Draw initial music lines
		d.drawInitial(this.x, this.y);

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

			if (currentNote.getRest() == null) {
				// y-position of note
				// Get the y-position of the note based on its octave and step
				// and the position of the music lines it will be drawn on.
				yPositionNote = d.getYPositionFromOctaveAndStep(currentNote.getUnpitched().getDisplayOctave(),
						currentNote.getUnpitched().getDisplayStep());
	
				// Set note drawer for the current note
				noteDrawer = new DrawDrumsetNote(this.pane, currentNote, yPositionMeasure, this.spacing, this.fontSize, xPositionNote, yPositionNote);
	
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

					if (nextNote.getGrace() != null && i + 2 < notes.size()) {
						nextNote = notes.get(i + 2);
					}
	
					if (nextNote.getType().equals("eighth")) {
						// Draw a single beam because the next note is an eighth note
						noteDrawer.drawSingleBeam();
					} else if (nextNote.getType().equals("16th")) {
						// Draw a double beam because the current note and next note are both 16th notes
						noteDrawer.drawDoubleBeam();
					}
				} else if (currentNote.getType().equals("32nd") && nextNote != null) {
					noteDrawer.draw();

					if (nextNote.getGrace() != null && i + 2 < notes.size()) {
						nextNote = notes.get(i + 2);
					}
	
					if (nextNote.getType().equals("eighth")) {
						// Draw a single beam because the next note is an eighth note
						noteDrawer.drawSingleBeam();
					} else if (nextNote.getType().equals("16th")) {
						// Draw a double beam because the current note is a 32nd note and the next note is a 16th note
						noteDrawer.drawDoubleBeam();
					} else if (nextNote.getType().equals("32nd")) {
						// Draw a triple beam because the current note and next note are both 32nd notes
						noteDrawer.drawTripleBeam();
					}
				} else {
					// This probably means this is the last note in the group, just draw the note
					// (if it is beamed, then the beam will already have been drawn).
					noteDrawer.draw();
				}

				this.handleTieOrSlur(currentNote, noteDrawer, xPositionNote, yPositionNote);
			} else {
				yPositionNote = d.getYPositionFromOctaveAndStep(4, "B");
				noteDrawer = new DrawDrumsetNote(this.pane, currentNote, yPositionMeasure, this.spacing, this.fontSize, xPositionNote, yPositionNote);
				noteDrawer.draw();
			}

			// Adding the current note to the highlight rectangle list and duration list.
			if (currentNote != null && currentNote.getDuration() != null) {
				// Make the rectangle to be hightlighted
				currentHighlightRectangle = new Rectangle(this.x, this.y, this.spacing, 5 * (10 * this.fontSize + 2 * this.musicLineSpacing));
				currentHighlightRectangle.setFill(Color.TRANSPARENT);

				// Add the rectangle to the pane
				pane.getChildren().add(currentHighlightRectangle);

				// Add the rectangle and duration to the rectangle and duration lists
				this.hightlightRectanlges.add(currentHighlightRectangle);
				double duration = 1000.0/currentNote.getDuration();
			
				this.noteDurations.add(60000.0/ (currentNote.getDuration()*tempo));
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
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane, this.spacing, 5 * this.fontSize + this.musicLineSpacing);

		Rectangle currentHighlightRectangle;

		double yPositionMeasure, xPositionNote, yPositionNote;

		// Draw initial music lines
		d.drawInitial(this.x, this.y);

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

			if (currentNote.getRest() != null) {
				yPositionNote = d.getYPositionFromOctaveAndStep(4, "B");
				noteDrawer = new DrawDrumsetNote(this.pane, currentNote, yPositionMeasure, this.spacing, this.fontSize, xPositionNote, yPositionNote);
				noteDrawer.draw();
			} else {
				// y-position of note
				// Get the y-position of the note based on its octave and step
				// and the position of the music lines it will be drawn on.
				yPositionNote = d.getYPositionFromOctaveAndStep(currentNote.getUnpitched().getDisplayOctave(),
						currentNote.getUnpitched().getDisplayStep());
	
				// Set note drawer for the current note
				noteDrawer = new DrawDrumsetNote(this.pane, currentNote, yPositionMeasure, this.spacing, this.fontSize, xPositionNote, yPositionNote);
	
				// Draw the note
				noteDrawer.draw();
	
				// Draw the flag only if the current note is not a chord
				// (if the current note is a chord, then the flag will have already
				// been drawn by the previous note).
				if (currentNote.getChord() == null) {
					noteDrawer.drawFlag();
				}

				this.handleTieOrSlur(currentNote, noteDrawer, xPositionNote, yPositionNote);
			}

			// Adding the current note to the highlight rectangle list and duration list.
			if (currentNote != null && currentNote.getDuration() != null) {
				// Make the rectangle to be hightlighted
				currentHighlightRectangle = new Rectangle(this.x, this.y, this.spacing, 5 * (10 * this.fontSize + 2 * this.musicLineSpacing));
				currentHighlightRectangle.setFill(Color.TRANSPARENT);

				// Add the rectangle to the pane
				pane.getChildren().add(currentHighlightRectangle);

				// Add the rectangle and duration to the rectangle and duration lists
				this.hightlightRectanlges.add(currentHighlightRectangle);
				int duration= currentNote.getDuration();
				double duration2 =1000.0/((double)duration);
				double duration3= duration2*60/tempo;
				this.noteDurations.add((double) duration3);
				//System.out.println((double) duration3);
			}

			// If the current note is a chord, increment x position
			this.x += currentNote.getChord() == null && currentNote.getGrace() == null ? this.spacing : 0;
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

		if (noteList == null) {
			return;
		}

		while (i < noteList.size()) {
			// Add the first note of the measure to the group
			currentNote = noteList.get(i);
			group.add(currentNote);
			i++;

			if (currentNote.getGrace() != null) {
				currentNote = noteList.get(i);
				group.add(currentNote);
				i++;
			}

			durationSum += currentNote.getDuration();

			// When durationSum % divisions == 0, that means we stopped at a beat (quarter
			// note).
			// Otherwise, keep iterating until we find a beat.
			while (durationSum != 0 && durationSum % divisions != 0 && i < noteList.size()) {
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

		// Add to lists for go-to measure
		xCoordinates.put(measure, this.x);
		yCoordinates.put(measure, this.y - 30);

		// Draw bar line after every measure
		DrawDrumsetBar bar = new DrawDrumsetBar(this.pane, 10 * this.fontSize + 2 * this.musicLineSpacing);
		bar.draw(this.x, this.y);
	}

	/**
	 * Draws a staff (which consists of a group of measure(s)).
	 * This should take up one line in the sheet music.
	 *
	 * @param staff    - The list of measures to be drawn
	 * @param d        - DrawDrumsetMusicLines drawer
	 * @param drumClef - DrawClef drawer
	 */
	private void drawStaff(List<Measure> staff, DrawDrumsetMusicLines d, DrawClef drumClef) {
		// Iterate through the list of measures
		for (Measure measure : staff) {
			// If the measure has a time signature, then draw it
			if (measure.getAttributes() != null && measure.getAttributes().getTime() != null) {
				// Draw music lines for the time signature
				d.draw(this.x, this.y);
				this.x += this.spacing;

				// Draw the time signature
				drumClef.drawTimeSignature(
					measure.getAttributes().getTime().getBeats(),
					measure.getAttributes().getTime().getBeats(),
					this.x - this.spacing / 2,
					this.y,
					this.fontSize,
					this.musicLineSpacing
				);
			}

			// Draw the current measure
			this.drawMeasure(measure);
		}
	}

	/**
	 * Draw the sheet music for drums.
	 */
	public void draw() {
		this.x = 0;
		this.y = 0;

		double paneWidth = this.pane.getMaxWidth();

		DrawDrumsetMusicLines d;
		DrawClef drumClef;

		this.x += this.spacing;

		// The length of the current staff (pixels)
		double staffLength = this.minimumSpacing;
		// The number of non chord or grace notes (number of notes that take up a unique space in the staff)
		int noteSpaces = 1;
		// The measures in the current staff
		List<Measure> staff = new ArrayList<Measure>();

		Measure measure;

		// Iterate through the measures
		for (int i = 0; i < this.measureList.size(); i++) {
			// Get the current measure
			measure = this.measureList.get(i);

			// Add the current measure to the staff list, and add its note spaces
			// and length to the counters
			staff.add(measure);
			noteSpaces += this.getMeasureLength(measure);
			staffLength = noteSpaces * this.minimumSpacing;

			// If the current measure is the last measure in the score,
			// or if the next measure will cause the staff to span greater than the pane width,
			// then draw the measure, otherwise keep iterating.
			if (i == this.measureList.size() - 1
				|| staffLength + this.getMeasureLength(measureList.get(i+1)) * this.minimumSpacing > paneWidth
			) {
				// Calculate the extra spacing to be added to the minimum spacing
				// (this makes the notes justified).
				double spacingPadding = (paneWidth - staffLength) / noteSpaces;
				this.spacing = this.minimumSpacing + spacingPadding;

				this.x = 0;

				// Draw music lines for clef
				d = new DrawDrumsetMusicLines(this.pane, this.spacing, 5 * this.fontSize + this.musicLineSpacing);
				d.draw(this.x, this.y);

				// Draw drum clef
				drumClef = new DrawClef(this.pane, d.getYPositionFromOctaveAndStep(5, "D"), d.getYPositionFromOctaveAndStep(4, "G"));
				drumClef.drawDrumClef();

				// Increment x-position
				this.x += this.spacing;

				// Draw staff
				this.drawStaff(staff, d, drumClef);

				// Reset staff list and counters
				staff.clear();
				noteSpaces = 1;
				staffLength = this.minimumSpacing;

				// Increment y-position
				this.y += this.staffSpacing;
			}
		}
	}

	/**
	 * Checks for ties and slurs in the current note and, based on previous tied and slurred notes,
	 * may draw the tie or slur as a curve between notes.
	 *
	 * @param note       - The current note to check for ties
	 * @param noteDrawer - The DrawDrumsetNote object for the current note
	 * @param xPosition  - The x-coordinate of the given note
	 * @param yPosition  - The y-coordinate of the given note
	 */
	private void handleTieOrSlur(Note note, DrawDrumsetNote noteDrawer, double xPosition, double yPosition) {
		// Tied and slurred notes are handled by keeping track of the coordinates of tied and slurred
		// notes for drums and cymbals (in a class variable), and drawing when there are enough tied or
		// slurred notes in these variables to be drawn.

		// Check for notations first (because ties and slurs are found in notations)
		if (note.getNotations() != null) {

			// Check if the note is a tied note
			if (note.getNotations().getTieds() != null) {

				// Check if the note is a cymbal note
				if (note.getNotehead() == null) {
					// If not a cymbal note, add the current coordinates to the drum tie coordinates list
					drumTieCoords.add(new Double[] {xPosition, yPosition});
				} else {
					// If it is a cymbal note, add the current coordinates to the cymbal tie coordinates list
					cymbalTieCoords.add(new Double[] {xPosition, yPosition});
				}

				// If the drum tie coordinate list has two elements, then a tie can be drawn
				if (drumTieCoords.size() == 2) {
					// Draw the tie
					noteDrawer.drawTie(drumTieCoords);

					// Check if the current note has more ties
					if (note.getNotations().getTieds().size() == 2) {
						// If the current note has two ties, then it is a start and end of a tie.
						// So we add the note back in the coordinate list for the next tie.
						drumTieCoords.clear();
						drumTieCoords.add(new Double[] {xPosition, yPosition});
					} else {
						// If the current note only has one tie, then just clear the list because it has been drawn
						drumTieCoords.clear();
					}
				}

				// If the cymbal tie coordinate list has two elements, then a tie can be drawn
				if (cymbalTieCoords.size() == 2) {
					// Draw the tie
					noteDrawer.drawTie(cymbalTieCoords);

					// Check if the current note has more ties
					if (note.getNotations().getTieds().size() == 2) {
						// If the current note has two ties, then it is a start and end of a tie.
						// So we add the note back in the coordinate list for the next tie.
						cymbalTieCoords.clear();
						cymbalTieCoords.add(new Double[] {xPosition, yPosition});
					} else {
						// If the current note only has one tie, then just clear the list because it has been drawn
						cymbalTieCoords.clear();
					}
				}
			}

			// Check if the note is a slurred note
			if (note.getNotations().getSlurs() != null) {

				// Check if the note is a cymbal note
				if (note.getNotehead() == null) {
					// If not a cymbal note, add the current coordinates to the drum slur coordinates list
					drumSlurCoords.add(new Double[] {xPosition, yPosition});
				} else {
					// If it is a cymbal note, add the current coordinates to the cymbal slur coordinates list
					cymbalSlurCoords.add(new Double[] {xPosition, yPosition});
				}

				// If the drum slur coordinate list has two elements, then a slur can be drawn
				if (drumSlurCoords.size() == 2) {
					noteDrawer.drawSlur(drumSlurCoords);
					drumSlurCoords.clear();
				}

				// If the cymbal slur coordinate list has two elements, then a slur can be drawn
				if (cymbalSlurCoords.size() == 2) {
					noteDrawer.drawSlur(cymbalSlurCoords);
					cymbalSlurCoords.clear();
				}
			}
		}
	}

	/**
	 * Calculates the length of the measure based on the spacing and the
	 * number of notes that are not chords or grace notes.
	 *
	 * @return The length of the measure in pixels.
	 */
	private int getMeasureLength(Measure measure) {
		List<Note> notes = measure.getNotesBeforeBackup();
		int length = 0;

		// Check if measure has time signature
		if (measure.getAttributes() != null && measure.getAttributes().getTime() != null) {
			length += 1;
		}

		if (notes != null) {
			for (Note note : notes) {
				if (note.getChord() == null && note.getGrace() == null) {
					length += 1;
				}
			}
		}

		return length;
	}

	public void clearHighlightMeasureArea() {
		ObservableList<?> children = pane.getChildren();
		ArrayList<Rectangle> removeRect = new ArrayList<Rectangle>();
		for (Iterator<?> iterator = children.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			// ONLY REMOVE RECTANGLES IF THEY ARE RED,
			if (((Rectangle) object).getStyle().equals("-fx-stroke: red;")) {
				removeRect.add((Rectangle) object);
			}
		}
		for (Iterator<Rectangle> iterator = removeRect.iterator(); iterator.hasNext();) {
			Rectangle rect = (Rectangle) iterator.next();
			pane.getChildren().remove(rect);
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

	public void highlightNote() {
		ArrayList<Rectangle> r = (ArrayList<Rectangle>) this.hightlightRectanlges;
		ArrayList<Double> noteDuration = (ArrayList<Double>) this.noteDurations;

		HighlightNote note = new HighlightNote(this, r, noteDuration);
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