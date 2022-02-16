package instruments;

import java.util.List;

import GUI.draw.DrawBar;
import GUI.draw.DrawClef;
import GUI.draw.DrawMusicLines;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;

public class Guitar {
	
	private ScorePartwise scorePartwise;
	@FXML private Pane pane;
	private List<Measure> measureList;
	private Clef clef;
	

	public Guitar(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.clef = this.scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
	}
	
	public void draw() {
		//draw guitar elements
		double x = 0;
		double y = 0;
		DrawMusicLines d = new DrawMusicLines(this.pane, x, y);
		d.draw();
		DrawClef dc = new DrawClef(this.pane, this.clef, x+5, y+15);
		dc.draw();
		x+=50;
		for(int i = 0;  i < measureList.size(); i++) {
			//x+=50;
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();
			for(int j = 0; j<noteList.size(); j++) {
				Note note = noteList.get(j);
				if(note.getNotations().getTechnical() != null) {
					int fret = note.getNotations().getTechnical().getFret();
					int string = note.getNotations().getTechnical().getString();
					if (note.getChord() == null) {
						
					}
				}
				
			}
			
			
			DrawBar bar = new DrawBar(this.pane, x, y);
			bar.draw();
			
		}
		
	}
	
	
	
	
	
}
