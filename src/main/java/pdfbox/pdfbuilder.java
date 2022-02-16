package pdfbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import models.Part;
import models.measure.Measure;
import models.measure.note.Note;
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
//………………………..
//code to add text content                                                (example on how to add text to the variable)
//………………………..
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
	//default builder
	//variables need to be static
	private static Preferences pref;
	private static Settings s;
	private static File file;
	private static PDDocument doc;
	private static PDPage page;
	private static PDImageXObject image;
	private static PDPageContentStream contentStream;
	private int pages;
	private final int notesPerPage = 50;
	private int maxNotesTotal;
	private int totalNotes;
	private int x;
	private int y;
	//initializer for the documen

	////Rough method idea
	//Once you press the preview or save button, you call this method with the PNG files so that it uses them to build it
	//Don't know how to hook it up to be viewable or interact with the buttons pressed on the GUI, will require help

	//Main method to call to create PDF
	public void sheetpdf(Part part) throws IOException {
		//get total notes to draw
		for(Measure measure : part.getMeasures()) {
			totalNotes += measure.getNotesAfterBackup().size();
		}

		//check if extra page is needed, if so, generate extra page
		while(maxNotesTotal <= totalNotes) {
			//gen next page of sheet music
			pdfpagegen();
		}

		//map notes onto sheet
		for(Measure m : part.getMeasures()) {
			for(Note n : m.getNotesAfterBackup()) {
				String pitchFret = "" + n.getPitch().getStep() + n.getNotations().getTechnical().getFret();
				switch (pitchFret) {
				case "E0":
					pdfnotegen(pitchFret, y);
					break;

				default:
					break;
				}
			}
		}


		doc.close();
	}

	public void arbitraryPath(String seqNote, int y) throws IOException {
		pdfnotegen("/TAB2XML_G14/src/main/resources/NOTES/" + seqNote + ".png", y);
	}
	
	//creates sheet lines on the page method
	public void pdfpagegen() throws IOException {
		// generates new sheet music on the page
		doc = new PDDocument();
		pref = Preferences.userRoot();
		//this command is for finding where to output the pdf;
		s.outputFolder = pref.get("outputFolder", System.getProperty("user.home"));
		page = new PDPage();
		doc.addPage(page);
		//NEED SPECIFIC PATH OF THE IMAGE TO INSERT
		image = PDImageXObject.createFromFile("/TAB2XML_G14/src/main/resources/SHEET/blankGuitarSheet.jpg", doc);
		contentStream = new PDPageContentStream(doc, page);
		contentStream.drawImage(image, 0, 989);
		contentStream.close();
	}

	//inputs notes on sheet music
	public void pdfnotegen(String imagePath, int localy) throws IOException {
		//arbitrary numbers for now
		x = 91;
		y = 885;
		for (int i = 0; i<maxNotesTotal; i++){
			if (x >= 591){
				image = PDImageXObject.createFromFile(imagePath, doc);
				contentStream = new PDPageContentStream(doc, doc.getPage(i));
				contentStream.drawImage(image, x, y + localy);
				contentStream.close();
				x = 91;
				//y is arbitrary, test later
				y -= 50;
			}
			else{
				//x is arbitrary, test later
				x += 50;
				image = PDImageXObject.createFromFile(imagePath, doc);
				contentStream = new PDPageContentStream(doc, doc.getPage(i));
				contentStream.drawImage(image, x, y + localy);
				contentStream.close();
			}
		}
		contentStream.close();
	}
}
