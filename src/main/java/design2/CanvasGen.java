package design2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import GUI.MainViewController;
import converter.Score;
import custom_exceptions.TXMLException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import models.measure.Measure;
import models.measure.note.Note;

import java.awt.*;

public class CanvasGen extends Canvas {
	private GraphicsContext gc;
	private int fontSize = 40;
	private Font f;

	
	private int initialX = 60;
	private int shiftX = 25; 
	private int globalX = initialX;
	private int globalY = 150;
	private int lineWidth;
	private int measureWidth = 400; 
	private NoteIdentifier noteIdentifier;

	public CanvasGen(double height, double width, MainViewController mvc) throws FileNotFoundException {
		// Configure Canvas
		this.setWidth(width);
		lineWidth=(int) (Math.floor(width/measureWidth)*measureWidth); // to make it a multiple of measure width, so each line show whole measures
		this.setHeight(height);
		gc = this.getGraphicsContext2D();
		gc.setFont(new Font("Bravura", fontSize)); // fontSize changes size of every drawn element
		try {
			f = Font.loadFont(new FileInputStream(new File(System.getProperty("user.home") + File.separator + "git" + File.separator + "TAB2XML" + 
																					File.separator + "src" + File.separator + "main" + File.separator + "resources" + 
																					File.separator + "fonts" + File.separator + "NotoMusic-Regular.ttf")), fontSize);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gc.setFont(f); // fontSize changes size of every drawn element
		//				 this.setStyle("-fx-padding: 10;" +
		//				            "-fx-border-style: solid inside;" +
		//				            "-fx-border-width: 2;" +
		//				            "-fx-border-insets: 5;" +
		//				            "-fx-border-radius: 5;" +
		//				            "-fx-border-color: white;" +
		//				            "-fx-background-color: white");
//		this.mvc = mvc;
		noteIdentifier = new NoteIdentifier();
	}

	public void draw(Score score) throws TXMLException, IOException {
		// fillText(Java source code, x-coordinate, y-coordinate);
		// For Java source unicode: https://www.fileformat.info/index.htm

		drawClef(score);
		drawStaff(score);
		
//		System.out.println("X:" + globalX + " | Y: " + globalY);
//		gc.fillText("\uD834\uDD5D", globalX, globalY);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX, globalY - 5);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 2, globalY - 10);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 3, globalY - 15); 
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 4, globalY - 20);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 5, globalY - 25);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 6, globalY - 30);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 7, globalY - 35);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 8, globalY - 40);
//		gc.fillText("\uD834\uDD5D", globalX + shiftX * 9, globalY - 45);
		
		drawNotes(score);

//		gc.fillText(getNoteType(64), 10, 50); // whole
//		gc.fillText(getNoteType(32), 30, 50); // half
//		gc.fillText(getNoteType(16), 50, 50); // quarter
//		gc.fillText(getNoteType(8), 70, 50); // eighth
//		gc.fillText(getNoteType(4), 90, 50); // sixteenth
//		gc.fillText(getNoteType(2), 110, 50); // thirty-second
//		gc.fillText(getNoteType(1), 130, 50); // sixty-fourth
//		gc.fillText(getNoteType(1 / 2), 150, 50); // one-twenty-eighth



	}

	public void drawClef(Score score) throws TXMLException {
		gc.fillText(getClefType(score.getModel().getPartList().getScoreParts().get(0).getPartName()), 10, globalY);

	}

	public void drawStaff(Score score) throws TXMLException {
		globalX = 10;
		
		while(globalX < lineWidth) {
			for(int i = -2; i < 4; i++) {
				System.out.println("IN STAFF METHOD X:" + globalX + " | Y: " + globalY);
				gc.fillText(getStaffType(score.getModel().getPartList().getScoreParts().get(0).getPartName()), globalX-10, globalY - (10 * i));
			}	
			globalX += shiftX;
		}
		gc.fillText("\uD834\uDD00",globalX, globalY); 
		gc.fillText("\uD834\uDD00",globalX, globalY-10);
		globalX = initialX;
	}

	public void drawNotes(Score score) throws TXMLException, IOException {
		for (Measure m : score.getModel().getParts().get(0).getMeasures()) {
			gc.fillText("\uD834\uDD00",globalX, globalY); 
			gc.fillText("\uD834\uDD00",globalX, globalY-10);
			
			//note: no idea how to get the time signature, m.getAttributes().getTime() not working. so i'm just adding all the notes in the measure instead
			// eg 4 4 beat is 4/4 = 1 whole note, measurTime=1
			//float measureTime=(float)m.getAttributes().getTime().getBeats()/(float)m.getAttributes().getTime().getBeatType();
			float measureTime=0;
			boolean isChord=false;
			for (Note n : m.getNotesBeforeBackup()) {
				if(n.getChord()==null) {
					isChord=false;
					measureTime+=1/(float)n.getDuration();
				}else if(!isChord) {
					isChord=true;
					measureTime+=1/(float)n.getDuration();
				}
				
			}
			//replace the above if we get time signature working
			
			
			for (Note n : m.getNotesBeforeBackup()) {
				NoteIdentifier noteIdentifier = new NoteIdentifier();

				if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
					if(globalX >= lineWidth) {
						globalY += 150;
						drawClef(score);
						drawStaff(score);
					}
					if(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()) == 0) {
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()));
					}
					else {
//						if(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()) < 0) {
//							gc.scale(1, -1);
//						}
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()));
						drawLines(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()), score, n);
					}
					if (n.getChord() == null) {
						globalX += measureWidth/n.getDuration()/measureTime;
						// currentNOPG--;
					}
				}
				else if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
					if(globalX >= lineWidth) {
						globalX = 50;
						globalY += 150;
						drawClef(score);
						drawStaff(score);
					}

					if(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getInstrument().getId()) == 0) {
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getInstrument().getId()));
					}
					else {
						// TODO use vertivaly flipped notes
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getInstrument().getId()));
						drawLines(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()), score, n);
					}
					if (n.getChord() == null) {
						globalX += measureWidth/n.getDuration()/measureTime;
						// currentNOPG--;
					}
				}
			}
		}
	}
	
	public void drawLines(int lines, Score score, Note n) throws IOException, TXMLException {
		if (lines > 0) {
			// TODO remove print 
			System.out.println("lines < 0 | " + globalY);
			int innerY = globalY + 15
					- noteIdentifier.noteOnLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave())
					+ noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave());
			for (int i = 0; i < lines; i++) {
				gc.fillText("\uD834\uDD16", globalX , innerY, 20);
				innerY -= 10;
			}
		}
		else if (lines < 0) {
			// TODO remove print 
			System.out.println("lines > 0 | " + globalY);
			int innerY = globalY - 15
					+ noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave());
			for (int i = 0; i < (-1 * lines); i++) {
				gc.fillText("\uD834\uDD16", globalX , innerY, 20);
				innerY += 10;
			}
		}
	}

	public String getStaffType(String instrument) {
		String staffType = "";

		switch(instrument) {
		case "Sixer":
			staffType = "\uD834\uDD1B"; // six line staff
			break;
		case "Drumset":
			staffType = "\uD834\uDD1B"; // six line staff
			break;
		case "F": // TODO check instrument name
			staffType = "\uD834\uDD1A"; // five line staff
			break;
		case "F Alta": // TODO check instrument name
			staffType = "\uD834\uDD19"; // four line staff
			break;
		case "F Bassa": // TODO check instrument name
			staffType = "\uD834\uDD18"; // three line staff
			break;
		case "D1": // TODO check instrument name
			staffType = "\uD834\uDD17"; // two line staff
			break;
		case "Guitar": // TODO check instrument name
			staffType = "\uD834\uDD16"; // one line staff
			break;
		}
		return staffType;
	}

	public String getClefType(String instrument) {
		String clefType = "";

		switch(instrument) {
		case "Guitar":
			clefType = "\uD834\uDD1E"; // g clef
			break;
		case "Guitar Alta": //TODO check instrument name
			clefType = "\uD834\uDD1F"; // g clef octava alta
			break;
		case "Guitar Bassa": // TODO check instrument name
			clefType = "\uD834\uDD20"; // g clef octava bassa
			break;
		case "C": // TODO check instrument name
			clefType = "\uD834\uDD21"; // c clef
			break;
		case "F": // TODO check instrument name
			clefType = "\uD834\uDD22"; // f clef
			break;
		case "F Alta": // TODO check instrument name
			clefType = "\uD834\uDD23"; // f clef octava alta
			break;
		case "F Bassa": // TODO check instrument name
			clefType = "\uD834\uDD24"; // f clef octava bassa
			break;
		case "Drumset": // TODO check instrument name
			clefType = "\uD834\uDD25"; // drum clef-1
			break;
		case "D2": // TODO check instrument name
			clefType = "\uD834\uDD26"; // drum clef-2
			break;
		}
		return clefType;
	}

	public String getNoteType(int duration) {
		String noteType = "";

		switch(duration) {
		case 64:
			noteType = "\uD834\uDD5D";
			break;
		case 32:
			noteType = "\uD834\uDD5E";
			break;
		case 16:
			noteType = "\uD834\uDD5F";
			break;
		case 8:
			noteType = "\uD834\uDD60";
			break;
		case 4:
			noteType = "\uD834\uDD61";
			break;
		case 2:
			noteType = "\uD834\uDD62";
			break;
		case 1:
			noteType = "\uD834\uDD63";
			break;
		case 1 / 2:
			noteType = "\uD834\uDD64";
			break;
		}
		return noteType;
	}

}
