package visualizer;


import converter.Score;
import custom_exceptions.TXMLException;
import javafx.scene.canvas.Canvas;
import models.Part;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Attributes;
import models.measure.barline.BarLine;
import models.measure.note.Note;
import models.part_list.PartList;
import org.jfugue.integration.MusicXmlParserListener;

import java.util.List;
/**
 * This Class is use for visualize musicXML file.
 *
 * @author Kuimou
 * */
public class visualizer {
	private ScorePartwise score;
	public visualizer(Score score) throws TXMLException {
		this.score = score.getModel();
	}
	/**
	 * This method is going to draw musicXML in visualExporter.
	 * visualExporter will create visualElements in both canvas and a PDF file.
	 *
	 * @param canvas
	 * canvas in the "preview page"
	 * */
	public void draw(Canvas canvas) {
		// PartList is the instrument information of each Part
		// Parts is collection of part
		drawPartList(score.getPartList());
		drawParts(score.getParts());
	}
	public void drawParts(List<Part> parts){
		//draw each part
		for (Part part:parts){
			drawPart(part);
		}
	}
	public void drawPart(Part part){
		//draw each measures
		// there will be additional metadata in the Part that we have to draw
		List<Measure> measures = part.getMeasures();
		//draw each measure
		for (Measure measure:measures){
			drawMeasure(measure);
		}
	}
	public void drawPartList(PartList partList){
		// some data to draw
	}

	public void drawMeasure(Measure measure){
		// attribute contain metadatas for whole measure
		drawAttributes(measure.getAttributes());
		// draw Notes
		drawNotes(measure.getNotesBeforeBackup());
		drawNotes(measure.getNotesAfterBackup());
		// draw Barlines
		drawBarlines(measure.getBarlines());
	}
	public void drawAttributes(Attributes attributes){

	}
	public void drawNotes(List<Note> notes){
		for (Note note:notes){
			drawNote(note);
		}
	}
	public void drawBarlines(List<BarLine> barLines){
		for (BarLine barLine:barLines){
			drawBarline(barLine);
		}
	}
	public void drawBarline(BarLine barLine){

	}
	public void drawNote(Note note){

	}

}
