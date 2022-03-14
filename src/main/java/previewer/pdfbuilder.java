package previewer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import converter.Score;
import custom_exceptions.TXMLException;
import models.Part;
import models.measure.Measure;
import models.measure.note.Note;
import utility.Settings;

// pdf file creation details
// doc.save(file path)                                               (path = "C/document/etc/...")
// doc.close                                                         (always evoke this command when doen with document
// doc.addpage(page)                                                 (adds a page)
// File file = new File(String "path of the document")               (creates a new file)
// PDDocument.load(file);                                            (pdf loaded as a new document)
// doc.getNumberOfPages()                                            (returns int number)
// doc.removepage(int number)                                        (remove page based on index system)
//
// Miscelaneous pdf details control commands
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
//
// Content file to insert into pdf creation
// PDPageContentStream contentStream = new PDPageContentStream(doc, page);(creates a variable to then add to the document)
//
// contentStream.beginText();
//
// code to add text content                                                (example on how to add text to the variable)
//
// contentStream.endText();
//
// contentStream.setFont(PDType1Font.some_font,int font_size );            (self-explanatory);
// contentStream.showText(String text);                                    (used to insert the variable into the pdf)
// contentStream.endText();                                                (end insertion)
// contentStream.setLeading(14.5f);                                        (not really sure)
// contentStream.newLineAtOffset(25, 725);                                 (same as above)
// contentStream.newLine();                                                (moves to a new line)
//
// Image Insertion
// PDImageXObject pdImage = PDImageXObject.createFromFile("C:/logo.png", doc);    (create image variable from path, and then indicate which doc to add it to)
// PDPageContentStream contentStream = new PDPageContentStream(doc, page);        (have to prepare content stream for next step)
// contentstream.drawImage(pdImage, 70, 250);                                     (number are for example, int based, guessing its for dimensions or the coordinates on the page)
// contentstream.close();                                                         (remember to close after being done)
// doc.save(file path in the directory)                                           (saves to apply image)
// doc.close()                                                                    (closes document)

/*---------------------------------------------------------------------------------------------------------------------------------------
Scans tab like a book:
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

To get the examined data, score.getModel(), this returns a ScorePartwise object with the data, series of notes can be used as follows:
// Draw notes on PDF
for(Measue i : score.getModel().getPart().getMeasure()) {
  for(Note n : i.getNote()) {
    // inner loop goes through notes of the ith measure and draws notes on pdf
  }
}

// Create the corresponding amount of pages
counter pages
final notesPERpage
counter maxNotesHoldable

	while(maxNotesHOlder < notes.getSize){
  		gen pdf;
  	maxNotesHOlder += notesPERpage
	}
------------------------------------------------------------------------------------------------------------------------------------------
 */

public class pdfbuilder {
	// JAVA doc object, has an array of pages which are of type PDPage
	public PDDocument doc;
	private static PDPage page;

	// needed for contentStream so it can put image on page
	private static PDImageXObject pageImage;

	// used to insert image onto page
	private static PDPageContentStream contentStream;
	private int totalNotes = 0;
	private int globalX = 78;
	private int globalY = 632;
	private int globalGap = 30;
	private int currentPage = -1;
	private String instr = "";

	// image paths
	private String nUp = "";
	private String nDown = "";
	private String drumX = "";
	private String line = "";
	private String o = "";
	private String x = "";
	private String sheet = "";

	public void setDirectories() {
		String home = System.getProperty("user.home");

		nUp = home + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "NOTES" + File.separator + "NotationUp.png";
		nDown = home + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "NOTES" + File.separator + "NotationDown.png";
		drumX = home + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "NOTES" + File.separator + "DRUMX.png";
		line = home + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "NOTES" + File.separator + "Line.png";
		o = home + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "NOTES" + File.separator + "o.png";
		x = home + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "NOTES" + File.separator + "x.png";
		sheet = home + File.separator + "git" + File.separator + "TAB2XML" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "SHEET" + File.separator + "SblankGuitarSheet.png";

		// TODO remove print
		System.out.println("OS: " + System.getProperty("os.name") + " | USER HOME: " + System.getProperty("user.home"));
		System.out.println("example directory: " + nUp);
	}

	public void sheetpdf(Score score) throws IOException, TXMLException {
		setDirectories();
		Part part = score.getModel().getParts().get(0);

		// get total notes to draw
		for (Measure measure : part.getMeasures()) {
			totalNotes += measure.getNotesBeforeBackup().size();
		}

		doc = new PDDocument();
		pdfpagegen();


		// map notes onto sheet
		int iteration = 0;
		int measure = 0;
		for (Measure m : part.getMeasures()) {
			for (Note n : m.getNotesBeforeBackup()) {
				String pitchFret = "";
				NoteIdentifier noteIdentifier = new NoteIdentifier();

				if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
					instr = "Guitar";
					pitchFret = "" + n.getPitch().getStep() + n.getPitch().getOctave();
				} 
				else if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
					instr = "Drumset";
					pitchFret = "" + n.getInstrument().getId();
				}
				// TODO remove print
				System.out.println(pitchFret);

				noteIdentifier.identifyNote(instr, pitchFret, n, score, iteration, measure, this);
				iteration++;
			}
			measure++;
		}

		doc.save("SheetMusic.pdf");
		doc.close();

		if(System.getProperty("os.name").contains("Mac") || System.getProperty("os.name").contains("Linux") ) {
			ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "sensible-browser SheetMusic.pdf");
			processBuilder.start();
		}
		else {
			Desktop.getDesktop().open(new File("SheetMusic.pdf"));
		}

		// doc.close();
	}

	public void arbitraryPath(int offset, int fret, int lines, Note n, Score score, int iteration, int measure) throws IOException, TXMLException {
		if ((lines < 0) && (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar"))) {
			pdfnotegen(nDown, offset, fret, lines, n, score, iteration, measure);
		} 
		else if ((lines >= 0) && (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar"))) {
			pdfnotegen(nUp, offset, fret, lines, n, score, iteration, measure);
		} 
		else if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
			pdfnotegen(drumX, offset, fret, lines, n, score, iteration, measure);
		}
	}

	public void pdfpagegen() throws IOException {
		page = new PDPage();
		doc.addPage(page);
		pageImage = PDImageXObject.createFromFile(sheet, doc);
		page.getCropBox().setLowerLeftX(0);
		page.getCropBox().setLowerLeftY(0);
		page.getCropBox().setUpperRightX(pageImage.getWidth());
		page.getCropBox().setUpperRightY(pageImage.getHeight());

		// TODO remove print
		System.out.println("H: " + page.getCropBox().getHeight() + ",   W: " + page.getCropBox().getWidth());

		contentStream = new PDPageContentStream(doc, page);
		contentStream.drawImage(pageImage, 0, 0);
		contentStream.close();
		currentPage++;
	}

	public void pdfnotegen(String imagePath, int offsety, int fretnumber, int lines, Note n, Score score, int iteration, int measure) throws IOException, TXMLException {
		// check if new page needed
		if (globalX >= 500 && globalY <= 100) {
			globalX = 78;
			globalY = 632;
			pdfpagegen();
		}
		if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
			if (n.getChord() != null) {
				globalX -= globalGap;
				// currentNOPG--;
			}
			if (lines == 0 && globalX >= 500) {
				globalX = 78;
				globalY -= 189;
			} 
			else if (globalX < 500) {
				// TODO remove print
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
			contentStream = new PDPageContentStream(doc, doc.getPage(currentPage), PDPageContentStream.AppendMode.APPEND, false);
			contentStream.drawImage(pageImage, globalX - 46, globalY - (offsety));
			contentStream.close();
			pdftabgen(getNumberPath(fretnumber), offsety, n, score);
			globalX += globalGap;
		} 
		else if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
			if (totalNotes == 0) {
				globalY = 656;
			}
			if (lines == 0 && globalX >= 500) {
				globalX = 78;
				globalY -= 189;
			} 
			else if (globalX < 500) {
				// TODO remove print
				System.out.println("GLOBAL Y : " + globalY);
				System.out.println("OFFSET   : " + (offsety));
				System.out.println("TEMP Y   : " + (globalY - (offsety)));
			} 
			else {
				globalX = 78;
				globalY -= 189;
			}

			pageImage = PDImageXObject.createFromFile(imagePath, doc);
			contentStream = new PDPageContentStream(doc, doc.getPage(currentPage), PDPageContentStream.AppendMode.APPEND, false);
			if ((iteration < score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().size()) && (score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().get(iteration).getChord() != null)) {
				globalX -= 30;
				contentStream.drawImage(pageImage, globalX - 30, globalY - (offsety));
			} 
			else if ((iteration >= score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().size()) || (score.getModel().getParts().get(0).getMeasures().get(measure).getNotesBeforeBackup().get(iteration).getChord() == null)) {
				contentStream.drawImage(pageImage, globalX, globalY - (offsety));
			}
			contentStream.close();

			if (n.getNotehead() != null) {
				// TODO remove print
				System.out.println(n.getNotehead().getType());

				//				pdftabgen(userPath + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\" + n.getNotehead().getType() + ".png", offsety, n, score);
			}

			globalX += globalGap;
		}
		// TODO remove print
		System.out.println("----------------------");
	}

	public String getNumberPath(int number) {
		return System.getProperty("user.home") + File.separator + "git" + File.separator + "TAB2XML" + File.separator  + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Numbers" + File.separator + number + ".png";
	}

	public void pdflinegen(int lines) throws IOException {
		pageImage = PDImageXObject.createFromFile(line, doc);
		if (lines > 0) {
			// TODO remove print 
			System.out.println(globalY);
			int innerY = globalY + 44;
			for (int i = 0; i < lines; i++) {
				contentStream = new PDPageContentStream(doc, doc.getPage(currentPage), PDPageContentStream.AppendMode.APPEND, false);
				contentStream.drawImage(pageImage, globalX, innerY);
				contentStream.close();
				innerY -= 6;
			}
		}
		else if (lines < 0) {
			// TODO remove print 
			System.out.println(globalY);
			int innerY = globalY + 88;
			for (int i = 0; i > lines; i--) {
				contentStream = new PDPageContentStream(doc, doc.getPage(currentPage), PDPageContentStream.AppendMode.APPEND, false);
				contentStream.drawImage(pageImage, globalX, innerY);
				contentStream.close();
				innerY += 6;
			}
		}
	}

	public void pdftabgen(String tabpath, int number, Note n, Score score) throws IOException, TXMLException {
		if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Guitar")) {
			int innerYT = globalY + 9;
			pageImage = PDImageXObject.createFromFile(tabpath, doc);
			contentStream = new PDPageContentStream(doc, doc.getPage(currentPage), PDPageContentStream.AppendMode.APPEND, false);

			if (instr.equals("Guitar")) {
				contentStream.drawImage(pageImage, globalX + 5,
				innerYT - (7 * (n.getNotations().getTechnical().getString() - 1)));
			} else {

			}

			contentStream.close();
		} else if (score.getModel().getPartList().getScoreParts().get(0).getPartName().equals("Drumset")) {
			int innerYT = globalY + 9; // the 60 is a placehoder at the top empty cell if the tab
			pageImage = PDImageXObject.createFromFile(line, doc);
			contentStream = new PDPageContentStream(doc, doc.getPage(currentPage), PDPageContentStream.AppendMode.APPEND, false);

			contentStream.drawImage(pageImage, globalX + 5, innerYT - (globalY - (number)));

			contentStream.close();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------
	// Display PDF

	int numPages() {
		return doc.getPages().getCount();
	}

	// TODO: connect this method to the GUI for the user to change spacing betwen notes
	public void changeXSpacing (int x) {
		if (x <= 0) {
			throw new IllegalArgumentException("Cannot reduce gap to less than 1.");
		}
		else {
			globalGap = x;
		}
	}
}
