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
	//initializer for the document
	//Assuming we will fit 4 music images per page, hence we will initialize with an array of images we plan to insert, open to modification if needed
	
    ////Rough method idea
	//Once you press the preview or save button, you call this method with the PNG files so that it uses them to build it
	//Don't know how to hook it up to be viewable or interact with the buttons pressed on the GUI, will require help


	public void pdfgen(File[] pngFiles) throws IOException {
		doc = new PDDocument();
		pref = Preferences.userRoot();
		BufferedImage usingImage;
		s.inputFolder = pref.get("inputFolder", System.getProperty("user.home"));
		s.outputFolder = pref.get("outputFolder", System.getProperty("user.home"));
		for (int i = 0; i<pngFiles.length; i++) {
			if (i == pngFiles.length) {
				if (i%8 == 0) {
					page = new PDPage();
					doc.addPage(page);
					image = PDImageXObject.createFromFileByExtension(pngFiles[i], doc);
					usingImage = ImageIO.read(pngFiles[i]);
					contentStream = new PDPageContentStream(doc, page);  
					contentStream.drawImage(image, usingImage.getWidth(), usingImage.getHeight());
					contentStream.close();  
					doc.save(s.outputFolder);
					doc.close();
					break;
				}
				else {
					image = PDImageXObject.createFromFileByExtension(pngFiles[i], doc);
					usingImage = ImageIO.read(pngFiles[i]);
					contentStream = new PDPageContentStream(doc, page);  
					contentStream.drawImage(image, usingImage.getWidth(), usingImage.getHeight());
					contentStream.close();  
					doc.save(s.outputFolder);
					doc.close();
					break;
				}
			}
			if (i == 0) {
				page = new PDPage();
				doc.addPage(page);
				image = PDImageXObject.createFromFileByExtension(pngFiles[i], doc);
				usingImage = ImageIO.read(pngFiles[i]);
				contentStream = new PDPageContentStream(doc, page);  
				contentStream.drawImage(image, usingImage.getWidth(), usingImage.getHeight());
			}
			if (i%8 == 0 && i != 0) {
				page = new PDPage();
				doc.addPage(page);
				image = PDImageXObject.createFromFileByExtension(pngFiles[i], doc);
				usingImage = ImageIO.read(pngFiles[i]);
				contentStream = new PDPageContentStream(doc, page);  
				contentStream.drawImage(image, usingImage.getWidth(), usingImage.getHeight());
			}
			else {
				image = PDImageXObject.createFromFileByExtension(pngFiles[i], doc);
				usingImage = ImageIO.read(pngFiles[i]);
				contentStream = new PDPageContentStream(doc, page);  
				contentStream.drawImage(image, usingImage.getWidth(), usingImage.getHeight());
			}
		}
		doc.save(pref.get("outputFolder", System.getProperty("user.home")));
		//call doc.close() when creation is reached its final stage. Unsure whether doc.close() is a final command.
	}
}
