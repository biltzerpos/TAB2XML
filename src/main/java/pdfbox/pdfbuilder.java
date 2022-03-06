package pdfbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.prefs.Preferences;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import converter.Score;
import custom_exceptions.TXMLException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import models.Part;
import models.measure.Measure;
import models.measure.note.Note;
import player.DrumInstrument;
import utility.DrumPiece;
import utility.DrumUtils;
import utility.Settings;

////pdf file creation details
// doc.save(file path)                                               (path = "C/document/etc/...")
// doc.close                                                         (always evoke this command when doen with document
// doc.addpage(page)                                                 (adds a page)
// File file = new File(String "path of the document")               (creates a new file)
// PDDocument.load(file);                                            (pdf loaded as a new document)
// doc.getNumberOfPages()                                            (returns int number)
// doc.removepage(int number)                                        (remove page based on index system)

////Miscelaneous pdf details control commands
// PDDocumentInformation pdd = output.getDocumentInformation();           (can use another document info, or create a new one to apply to a document)
// pdd.setAuthor(String name);                                            (sets author for document details)
// pdd.setTitle(String name);                                             (same as above but title)
// pdd.setCreator(String name);                                           (same as above but creator)
// pdd.setSubject(String name);                                           (same as above but subject)
// Calendar date = new GregorianCalendar();                               (creates a time variable for pdf details)
// date.set(int year, int month, int day);                                (uses date variable to set a date)
// pdd.setCreationDate(date);                                             (applies date variable to pdf details) (creation date)
// pdd.setModificationDate(date);                                         (same as creation date procedure but for modification)
// pdd.setKeywords(String keyword);                                      (just adding keywords)
//
// a bunch of accessor commands are available per the library such as pdd.getCreationDate(), etc...

////Content file to insert into pdf creation
// PDPageContentStream contentStream = new PDPageContentStream(doc, page);(creates a variable to then add to the document)
//
///contentStream.beginText();
//���������..
//code to add text content                                                (example on how to add text to the variable)
//���������..
//contentStream.endText();
//
//contentStream.setFont(PDType1Font.some_font,int font_size );            (self-explanatory);
//contentStream.showText(String text);                                    (used to insert the variable into the pdf)
//contentStream.endText();                                                (end insertion)
//contentstream.close();                                                  (need to close after)
//contentStream.setLeading(14.5f);                                        (not really sure)
//contentStream.newLineAtOffset(25, 725);                                 (same as above)
//contentStream.newLine();                                                (moves to a new line)

////Image Insertion
//PDImageXObject pdImage = PDImageXObject.createFromFile("C:/logo.png", doc);    (create image variable from path, and then indicate which doc to add it to)
//PDPageContentStream contentStream = new PDPageContentStream(doc, page);        (have to prepare content stream for next step)
//contentstream.drawImage(pdImage, 70, 250);                                     (number are for example, int based, guessing its for dimensions or the coordinates on the page)
//contentstream.close();                                                         (remember to close after being done)
//doc.save(file path in the directory)                                           (saves to apply image)
//doc.close()                                                                    (closes document)

/*-------------------------------------------------------------------------------------------------------------------------------------------
 *
 * 	Scans tab like a book:

  1) Start at first measure
  2) Examines first line
  3) Then the next line
  4) Keeps track of the position of each line's first position (ex.    " e||--------------------5|
                                                p is 26 (0+(25+1))       B||---3----------------||
                                                p is 52                  G||*------------------*||
                                                ..                       D||*----5-------------*||
                                                ..                       A||-------------8------||
                                                ..                       E||--------------------||"
  5) After first measure is examined, move to next measure
  6) Repeat until all measures examined.


To get the examined data score.getModel(), this returns a ScorePartwise object with the data, series of notes is:


// Draw notes on PDF
//
    for(Measue i : score.getModel().getPart().getMeasure()) {
      for(Note n : i.getNote()) {
        //inner loop goes through notes of the ith measure and draws notes on pdf
      }
    }
//

//Create the corresponding amount of pages
counter pages
final notesPERpage
counter maxNotesHoldable


	while(maxNotesHOlder < notes.getSize){
  		gen pdf;
  	maxNotesHOlder += notesPERpage
	}
 * ------------------------------------------------------------------------------------------------------------------------------------------
 *
 */

//@SuppressWarnings("unused")
public class pdfbuilder {
	// default builder
	// variables need to be static
	private static Preferences pref;
	private static Settings s;
	private static File file;

	// JAVA doc object, has an array of pages which are of type PDPage
	public PDDocument doc;
	private static PDPage page;

	// needed for contentStream so it can put image on page
	private static PDImageXObject pageImage;

	// used to insert image onto page
	private static PDPageContentStream contentStream;
	private String userPath;
	private final int notesPerPage = 50;
	private int maxNotesTotal = notesPerPage;
	private int totalNotes = 0;
	private int globalX = 78;
	private int globalY = 632;
	private int pageCounter;
	private String instr = "";

	private PDFRenderer renderer;
	// initializer for the document

	//// Rough method idea
	// Once you press the preview or save button, you call this method with the PNG
	//// files so that it uses them to build it
	// Don't know how to hook it up to be viewable or interact with the buttons
	//// pressed on the GUI, will require help

	// Offset calculated using first group as reference frame
	// First Global y = 901
	// Second Global y = 726 (change in y 901 - 726 = 175)
	// so on.....
	public enum Offset {

		// TODO: NEW offset x 198
		// y 153
		E2offsety(60, 7), // 1
		F2offsety(54, 6), // 2
		G2offsety(48, 6), // 3
		A2offsety(42, 5), // 4
		B2offsety(36, 5), // 5

		C3offsety(30, 4), // 6
		D3offsety(24, 4), // 7
		E3offsety(18, 3), // 8
		F3offsety(12, 3), // 9
		G3offsety(6, 2), // 10
		A3offsety(0, 2), // 11
		B3offsety((-6), 1), // 12

		C4offsety((-12), 1), // 13
		D4offsety((-18), 0), // 14
		E4offsety((-24), 0), // 15
		F4offsety((-30), 0), // 16
		G4offsety((-36), 0), // 17
		A4offsety((-42), 0), // 18
		B4offsety((-48), 0), // 19

		C5offsety((-54), 0), // 20
		D5offsety((-60), 0), // 21
		E5offsety((-66), 0), // 22
		F5offsety((-72), 0), // 23
		G5offsety((-78), 0), // 24
		A5offsety((-84), -1), // 25
		B5offsety((-90), -1), // 26

		C6offsety((-96), -2), // 27
		D6offsety((-112), -2), // 28
		E6offsety((-118), -23); // 29

		protected final Integer offset;
		protected final Integer lines;

		Offset(int i, int j) {
			this.offset = i;
			this.lines = j;
		}

		public int offset() {
			return (this.offset / 2);
		}

		public int lines() {
			return this.lines;
		}
	}

	// Main method to call to create PDF
	public void sheetpdf(Score score) throws IOException, TXMLException {
		// set userPath
		// System.getProperty("user.home") returns a String of the user Path (for
		// example, "C:\Users\ian")
		userPath = System.getProperty("user.home");

		Part part = score.getModel().getParts().get(0);

		// get total notes to draw
		for (Measure measure : part.getMeasures()) {
			totalNotes += measure.getNotesBeforeBackup().size();
		}

		pdfpagegen();
		// check if extra page is needed, if so, generate extra page
		while (maxNotesTotal <= totalNotes) {
			// gen next page of sheet music
			pdfpagegen();
			maxNotesTotal += 50;
		}

		int mLength = part.getMeasures().size();


		int iteration = 0;
		int measure = 0;
		// map notes onto sheet
		for (Measure m : part.getMeasures()) {
			for (Note n : m.getNotesBeforeBackup()) {
				String pitchFret = "";
				if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
					instr = "Guitar";
					pitchFret = "" + n.getPitch().getStep() + n.getPitch().getOctave();

					switch (pitchFret) {
					// TODO: change offset and the call to Offset
					case "E2": // 1
						arbitraryPath(Offset.E2offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.E2offsety.lines(), n, score, iteration, measure);
						break;
					case "F2": // 2
						arbitraryPath(Offset.F2offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.F2offsety.lines(), n, score, iteration, measure);
						break;
					case "G2": // 3
						arbitraryPath(Offset.G2offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.G2offsety.lines(), n, score, iteration, measure);
						break;
					case "A2": // 4
						arbitraryPath(Offset.A2offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.A2offsety.lines(), n, score, iteration, measure);
						break;
					case "B2": // 5
						arbitraryPath(Offset.B2offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.B2offsety.lines(), n, score, iteration, measure);
						break;
					case "C3": // 6
						arbitraryPath(Offset.C3offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.C3offsety.lines(), n, score, iteration, measure);
						break;
					case "D3": // 7
						arbitraryPath(Offset.D3offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.D3offsety.lines(), n, score, iteration, measure);
						break;
					case "E3": // 8
						arbitraryPath(Offset.E3offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.E3offsety.lines(), n, score, iteration, measure);
						break;
					case "F3": // 9
						arbitraryPath(Offset.F3offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.F3offsety.lines(), n, score, iteration, measure);
						break;
					case "G3": // 10
						arbitraryPath(Offset.G3offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.G3offsety.lines(), n, score, iteration, measure);
						break;
					case "A3": // 11
						arbitraryPath(Offset.A3offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.A3offsety.lines(), n, score, iteration, measure);
						break;
					case "B3": // 12
						arbitraryPath(Offset.B3offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.B3offsety.lines(), n, score, iteration, measure);
						break;
					case "C4": // 13
						arbitraryPath(Offset.C4offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.C4offsety.lines(), n, score, iteration, measure);
						break;
					case "D4": // 14
						arbitraryPath(Offset.D4offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.D4offsety.lines(), n, score, iteration, measure);
						break;
					case "E4": // 15
						arbitraryPath(Offset.E4offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.E4offsety.lines(), n, score, iteration, measure);
						break;
					case "F4": // 16
						arbitraryPath(Offset.F4offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.F4offsety.lines(), n, score, iteration, measure);
						break;
					case "G4": // 17
						arbitraryPath(Offset.G4offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.G4offsety.lines(), n, score, iteration, measure);
						break;
					case "A4": // 18
						arbitraryPath(Offset.A4offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.A4offsety.lines(), n, score, iteration, measure);
						break;
					case "B4": // 19
						arbitraryPath(Offset.B4offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.B4offsety.lines(), n, score, iteration, measure);
						break;
					case "C5": // 20
						arbitraryPath(Offset.C5offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.C5offsety.lines(), n, score, iteration, measure);
						break;
					case "D5": // 21
						arbitraryPath(Offset.D5offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.D5offsety.lines(), n, score, iteration, measure);
						break;
					case "E5": // 22
						arbitraryPath(Offset.E5offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.E5offsety.lines(), n, score, iteration, measure);
						break;
					case "F5": // 23
						arbitraryPath(Offset.F5offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.F5offsety.lines(), n, score, iteration, measure);
						break;
					case "G5": // 24
						arbitraryPath(Offset.G5offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.G5offsety.lines(), n, score, iteration, measure);
						break;
					case "A5": // 25
						arbitraryPath(Offset.A5offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.A5offsety.lines(), n, score, iteration, measure);
						break;
					case "B5": // 26
						arbitraryPath(Offset.B5offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.B5offsety.lines(), n, score, iteration, measure);
						break;
					case "C6": // 27
						arbitraryPath(Offset.C6offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.C6offsety.lines(), n, score, iteration, measure);
						break;
					case "D6": // 28
						arbitraryPath(Offset.D6offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.D6offsety.lines(), n, score, iteration, measure);
						break;
					case "E6": // 29
						arbitraryPath(Offset.E6offsety.offset(), n.getNotations().getTechnical().getFret(),
								Offset.E6offsety.lines(), n, score, iteration, measure);
						break;
					default:
						break;
					}
				} else if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
					instr = "Drumset";
					pitchFret = "" + n.getInstrument().getId();

					switch (pitchFret) {
					// TODO: change offset and the call to Offset
					case "P1-I36": // 1
						arbitraryPath(DrumPiece.BASS_DRUM.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I39": // 2
						arbitraryPath(DrumPiece.ACOUSTIC_SNARE.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I43": // 3
						arbitraryPath(DrumPiece.CLOSED_HI_HAT.getOffset() , 0, 0, n, score, iteration, measure);
						break;
					case "P1-I47": // 4
						arbitraryPath(DrumPiece.OPEN_HI_HAT.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I52": // 5
						arbitraryPath(DrumPiece.RIDE_CYMBAL_1.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I54": // 6
						arbitraryPath(DrumPiece.RIDE_BELL.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I50": // 7
						arbitraryPath(DrumPiece.CRASH_CYMBAL_1.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I53": // 8
						arbitraryPath(DrumPiece.CHINESE_CYMBAL.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I48": // 9
						arbitraryPath(DrumPiece.LO_MID_TOM.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I46": // 10
						arbitraryPath(DrumPiece.LO_TOM.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I44": // 11
						arbitraryPath(DrumPiece.HIGH_FLOOR_TOM.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I42": // 12
						arbitraryPath(DrumPiece.LO_FLOOR_TOM.getOffset(), 0, 0, n, score, iteration, measure);
						break;
					case "P1-I45": // 13
						arbitraryPath(DrumPiece.PEDAL_HI_HAT.getOffset(), 0, 0, n, score, iteration, measure);
						break;
//					case "D4": // 14
//						arbitraryPath(Offset.D4offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "E4": // 15
//						arbitraryPath(Offset.E4offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "F4": // 16
//						arbitraryPath(Offset.F4offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "G4": // 17
//						arbitraryPath(Offset.G4offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "A4": // 18
//						arbitraryPath(Offset.A4offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "B4": // 19
//						arbitraryPath(Offset.B4offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "C5": // 20
//						arbitraryPath(Offset.C5offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "D5": // 21
//						arbitraryPath(Offset.D5offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "E5": // 22
//						arbitraryPath(Offset.E5offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "F5": // 23
//						arbitraryPath(Offset.F5offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "G5": // 24
//						arbitraryPath(Offset.G5offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "A5": // 25
//						arbitraryPath(Offset.A5offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "B5": // 26
//						arbitraryPath(Offset.B5offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "C6": // 27
//						arbitraryPath(Offset.C6offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "D6": // 28
//						arbitraryPath(Offset.D6offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
//					case "E6": // 29
//						arbitraryPath(Offset.E6offsety.offset(), 0, 0, n, score, iteration, measure);
//						break;
					default:
						break;
					}
				}
				iteration++;
			}
			measure++;
		}
		// TODO: why save as previreSheetMusic.fxml?
		//		doc.save("previewSheetMusic.jpg");
		renderer = new PDFRenderer(doc);
		// doc.close();
		
	}

	public void arbitraryPath(int offset, int fret, int lines, Note n, Score score, int iteration, int measure) throws IOException, TXMLException {
		if((lines < 0) && (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar"))) {
			pdfnotegen(userPath + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\NotationDown.png", offset, fret, -1 * (lines), n, score, iteration, measure); 
		}
		else if((lines >= 0) && (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar"))) {
			pdfnotegen(userPath + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\NotationUp.png", offset, fret, lines, n, score, iteration, measure);
		}
		else if((score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset"))) {
			pdfnotegen(userPath + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\DRUMX.png", offset, fret, lines, n, score, iteration, measure);
		}
	}

	// creates sheet lines on the page method
	public void pdfpagegen() throws IOException {
		// generates new sheet music on the page
		doc = new PDDocument();
		// generates a page;
		page = new PDPage();
		doc.addPage(page);
		pageImage = PDImageXObject.createFromFile(System.getProperty("user.home") + "\\git\\TAB2XML\\src\\main\\resources\\SHEET\\SblankGuitarSheet.png",doc);
		page.getCropBox().setLowerLeftX(0);
		page.getCropBox().setLowerLeftY(0);
		page.getCropBox().setUpperRightX(pageImage.getWidth());
		page.getCropBox().setUpperRightY(pageImage.getHeight());

		System.out.println("H: " + page.getCropBox().getHeight() + ",   W: " + page.getCropBox().getWidth());

		contentStream = new PDPageContentStream(doc, page);
		contentStream.drawImage(pageImage, 0, 0);
		contentStream.close();
		pageCounter++;
	}

	// inputs notes on sheet music
	// TODO: change to append, so that the sheet music isn't overwritten, but
	// instead, display the note on top of the sheet music
	public void pdfnotegen(String imagePath, int offsety, int fretnumber, int lines, Note n, Score score, int iteration, int measure) throws IOException, TXMLException {
		// arbitrary numbers for now
		// this if is for when the sheet staff isn't filled yet
		if(score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
			if(n.getChord() != null) {
				globalX -= 30;
			}
			if (lines == 0 && globalX >= 500) {
				globalX = 78;
				globalY -= 189;
			} 
			else if (globalX < 500) {
				System.out.println("GLOBAL Y : " + globalY);
				System.out.println("OFFSET   : " + (offsety));
				System.out.println("TEMP Y   : " + (globalY - (offsety)));
				
				pdflinegen(lines);
			} 
			else {
				globalX = 78;
				globalY -= 189;
				pdflinegen(lines);
			}

			pageImage = PDImageXObject.createFromFile(imagePath, doc);
			contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
			contentStream.drawImage(pageImage, globalX - 46, globalY - (offsety));
			contentStream.close();
			pdftabgen(userPath + "\\git\\TAB2XML\\src\\main\\resources\\Numbers\\" + fretnumber + ".png", offsety, n, score);
			globalX += 30;
			++totalNotes;
		}
		else if(score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
			if(totalNotes != 0) {
				globalY = 656;
			}
			if (lines == 0 && globalX >= 500) {
				globalX = 78;
				globalY -= 189;
			} 
			else if (globalX < 500) {
				System.out.println("GLOBAL Y : " + globalY);
				System.out.println("OFFSET   : " + (offsety));
				System.out.println("TEMP Y   : " + (globalY - (offsety)));

//				pdflinegen(lines);
			} 
			else {
				globalX = 78;
				globalY -= 189;
//				pdflinegen(lines);
			}

			pageImage = PDImageXObject.createFromFile(imagePath, doc);
			contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
			if((iteration < score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().size()) && 
					(score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().get(iteration).getChord() != null)) {
				globalX -= 30;
				contentStream.drawImage(pageImage, globalX - 30, globalY - (offsety));
			}
			else if((iteration >= score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().size()) || 
					(score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().get(iteration).getChord() == null)) {
				contentStream.drawImage(pageImage, globalX, globalY - (offsety));
			}
			contentStream.close();

			if(n.getNotehead() != null) {
				System.out.println(n.getNotehead().getType());

//				pdftabgen(userPath + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\" + n.getNotehead().getType() + ".png", offsety, n, score);
			}

			globalX += 30;
			++totalNotes;
		}
		System.out.println("----------------------");
	}

	public void pdflinegen(int lines) throws IOException {
		String linepath = System.getProperty("user.home") + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\Line.png"; // gets
		// png
		// of
		// line
		pageImage = PDImageXObject.createFromFile(linepath, doc);
		int innerY = globalY + 44;
		for (int i = 0; i < lines; i++) {
			contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
			contentStream.drawImage(pageImage, globalX, innerY);
			contentStream.close();
			innerY -= 6;
		}
	}

	public void pdftabgen(String tabpath, int number, Note n, Score score) throws IOException, TXMLException {
		if(score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
			int innerYT = globalY + 9; // the 60 is a placehoder at the top empty cell if the tab
			pageImage = PDImageXObject.createFromFile(tabpath, doc);
			contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);

			if (instr.equals("Guitar")) {
				contentStream.drawImage(pageImage, globalX + 5,
						innerYT - (7 * (n.getNotations().getTechnical().getString() - 1)));
			} else {

			}

			contentStream.close();
		}
		else if(score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
			int innerYT = globalY + 9; // the 60 is a placehoder at the top empty cell if the tab
			pageImage = PDImageXObject.createFromFile(System.getProperty("user.home") + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\Line.png", doc);
			contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);

			contentStream.drawImage(pageImage, globalX + 5,  innerYT - (globalY - (number)));

			contentStream.close();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------
	// Display PDF

	int numPages() {
		return doc.getPages().getCount();
	}

	public Image getImage(int pageNumber) {
		BufferedImage pageImage;
		try {
			PDPage pDPage = doc.getPage(pageNumber);

			pageImage = renderer.renderImage(pageNumber);
		} catch (IOException ex) {
			throw new UncheckedIOException("PDFRenderer throws IOException", ex);
		}
		return BufferedToFXimage(pageImage);
	}

	private Image BufferedToFXimage(BufferedImage pageImage) {
		WritableImage wr = null;
		if (pageImage != null) {
			wr = new WritableImage(pageImage.getWidth(), pageImage.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < pageImage.getWidth(); x++) {
				for (int y = 0; y < pageImage.getHeight(); y++) {
					pw.setArgb(x, y, pageImage.getRGB(x, y));
				}
			}
		}

		return new ImageView(wr).getImage();
	}
}
