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

	//JAVA doc object, has an array of pages which are of type PDPage 
	public PDDocument doc;
	private static PDPage page;

	//needed for contentStream so it can put image on page
	private static PDImageXObject pageImage;

	//used to insert image onto page
	private static PDPageContentStream contentStream;
	private String userPath;
	private final int notesPerPage = 50;
	private int maxNotesTotal = notesPerPage;
	private int totalNotes;
	private int globalX = 15;
	private int globalY = 425;
	private int pageCounter;

	private PDFRenderer renderer;
	//initializer for the document

	////Rough method idea
	//Once you press the preview or save button, you call this method with the PNG files so that it uses them to build it
	//Don't know how to hook it up to be viewable or interact with the buttons pressed on the GUI, will require help

	//Offset calculated using first group as reference frame
	//First Global y = 901    
	//Second Global y = 726  (change in y 901 - 726 = 175) 
	//so on.....
	public enum Offset { 

		//TODO: NEW offset x 198
		//                 y 153
		E2offsety (901 - 905 - 198, 7),	//1
		F2offsety (901 - 918 - 198, 6),	//2
		G2offsety (901 - 910 - 198, 6),	//3
		A2offsety (901 - 918 - 198, 5),	//4
		B2offsety (901 - 918 - 198, 5),	//5
		
		C3offsety (901 - 918 - 198, 4),	//6
		D3offsety (901 - 910 - 198, 4),	//7
		E3offsety (901 - 913 - 198, 3),	//8
		F3offsety (901 - 918 - 198, 3),	//9
		G3offsety (901 - 922 - 198, 2),	//10
		A3offsety (901 - 925 - 198, 2),	//11
		B3offsety (901 - 918 - 198, 1),	//12
		
		C4offsety (901 - 918 - 198, 1),	//13
		D4offsety (901 - 918 - 198, 0),	//14
		E4offsety (901 - 918 - 198, 0),	//15
		F4offsety (901 - 921 - 198, 0),	//16
		G4offsety (901 - 926 - 198, 0),	//17
		A4offsety (901 - 930 - 198, 0),	//18
		B4offsety (901 - 933 - 198, 0),	//19
		
		C5offsety (901 - 937 - 198, 0),	//20
		D5offsety (901 - 940 - 198, 0),	//21
		E5offsety (901 - 943 - 198, 0),	//22
		F5offsety (901 - 946 - 198, 0),	//23
		G5offsety (901 - 949 - 198, 0),	//24
		A5offsety (901 - 951 - 198, 1),	//25
		B5offsety (901 - 954 - 198, 1),	//26
		
		C6offsety (901 - 957 - 198, 2),	//27
		D6offsety (901 - 960 - 198, 2),	//28
		E6offsety (901 - 963 - 198, 23);	//29

		protected final Integer offset;
		protected final Integer lines;

		Offset(int i, int j) {
			this.offset = i;
			this.lines = j;
		}

		public int offset() {
			return this.offset;
		}
		
		public int lines() {
			return this.lines;
		}
	}

	//Main method to call to create PDF
	public void sheetpdf(Score score) throws IOException, TXMLException {
		//set userPath
		//System.getProperty("user.home") returns a String of the user Path (for example, "C:\Users\ian")
		userPath = System.getProperty("user.home");

		Part part = score.getModel().getParts().get(0);
		
		//get total notes to draw
		for(Measure measure : part.getMeasures()) {
			totalNotes += measure.getNotesBeforeBackup().size();
		}

		pdfpagegen();
		//check if extra page is needed, if so, generate extra page
		while(maxNotesTotal <= totalNotes) {
			//gen next page of sheet music
			pdfpagegen();
		    maxNotesTotal += 50;
		}

		int mLength = part.getMeasures().size();
		System.out.println(mLength);
		//map notes onto sheet
		for(Measure m : part.getMeasures()) {
			for(Note n : m.getNotesBeforeBackup()) {
				String pitchFret = "" + n.getPitch().getStep() + n.getPitch().getOctave();
				
				
				System.out.println(pitchFret);
				System.out.println(score.getModel().getPartList().getScoreParts().get(0).getPartName());
				
				
				switch (pitchFret) {
				//TODO: change offset and the call to Offset
				case "E2": //1
					arbitraryPath(Offset.E2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E2offsety.lines());
					break;
				case "F2": //2
					arbitraryPath(Offset.F2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F2offsety.lines());
					break;
				case "G2": //3
					arbitraryPath(Offset.G2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G2offsety.lines());
					break;
				case "A2": //4
					arbitraryPath(Offset.A2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A2offsety.lines());
					break;
				case "B2": //5
					arbitraryPath(Offset.B2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B2offsety.lines());
					break;
				case "C3": //6
					arbitraryPath(Offset.C3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C3offsety.lines());
					break;
				case "D3": //7
					arbitraryPath(Offset.D3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D3offsety.lines());
					break;
				case "E3": //8
					arbitraryPath(Offset.E3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E3offsety.lines());
					break;
				case "F3": //9
					arbitraryPath(Offset.F3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F3offsety.lines());
					break;
				case "G3": //10
					arbitraryPath(Offset.G3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G3offsety.lines());
					break;
				case "A3": //11
					arbitraryPath(Offset.A3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A3offsety.lines());
					break;
				case "B3": //12
					arbitraryPath(Offset.B3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B3offsety.lines());
					break;
				case "C4": //13
					arbitraryPath(Offset.C4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C4offsety.lines());
					break;
				case "D4": //14
					arbitraryPath(Offset.D4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D4offsety.lines());
					break;
				case "E4": //15
					arbitraryPath(Offset.E4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E4offsety.lines());
					break;
				case "F4": //16
					arbitraryPath(Offset.F4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F4offsety.lines());
					break;
				case "G4": //17
					arbitraryPath(Offset.G4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G4offsety.lines());
					break;
				case "A4": //18
					arbitraryPath(Offset.A4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A4offsety.lines());
					break;
				case "B4": //19
					arbitraryPath(Offset.B4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B4offsety.lines());
					break;
				case "C5": //20
					arbitraryPath(Offset.C5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C5offsety.lines());
					break;
				case "D5": //21
					arbitraryPath(Offset.D5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D5offsety.lines());
					break;
				case "E5": //22
					arbitraryPath(Offset.E5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E5offsety.lines());
					break;
				case "F5": //23
					arbitraryPath(Offset.F5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F5offsety.lines());
					break;
				case "G5": //24
					arbitraryPath(Offset.G5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G5offsety.lines());
					break;
				case "A5": //25
					arbitraryPath(Offset.A5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A5offsety.lines());
					break;
				case "B5": //26
					arbitraryPath(Offset.B5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B5offsety.lines());
					break;
				case "C6": //27
					arbitraryPath(Offset.C6offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C6offsety.lines());
					break;
				case "D6": //28
					arbitraryPath(Offset.D6offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D6offsety.lines());
					break;
				case "E6": //29
					arbitraryPath(Offset.E6offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E6offsety.lines());
					break;
				default:
					break;
				}
			}
		}
		//TODO: why save as previreSheetMusic.fxml?
		doc.save("previewSheetMusic.jpg");
		renderer = new PDFRenderer(doc);
		//		doc.close();

	}

	public void arbitraryPath(int offset, int fret, int lines) throws IOException {
		pdfnotegen(userPath + "\\git\\TAB2XML\\src\\main\\resources\\NOTES\\NotationUp.png", offset);
	}

	//creates sheet lines on the page method
	public void pdfpagegen() throws IOException {
		// generates new sheet music on the page
		doc = new PDDocument();
		//generates a page;
		page = new PDPage();
		doc.addPage(page);
		pageImage = PDImageXObject.createFromFile(System.getProperty("user.home") + "\\git\\TAB2XML\\src\\main\\resources\\SHEET\\SblankGuitarSheet.png", doc);
		page.getCropBox().setLowerLeftX(0);
		page.getCropBox().setLowerLeftY(0);
		page.getCropBox().setUpperRightX(pageImage.getWidth());
		page.getCropBox().setUpperRightY(pageImage.getHeight());
		//		System.out.println("Page" + page.getCropBox().getHeight() + "and" + page.getCropBox().getWidth());
		contentStream = new PDPageContentStream(doc, page);
		contentStream.drawImage(pageImage, 0, 0);
		contentStream.close();
		pageCounter++;
	}

	//inputs notes on sheet music
	//TODO: change to append, so that the sheet music isn't overwritten, but instead, display the note on top of the sheet music
	public void pdfnotegen(String imagePath, int offsety) throws IOException {
		//arbitrary numbers for now
		//this if is for when the sheet staff isn't filled yet
		if (globalX < 500){
			pageImage = PDImageXObject.createFromFile(imagePath, doc);
			contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
			contentStream.drawImage(pageImage, globalX, globalY - offsety);
			System.out.println(globalX +","+ globalY);
			contentStream.close();
			globalX += 50;
			//y is arbitrary, test later
			++totalNotes;
		}
		// this else is for when it reaches the end of the staff, to then translate it into the next staff
		else {
			globalX = 15;
			//y is the best i could chose
			globalY -= 140;
			//x is arbitrary, test later
			pageImage = PDImageXObject.createFromFile(imagePath, doc);
			contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
			contentStream.drawImage(pageImage, globalX, globalY - offsety);
			System.out.println(globalX +","+ globalY);
			contentStream.close();
			globalX += 50;
			++totalNotes;
		}
	}



	//---------------------------------------------------------------------------------------------------------------
	//Display PDF

	int numPages() {
		return doc.getPages().getCount();
	}   

	public Image getImage(int pageNumber) {
		BufferedImage pageImage;
		try {
			PDPage pDPage = doc.getPage(pageNumber);

			pageImage = renderer.renderImage(pageNumber);

			System.out.println("Page" + page.getCropBox().getHeight() + "and" + page.getCropBox().getWidth());
			System.out.println("Buffered" + pageImage.getHeight() + "and" + pageImage.getWidth());

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