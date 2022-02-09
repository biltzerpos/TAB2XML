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
import java.util.List;

public class visualizer {
	private ScorePartwise score;
	public visualizer(Score score) throws TXMLException {
		this.score = score.getModel();
	}
	public void draw(Canvas canvas)  {
		drawPartList(score.getPartList());
		drawParts(score.getParts());
	}
	public void drawParts(List<Part> parts){
		for (Part part:parts){
			drawPart(part);
		}
	}
	public void drawPart(Part part){
		List<Measure> measures = part.getMeasures();
		for (Measure measure:measures){
			drawMeasure(measure);
		}
	}
	public void drawPartList(PartList partList){

	}

	public void drawMeasure(Measure measure){
		drawAttributes(measure.getAttributes());
		drawNotes(measure.getNotesBeforeBackup());
		drawNotes(measure.getNotesAfterBackup());
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
