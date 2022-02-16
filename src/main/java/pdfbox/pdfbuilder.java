package pdfbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Array;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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
	//default builder
	//variables need to be static
	private static Preferences pref;
	private static Settings s;
	private static File file;
	private static PDDocument doc;
	private static PDPage page;
	private static PDImageXObject pageImage;
	private static PDPageContentStream contentStream;
	private int pages;
	private final int notesPerPage = 50;
	private int maxNotesTotal;
	private int totalNotes;
	private int x;
	private int y;
	private int pageCounter;
	private final int maxNotes = 20;

	private PDFRenderer renderer;
	//initializer for the documen

	////Rough method idea
	//Once you press the preview or save button, you call this method with the PNG files so that it uses them to build it
	//Don't know how to hook it up to be viewable or interact with the buttons pressed on the GUI, will require help

	//Offset calculated using first group as reference frame
	//First Global y = 901    
	//Second Global y = 726  (change in y 901 - 726 = 175) 
	//so on.....
	public enum Offset {
		EOoffsety (901 - 905),
		F1offsety (901 - 918),
		G3offsety (901 - 910),
		A0offsety (901 - 918),
		B2offsety (901 - 918),
		C3offsety (901 - 918),
		D0offsety (901 - 910),	
		E2offsety (901 - 913),
		F3offsety (901 - 918),
		G0offsety (901 - 922),
		A2offsety (901 - 925),
		B0offsety (901 - 918),
		C1offsety (901 - 918),
		D3offsety (901 - 918),
		E0Hoffsety (901 - 918),
		F1Hoffsety (901 - 921),
		G3Hoffsety (901 - 926),
		A5Hoffsety (901 - 930),
		B7Hoffsety (901 - 933),
		C8Hoffsety (901 - 937),
		D10Hoffsety (901 - 940);

		protected final Integer offset;

		Offset(int i) {
			this.offset = i;
		}

		public int offset() {
			return this.offset;
		}
	}

	//Main method to call to create PDF
	public void sheetpdf(Part part) throws IOException {
		//get total notes to draw
		for(Measure measure : part.getMeasures()) {
			totalNotes += measure.getNotesBeforeBackup().size();
		}

		//check if extra page is needed, if so, generate extra page
		while(maxNotesTotal <= totalNotes) {
			//gen next page of sheet music
			pdfpagegen();
			++maxNotesTotal;
		}
		//map notes onto sheet
		for(Measure m : part.getMeasures()) {
			for(Note n : m.getNotesBeforeBackup()) {
				String pitchFret = "" + n.getPitch().getStep() + n.getNotations().getTechnical().getFret();
				switch (pitchFret) {
				case "E0": //1
					arbitraryPath("E0", Offset.E0Hoffsety.offset());
					break;
				case "F1": //2
					arbitraryPath("F1", Offset.F1offsety.offset());
					break;
				case "G3": //3
					arbitraryPath("G3", Offset.G3offsety.offset());
					break;
				case "A0": //4
					arbitraryPath("A0", Offset.A0offsety.offset());
					break;
				case "B2": //5
					arbitraryPath("B2", Offset.B2offsety.offset());
					break;
				case "C3": //6
					arbitraryPath("C3", Offset.C3offsety.offset());
					break;
				case "D0": //7
					arbitraryPath("D0", Offset.D0offsety.offset());
					break;
				case "E2": //8
					arbitraryPath("E2", Offset.E2offsety.offset());
					break;
				case "F3": //9
					arbitraryPath("F3", Offset.F3offsety.offset());
					break;
				case "G0": //10
					arbitraryPath("G0", Offset.G0offsety.offset());
					break;
				case "A2": //11
					arbitraryPath("A2", Offset.A2offsety.offset());
					break;
				case "B0": //12
					arbitraryPath("B0", Offset.B0offsety.offset());
					break;
				case "C1": //13
					arbitraryPath("C1", Offset.C1offsety.offset());
					break;
				case "D3": //14
					arbitraryPath("D3", Offset.D3offsety.offset());
					break;
				case "A5": //15
					arbitraryPath("A5H", Offset.A5Hoffsety.offset());
					break;
				case "B7": //16
					arbitraryPath("B7H", Offset.B7Hoffsety.offset());
					break;
				case "C8": //17
					arbitraryPath("C8H", Offset.C8Hoffsety.offset());
					break;
				case "D10": //18
					arbitraryPath("D10H", Offset.D10Hoffsety.offset());
					break;
				default:
					break;
				}
			}
		}
		doc.save("previewSheetMusic.fxml");
		renderer = new PDFRenderer(doc);
//		doc.close();


		//getting the image to display
	}

	public void arbitraryPath(String seqNote, int i) throws IOException {
		pdfnotegen("\\TAB2XML_G14\\src\\main\\resources\\NOTES\\" + seqNote + ".png", i);
	}

	//creates sheet lines on the page method
	public void pdfpagegen() throws IOException {
		// generates new sheet music on the page
		doc = new PDDocument();
		//this command is for finding where to output the pdf;
		page = new PDPage();
		doc.addPage(page);
		pageImage = PDImageXObject.createFromFile("C:\\Users\\ahmed\\git\\TAB2XML\\src\\main\\resources\\SHEET\\blankGuitarSheet.jpg", doc);
		contentStream = new PDPageContentStream(doc, page);
		contentStream.drawImage(pageImage, 0, 989);
		contentStream.close();
		++pageCounter;
	}

	//inputs notes on sheet music
	public void pdfnotegen(String imagePath, int offsety) throws IOException {
		//arbitrary numbers for now
		x = 91;
		y = 885;
		int j = 0;
		for (int i = 0; i<maxNotesTotal; i++){
			if (x >= 591){
				pageImage = PDImageXObject.createFromFile(imagePath, doc);
				contentStream = new PDPageContentStream(doc, doc.getPage(j));
				contentStream.drawImage(pageImage, x, y - offsety);
				contentStream.close();
				x = 91;
				//y is arbitrary, test later
				y -= 50;
			}
			else{
				//x is arbitrary, test later
				x += 50;
				pageImage = PDImageXObject.createFromFile(imagePath, doc);
				contentStream = new PDPageContentStream(doc, doc.getPage(j));
				contentStream.drawImage(pageImage, x, y - offsety);
				contentStream.close();
			}
		}
		contentStream.close();
	}


	//---------------------------------------------------------------------------------------------------------------
	//Display PDF

	int numPages() {
		return doc.getPages().getCount();
	}   

	public Image getImage(int pageNumber) {
		BufferedImage pageImage;
		try {
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