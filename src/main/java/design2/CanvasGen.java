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

	private int globalX = 50;
	private int globalY = 150;
	private NoteIdentifier noteIdentifier;

	public CanvasGen(double height, double width, MainViewController mvc) throws FileNotFoundException {
		// Configure Canvas
		this.setWidth(height);
		this.setHeight(width);
		gc = this.getGraphicsContext2D();
		gc.setFont(new Font("Bravura", fontSize)); // fontSize changes size of every drawn element
		try {
			f = Font.loadFont(new FileInputStream(new File(System.getProperty("user.home") + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "fonts" + File.separator + "NotoMusic-Regular.ttf")), fontSize);
		} catch (FileNotFoundException e) {
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
		while(globalX < this.getWidth()) {
			gc.fillText(getStaffType(score.getModel().getPartList().getScoreParts().get(0).getPartName()), globalX, globalY);
			globalX += 30;
		}
		globalX = 40;
	}

	public void drawNotes(Score score) throws TXMLException, IOException {
		for (Measure m : score.getModel().getParts().get(0).getMeasures()) {
			for (Note n : m.getNotesBeforeBackup()) {
				String pitchFret = "";
				NoteIdentifier noteIdentifier = new NoteIdentifier();

				if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
					pitchFret = "" + n.getPitch().getStep() + n.getPitch().getOctave();
					if(globalX >= 600) {
						globalX = 40;
						globalY += 150;
						drawClef(score);
						drawStaff(score);
					}
					if (n.getChord() != null) {
						globalX -= 40;
						// currentNOPG--;
					}
					if(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()) == 0) {
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()));
					}
					else {
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()));
						drawLines(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()), score, n);
					}
					globalX += 40;
				}
				else if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
					pitchFret = "" + n.getInstrument().getId();
					if(globalX >= 600) {
						globalX = 40;
						globalY += 150;
						drawClef(score);
						drawStaff(score);
					}
					if(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()) == 0) {
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()));
					}
					else {
						gc.fillText(getNoteType(n.getDuration()), globalX, globalY + noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()));
						drawLines(noteIdentifier.noteLines(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave()), score, n);
					}
					globalX += 40;
				}
				// TODO remove print
				System.out.println(pitchFret);
			}
		}
	}
	
	public void drawLines(int lines, Score score, Note n) throws IOException, TXMLException {
		if (lines > 0) {
			// TODO remove print 
			System.out.println(globalY);
			int innerY = globalY + 15
					+ noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave());
			for (int i = 0; i < lines; i++) {
				gc.fillText("\uD834\uDD16", globalX , innerY, 20);
				innerY -= 6;
			}
		}
		else if (lines < 0) {
			// TODO remove print 
			System.out.println(globalY);
			int innerY = globalY + 15
					+ noteIdentifier.identifyNote(score.getModel().getPartList().getScoreParts().get(0).getPartName(), "" + n.getPitch().getStep() + n.getPitch().getOctave());
			for (int i = 0; i < lines; i++) {
				gc.fillText("\uD834\uDD16", globalX , innerY, 20);
				innerY += 6;
			}
		}
	}

	public String getStaffType(String instrument) {
		String staffType = "";

		switch(instrument) {
		case "Guitar":
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
		case "D2": // TODO check instrument name
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
		case "D1": // TODO check instrument name
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
