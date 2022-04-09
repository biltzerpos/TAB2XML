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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Dot;
import models.measure.note.Note;
import models.measure.note.notations.Slide;
import models.measure.note.notations.Slur;
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
	private double spacing;
	private int LineSpacing;
	private int noteTypeCounter;
	private DrawNote noteDrawer;
	private int fontSize;
	private int staffSpacing;
	private DrawSlur slurDrawer;
	private double harmonic;
	private int ActualCounter;
	private int six16thActual;
	private int eight32ndnotes; 
	
	public Guitar() {
	}

	public Guitar(ScorePartwise scorePartwise, Pane pane, int noteSpacing, int font, int StaffSpacing,
			int LineSpacing) {
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
		this.noteDrawer.setGraceFontSize(this.fontSize - 4);
		this.d = new DrawMusicLines(this.pane, noteSpacing, staffSpacing);
		this.slurDrawer = new DrawSlur();
		this.slurDrawer.setPane(this.pane);
		this.harmonic = 0;
	}

	/*
	 * This method is where the actual drawing of the guitar elements happen
	 */

	public void drawGuitar() {
		Clef clef = getClef();
		double width = this.pane.getMaxWidth();
		List<Measure> tempList = new ArrayList<>();
		int numOfNotes = 0; 
		double tempSpacing = this.spacing; 
		
		for (int i = 0; i < measureList.size(); i++) {
			int spaceRequired = 0;
			Measure measure = measureList.get(i);
			// clef of first line
			if (x == 0) {
				d.draw(x, y);
				double clefSpacing = this.staffSpacing + (this.staffSpacing / 2);
				DrawClef dc = new DrawClef(this.pane, clef, x, y + clefSpacing);
				dc.setFontSize(this.fontSize + 6);
				dc.draw(clefSpacing);
				drawMeasureNum(i);
				x += spacing;
				spaceRequired += getSpacing();
			}
			List<Note> noteList = measure.getNotesBeforeBackup();
			spaceRequired += countNoteSpacesRequired(noteList);
			if (width >= spaceRequired) {
				//drawMeasureNotes(measure);
				tempList.add(measure); 
				numOfNotes += countNotes(noteList); 
				width = width - spaceRequired;
				if(i == measureList.size()-1) {
					drawMeasuresOnSameLine(tempList);
				}

			} else {
				System.out.println("The measure num: " + (i+1) + " initial spacing: "+ this.spacing);
				double extra = width/numOfNotes; 
				spacing += extra; 
				d.setLength(spacing); 
				drawMeasuresOnSameLine(tempList);
				System.out.println("the new Spacing: " + this.spacing); 
				tempList.removeAll(tempList);
				numOfNotes = 0; 
				spacing = tempSpacing;
				d.setLength(spacing); 
				System.out.println("reset spacing: "+ this.spacing); 
				
				this.x = 0.0;
				this.y += this.LineSpacing;
				width = this.pane.getMaxWidth();

				d.draw(x, y);
				double clefSpacing = this.staffSpacing + (this.staffSpacing / 2);
				DrawClef dc = new DrawClef(this.pane, clef, x, y + clefSpacing);
				dc.setFontSize(this.fontSize + 6);
				dc.draw(clefSpacing);
				drawMeasureNum(i);
				x += spacing;
				spaceRequired += getSpacing();
				numOfNotes += countNotes(noteList); 
				tempList.add(measure); 
				//drawMeasureNotes(measure);
				width = width - spaceRequired;

			}
			xCoordinates.put(measure, x);
			yCoordinates.put(measure, y);
			// System.out.println("Measure:" + measure + "X:" + x + "Y:" + y + pane);
		}

	}

	private int countNotes(List<Note> noteList) {
		// TODO Auto-generated method stub
		int numOfSpace = 0;
		for (int i = 0; i < noteList.size(); i++) {
			Note n = noteList.get(i);
			if (!noteHasChord(n) && !noteHasGrace(n)) {
				numOfSpace++;
			}
		}
		return numOfSpace;
	}

	private void drawMeasuresOnSameLine(List<Measure> tempList) {
		// TODO Auto-generated method stub
		for(Measure m: tempList) {
			drawMeasureNotes(m);
			drawBar(); 
		}
		
	}

	private void drawBar() {
		// TODO Auto-generated method stub
		double len = getLastLineCoordinateY() - getFirstLineCoordinateY();
		DrawBar bar = new DrawBar(this.pane, x, y, len);
		bar.draw();
	}

	private double getSpaceRequired(Measure measure) {
		double spaceRequired = 0;
		List<Note> noteList = measure.getNotesBeforeBackup();
		spaceRequired += countNoteSpacesRequired(noteList);
		return spaceRequired;
	}



	private void drawMeasureNum(int i) {
		// TODO Auto-generated method stub
		String n = Integer.toString(i + 1);
		Text num = new Text();
		num.setX(x);
		num.setY(getFirstLineCoordinateY() + y - this.fontSize);
		num.setText(n);
		num.setViewOrder(-1);
		num.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, this.fontSize));
		this.pane.getChildren().add(num);
	}

	// This method extracts a clef from a given measure
	public Clef getClef() {
		Clef clef = measureList.get(0).getAttributes().getClef();
		return clef;
	}

	// method to count the space required for notes in noteList
	private int countNoteSpacesRequired(List<Note> noteList) {
		int numOfSpace = countNotes(noteList); 
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
		this.ActualCounter = 2;
		this.six16thActual = 5; 
		this.eight32ndnotes = 7; 
		List<Note> noteList = measure.getNotesBeforeBackup();
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (noteHasTechnical(note)) {
				drawNoteWithTechnical(note, noteList, measure);
			}

		}

	}

	public Boolean noteHasTechnical(Note n) {
		Boolean result = n.getNotations().getTechnical() != null ? true : false;
		return result;
	}

	private void drawNoteWithTechnical(Note note, List<Note> noteList, Measure measure) {
		if (!noteHasChord(note)) {
			// Draw grace notes
			if (noteHasGrace(note)) {
				drawGraceNotes(note, noteList);
			} else {
				drawNoteWithoutGrace(note, noteList);

			}
			// drawSlur(note, noteList);

		} else if (noteHasChord(note)) {
			if (noteHasGrace(note)) {
				drawChordsWithGraceNotes(note, noteList);
			} else {
				drawChordWithoutGrace(note, noteList);

			}
			// drawSlur(note, noteList);
		}
		drawSlur(note, noteList);
		drawTie(note, noteList, measure);
		drawHarmonic(note, noteList);
		drawSlides(note, noteList);

	}

	private void drawSlides(Note note, List<Note> noteList) {
		if (noteHasSlide(note)) {
			List<Slide> slideList = note.getNotations().getSlides();
			for (Slide s : slideList) {
				int num = s.getNumber();
				String type = s.getType();
				if (type == "start") {
					int string = note.getNotations().getTechnical().getString();
					double positionY = getLineCoordinateY(string);
					Line line = new Line();
					line.setViewOrder(-1);
					line.setStroke(Color.BLACK);
					line.setStrokeWidth(this.fontSize / 6);
					line.setStartX(noteDrawer.getStartX() + (this.fontSize));
					line.setStartY(positionY + y + (this.fontSize / 2));
					int count = 1; 
					Loop: for (int i = noteList.indexOf(note); i < noteList.size() - 1; i++) {
						Note next = noteList.get(i + 1);
						if (noteHasSlide(next)) {
							List<Slide> nextSlide = next.getNotations().getSlides();
							for (Slide ns : nextSlide) {
								int nextNum = ns.getNumber();
								String nextType = ns.getType();
								if (nextNum == num && nextType == "stop") {
									int nextString = note.getNotations().getTechnical().getString();
									double nextPositionY = getLineCoordinateY(nextString);
									line.setEndX(noteDrawer.getStartX() + (spacing*(count)));
									line.setEndY(nextPositionY + y - (this.fontSize / 2));
									this.pane.getChildren().add(line);
									if(count > 1) {
										Text t= new Text(noteDrawer.getStartX() + (this.fontSize),positionY + y - (this.fontSize/2), "gliss" ); 
										t.setRotate(-20); 
										this.pane.getChildren().add(t);
									}
									break Loop;
								}
							}
						}
						count++; 
					}
				}
			}
		}

	}

	private boolean noteHasSlide(Note note) {
		// TODO Auto-generated method stub
		Boolean res = note.getNotations().getSlides() == null ? false : true;
		return res;
	}

	private void drawHarmonic(Note note, List<Note> noteList) {
		// TODO Auto-generated method stub
		if (noteHasHarmonic(note)) {
			double firstLine = getFirstLineCoordinateY();
			double xCenter = noteDrawer.getStartX() + this.fontSize / 2;
			double yCenter = firstLine + y - this.fontSize - (this.harmonic * this.fontSize);
			double radius = spacing / 25;
			Circle circle = new Circle(xCenter, yCenter, radius);
			circle.setFill(Color.WHITE);
			circle.setStroke(Color.BLACK);
			pane.getChildren().add(circle);

			for (int i = noteList.indexOf(note); i < noteList.size() - 1; i++) {
				Note next = noteList.get(i + 1);
				if (noteHasHarmonic(next)) {
					this.harmonic++;
					break;
				} else {
					this.harmonic = 0;
					break;
				}
			}
		} else {
			this.harmonic = 0;
		}
	}

	private boolean noteHasHarmonic(Note note) {
		// TODO Auto-generated method stub
		Boolean res = note.getNotations().getTechnical().getHarmonic() == null ? false : true;
		return res;
	}

	private void drawTie(Note note, List<Note> noteList, Measure measure) {
		// TODO Auto-generated method stub
		if (noteHasTie(note)) {
			slurDrawer.setStrokeWidth(this.fontSize);
			if (!noteHasChord(note)) {
				drawRegualrTied(note, noteList, measure);
			} else {
//				int f = note.getNotations().getTechnical().getFret();
//				List<Tied> tiedList = note.getNotations().getTieds();
//				for (Tied t : tiedList) {
//					String type = t.getType();
//					if (type == "start") {
//						// System.out.println(f +" "+ type + "\n");
//					}
//				}
				drawChordTied(note, noteList, measure);
			}

		}

	}

	private void drawChordTied(Note note, List<Note> noteList, Measure measure) {
		List<Tied> tiedList = note.getNotations().getTieds();
		for (Tied t : tiedList) {
			String type = t.getType();
			if (type == "start") {
				int string = note.getNotations().getTechnical().getString();
				double positionY = getLineCoordinateY(string);
				slurDrawer.setStartX(noteDrawer.getStartX() + fontSize);
				slurDrawer.setStartY(positionY + fontSize / 2 + y);
				slurDrawer.setPlace(1);
				int measureIndex = measureList.indexOf(measure);
				Boolean foundStop = false;
				loop: for (int i = noteList.indexOf(note); i < noteList.size() - 1; i++) {
					Note next = noteList.get(i + 1);
					if (noteHasTie(next)) {
						if (noteHasChord(next)) {
							// int n = next.getNotations().getTechnical().getFret();
							List<Tied> nextList = next.getNotations().getTieds();
							for (Tied nt : nextList) {
								String nextType = nt.getType();
								if (nextType == "stop") {
									slurDrawer.setEndX(noteDrawer.getStartX() + spacing);
									foundStop = true;
									break loop;
								}
							}
						}
					}
				}
				if (!foundStop) {
					Measure nextMeasure = measureList.get(measureIndex + 1);
					List<Note> nextNoteList = nextMeasure.getNotesBeforeBackup();
					loop1: for (Note n : nextNoteList) {
						if (noteHasTie(n) && noteHasChord(n)) {
							// int n1 = n.getNotations().getTechnical().getFret();
							List<Tied> tiedList2 = n.getNotations().getTieds();
							for (Tied nt : tiedList2) {
								String nextType = nt.getType();
								if (nextType == "stop") {
									slurDrawer.setEndX(noteDrawer.getStartX() + spacing);
									// System.out.println(f +" "+ type + " | "+ n1 + " "+ nextType +"\n");
									break loop1;
								}
							}
						}
					}
				}
				slurDrawer.draw();
			}
		}
	}

	private void drawRegualrTied(Note note, List<Note> noteList, Measure measure) {
		// TODO Auto-generated method stub
		List<Tied> tiedList = note.getNotations().getTieds();
		for (Tied t : tiedList) {
			String type = t.getType();
			if (type == "start") {
				int string = note.getNotations().getTechnical().getString();
				double positionY = getLineCoordinateY(string);
				slurDrawer.setStartX(noteDrawer.getStartX() + fontSize);
				slurDrawer.setStartY(positionY - fontSize / 2 + y);
				slurDrawer.setPlace(-1);
				int measureIndex = measureList.indexOf(measure);
				Boolean foundStop = false;
				loop: for (int i = noteList.indexOf(note); i < noteList.size() - 1; i++) {
					Note next = noteList.get(i + 1);
					if (noteHasTie(next)) {
						if (!noteHasChord(next)) {
							int n = next.getNotations().getTechnical().getFret();
							List<Tied> nextList = next.getNotations().getTieds();
							for (Tied nt : nextList) {
								String nextType = nt.getType();
								if (nextType == "stop") {
									slurDrawer.setEndX(noteDrawer.getStartX() + spacing);
									foundStop = true;
									break loop;
								}
							}
						}
					}
				}
				if (!foundStop) {
					Measure nextMeasure = measureList.get(measureIndex + 1);
					List<Note> nextNoteList = nextMeasure.getNotesBeforeBackup();
					loop1: for (Note n : nextNoteList) {
						if (noteHasTie(n) && !noteHasChord(n)) {
							int n1 = n.getNotations().getTechnical().getFret();
							List<Tied> tiedList2 = n.getNotations().getTieds();
							for (Tied nt : tiedList2) {
								String nextType = nt.getType();
								if (nextType == "stop") {
									slurDrawer.setEndX(noteDrawer.getStartX() + spacing);
									// System.out.println(f +" "+ type + " | "+ n1 + " "+ nextType +"\n");
									break loop1;
								}
							}
						}
					}
				}
				slurDrawer.draw();
			}
		}
	}

	private boolean noteHasTie(Note note) {
		Boolean res = note.getNotations().getTieds() == null ? false : true;
		return res;
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
			graceSpacing = xPosition - ((spacing*num) / (4 ));
		} else {
			graceSpacing = xPosition - (spacing / (4 / num) + num);
		}
		double positionY = getLineCoordinateY(string);
		noteDrawer.setPane(pane);
		noteDrawer.setNote(note);
		noteDrawer.setStartX(graceSpacing);
		noteDrawer.setStartY(positionY + 3 + y);
		noteDrawer.drawGuitarGrace();
		// drawSlur(note, noteList);
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
		// drawSlur(note, noteList);

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
		// drawSlur(note, noteList);
		/*
		 * double py = getLastLineCoordinateY(); DrawNoteType type = new
		 * DrawNoteType(pane, note, noteDrawer.getStartX() + 7, py + y);
		 * type.drawType();
		 */
	}

	private void drawType(Note note, List<Note> noteList) {
		String current = note.getType();
		double py = getLastLineCoordinateY();
		double shortStick = this.fontSize+3;
		DrawNoteType type = new DrawNoteType(pane, noteDrawer.getStartX() + 4, py + y, shortStick);
		if(noteHasActual(note)) {
			//if(!beforeHasActual(note, noteList)) {
			//get the number of the actuals in a row --> 2 or 3
			int ActualCounter = getActualNum(note, noteList); 
			//System.out.println(ActualCounter);
				int f = note.getNotations().getTechnical().getFret(); 
				//System.out.println("note is: "+ f+" | type: "+current+" | counter is: "+ ActualCounter);
			//}	
			drawActualTypes(current, note, noteList, type, py); 
			if(ActualCounter == 2 &&(current == "eighth" || current == "quarter")) {
				int actual = note.getTimeModification().getActualNotes(); 
				//type.drawActual(actual, spacing, this.fontSize);
			}
		}
		else {
			if(current =="half") {
				type.drawShortLine();
				drawDot(note, type, py + shortStick);
			}
			if(current == "quarter") {
				type.drawLongLine();
				drawDot(note, type, py);
			}
			if(current == "eighth"){
				drawEighth(note, noteList, type, py); 
			}
			if(current == "16th") {
				draw16th(note, noteList, type, py); 
			}
			if(current == "32nd") {
				draw32ndnotes(note, noteList, type, py);
			}
		}
	}

	private void drawActualTypes(String current, Note note, List<Note> noteList, DrawNoteType type, double py) {
		// TODO Auto-generated method stub
		String nextType = getNextType(note, noteList); 
		String lastType = getLastType(note, noteList); 
		if(current=="quarter") {
			type.drawLongLine();
			drawDot(note, type, py);
			if(nextType == "eighth") {
				int actual = note.getTimeModification().getActualNotes(); 
				type.drawActual(actual, spacing, this.fontSize);
			}
			
		}
		else if(current == "eighth") {
			type.drawLongLine();
			Boolean nextIsActual = nextHasActual(note, noteList); 
			if(nextIsActual==false && lastType != "eighth") {
				type.drawBeam(spacing/4);
			}
			//String nextType = getNextType(note, noteList); 
			else {
				if(nextType == "eighth" && this.ActualCounter > 0 && nextIsActual==true) {
					type.drawBeam(spacing);
					//type.setStartY(py + y - 5);
					//type.drawBeam(spacing);
					this.ActualCounter--; 
				}
				else if(nextType == "quarter" && nextIsActual==true) {
					int actual = note.getTimeModification().getActualNotes(); 
					type.drawActual(actual, spacing, this.fontSize);
				}
				else {
					String actual = Integer.toString(note.getTimeModification().getActualNotes()); 
					Text t = new Text(noteDrawer.getStartX() -spacing, py + y+50, actual);
					t.setViewOrder(-1);
					t.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC,  this.fontSize));
					this.pane.getChildren().add(t); 
					this.ActualCounter = 2; 
				}
			}
		}
		else if(current == "16th") {
			type.drawLongLine();
			//String nextType = getNextType(note, noteList); 
			if(nextType == "16th" && this.ActualCounter > 0) {
				type.drawBeam(spacing);
				type.setStartY(py + y - 5);
				type.drawBeam(spacing);
				this.ActualCounter--; 
			}
			else {
				String actual = Integer.toString(note.getTimeModification().getActualNotes()); 
				Text t = new Text(noteDrawer.getStartX() -spacing, py + y+50, actual);
				t.setViewOrder(-1);
				t.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC,  this.fontSize));
				this.pane.getChildren().add(t); 
				this.ActualCounter = 2; 
			}
			if(this.six16thActual > 0 ) {
				type.drawBeam(spacing);
				this.six16thActual--; 
			}
			else {
				this.six16thActual = 5; 
			}
		}
	}

	private Boolean nextHasActual(Note note, List<Note> noteList) {
		// TODO Auto-generated method stub
		Boolean res = false; 
		for (int i = noteList.indexOf(note); i<noteList.size()-1; i++) {
			Note next = noteList.get(i+1); 
			if(!noteHasGrace(next) && !noteHasChord(next)) {
				if(noteHasActual(next)) {
					res = true; 
					break; 
				}
				else {
					break; 
				}
			}
		}
		return res;
	}

	private int getActualNum(Note note, List<Note> noteList) {
		int res = 0; 
		for(int i = noteList.indexOf(note); i<noteList.size(); i++) {
			Note next = noteList.get(i); 
			if(!noteHasChord(next)) {
				if(noteHasActual(next)) {
					//int n = next.getNotations().getTechnical().getFret(); 
					//System.out.println("the next note with technical is: "+ n + "\n\n");
					res++; 
				}
				else {
					break; 
				}
			}
		}
		return res;
	}

	private void draw32ndnotes(Note note, List<Note> noteList, DrawNoteType type, double py) {
		type.drawLongLine();
		drawDot(note, type, py);
		String nextType = getNextType(note, noteList); 
		String lastType = getLastType(note, noteList); 
		if(nextType == "32nd") {
			if(this.noteTypeCounter > 0) {
				type.drawBeam(spacing);
				type.setStartY(py + y - 5);
				type.drawBeam(spacing);
				type.setStartY(py + y - 10);
				type.drawBeam(spacing);
				this.noteTypeCounter--; 
			}
			else {
				this.noteTypeCounter = 3; 
			}
			if(this.eight32ndnotes > 0 ) {
				type.drawBeam(spacing);
				this.eight32ndnotes--; 
			}
			else {
				this.eight32ndnotes = 8; 
			}
		}
		else {
			if(lastType != "32nd") {
				if( lastType!="16th") {
					type.drawBeam(spacing/4);
					type.setStartY(py + y - 5);
					type.drawBeam(spacing/4);
				}
				else {
					//type.drawBeam(-spacing/2);
					type.setStartY(py + y - 10);
					type.drawBeam(-spacing/2);
				}
			}
			
			this.noteTypeCounter =3; 
		}
		
	}
	private void draw16th(Note note, List<Note> noteList, DrawNoteType type, double py) {
		type.drawLongLine();
		drawDot(note, type, py);
		String nextType = getNextType(note, noteList); 
		String lastType = getLastType(note, noteList); 
		if(nextType == "16th") {
			if(this.noteTypeCounter > 0) {
				type.drawBeam(spacing);
				type.setStartY(py + y - 5);
				type.drawBeam(spacing);
				this.noteTypeCounter--; 
			}
			else {
				if(nextIsLastNote(note, noteList)) {
					type.drawBeam(spacing);
					type.setStartY(py + y - 5);
					type.drawBeam(spacing);
				}
				this.noteTypeCounter = 3; 
			}
		}
		else if(nextType == "eighth") {
			type.drawBeam(spacing);
		}
		else if(nextType =="32nd") {
			type.drawBeam(spacing);
			type.setStartY(py + y - 5);
			type.drawBeam(spacing);
		}
		else {
			if(lastType != "16th") {
				if( lastType!="eighth") {
					type.drawBeam(spacing/4);
					type.setStartY(py + y - 5);
					type.drawBeam(spacing/4);
				}
				else {
					type.drawBeam(-spacing/2);
					type.setStartY(py + y - 5);
					type.drawBeam(-spacing/2);
				}
			}
			
			this.noteTypeCounter =3; 
		}
	}

	private boolean nextIsLastNote(Note note, List<Note> noteList) {
		// TODO Auto-generated method stub
		Boolean res = false; 
		int index = noteList.indexOf(note); 
		if(index + 1 == noteList.size()-1) {
			res = true; 
		}
		return res;
	}

	private void drawEighth(Note note, List<Note> noteList, DrawNoteType type, double py) {
		// TODO Auto-generated method stub
		type.drawLongLine();
		drawDot(note, type, py);
		String nextType = getNextType(note, noteList); 
		String lastType = getLastType(note, noteList); 
		if(nextType == "eighth") {
			if(this.noteTypeCounter > 0) {
				type.drawBeam(spacing);
				this.noteTypeCounter--; 
			}
			else {
				this.noteTypeCounter = 3; 
			}
		}
		else if(nextType == "16th"&& this.noteTypeCounter > 0) {
			type.drawBeam(spacing);
			this.noteTypeCounter = 3; 
		}
		else {
			if(lastType != "eighth") {
				if(nextType == "16th") {
					type.drawBeam(spacing);
				}
				else {
				type.drawBeam(spacing/4);}
			}
			
			this.noteTypeCounter = 3; 
		}
	}


	private String getLastType(Note note, List<Note> noteList) {
		String lastType = ""; 
		int index = noteList.indexOf(note); 
		for(int i = index; i> 0; i--) {
			Note last = noteList.get(i-1); 
			if(!noteHasGrace(last)&&!noteHasChord(last)) {
				lastType = last.getType(); 
				break; 
			}
		}
		return lastType; 
	}

	private String getNextType(Note note, List<Note> noteList) {
		String nextType = ""; 
		int index = noteList.indexOf(note); 
		for(int i = index; i< noteList.size()-1; i++) {
			Note next = noteList.get(i+1); 
			if(!noteHasGrace(next)&&!noteHasChord(next)) {
				nextType = next.getType(); 
				break; 
			}
		}
		return nextType; 
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
		if (noteHasSlur(note)) {
			// int f = note.getNotations().getTechnical().getFret();
			// System.out.println(f +"\n");
			int string = note.getNotations().getTechnical().getString();
			double positionY = getLineCoordinateY(string);
			List<Slur> slurList = note.getNotations().getSlurs();
			for (Slur s : slurList) {
				int num = s.getNumber();
				String type = s.getType();
				if (type == "start") {
					slurDrawer.setStartX(noteDrawer.getStartX());
					slurDrawer.setStrokeWidth(this.fontSize / 2);
					int index = noteList.indexOf(note);
					lookForStop: for (int i = index; i < noteList.size() - 1; i++) {
						Note next = noteList.get(i + 1);
						if (noteHasSlur(next)) {
							List<Slur> nextSlurs = next.getNotations().getSlurs();
							for (Slur ns : nextSlurs) {
								int nsNum = ns.getNumber();
								String nsType = ns.getType();
								// String placement = "";
								if (nsType == "stop" && nsNum == num) {
									if (noteHasGrace(next)) {
										slurDrawer.setEndX(noteDrawer.getStartX() + spacing / 4);
									} else if ((noteHasGrace(note) && !noteHasGrace(next))
											|| (!noteHasGrace(note) && noteHasGrace(next))) {
										slurDrawer.setEndX(noteDrawer.getStartX() + spacing / 2);
									} else {
										slurDrawer.setEndX(noteDrawer.getStartX() + spacing + spacing / 4);
									}
									String placement = "";
									if (s.getPlacement() != null) {
										placement = s.getPlacement();
									}
									if (noteHasChord(note)) {
										slurDrawer.setStartY(positionY + fontSize + y);
										slurDrawer.setPlace(1);
									} else {
										slurDrawer.setStartY(positionY - fontSize + y);
										slurDrawer.setPlace(-1);
									}
									// int c = note.getNotations().getTechnical().getFret();
									// int n = next.getNotations().getTechnical().getFret();
									// System.out.println("grace between: "+ c+ " and "+ n);
									slurDrawer.draw();
									break lookForStop;
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean noteHasSlur(Note note) {
		Boolean res = note.getNotations().getSlurs() == null ? false : true;
		return res;
	}

	public void clearHighlightMeasureArea() {
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

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	

	// End getters and setters
}