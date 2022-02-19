package instruments;

import java.util.List;

import GUI.draw.DrawBar;
import GUI.draw.DrawClef;
import GUI.draw.DrawMusicLines;
import GUI.draw.DrawNote;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;

public class Guitar 
{

	private ScorePartwise scorePartwise;
	@FXML private Pane pane;
	private List<Measure> measureList;
	private double x; 
	private double y;
	private DrawMusicLines d;
	


	public Guitar(ScorePartwise scorePartwise, Pane pane) 
	{
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.x = 0; 
		this.y = 0;
		this.d = new DrawMusicLines(this.pane);
	}

	/*
	 * This method is where the actual drawing of the guitar elements happen*/
	
	public void drawGuitar() 
	{
		for(int i = 0;  i < measureList.size(); i++) 
		{
			Measure measure = measureList.get(i);
			if(measure.getAttributes().getClef() != null) 
			{
				d.draw(x,y);
				drawMeasureCleft(measure);
				x+=50;
			}
			List<Note> noteList = measure.getNotesBeforeBackup();
			if(x < 900)
			{
				drawMeasureNotes(noteList);
			}
			else
			{
				x = 0;
				y += 100;
				drawMeasureNotes(noteList);
			}
			DrawBar bar = new DrawBar(this.pane, x, y);
			bar.draw();

		}
	}
	
	private void drawMeasureCleft(Measure m) 
	{
		Clef clef = m.getAttributes().getClef();
		DrawClef dc = new DrawClef(this.pane, clef, x+5, y+15);
		dc.draw();
	}

	private void drawMeasureNotes(List<Note> noteList) 
	{
		for(int j = 0; j<noteList.size(); j++) 
		{
			Note note = noteList.get(j);
			if(note.getNotations().getTechnical() != null) 
			{		
				int fret = note.getNotations().getTechnical().getFret();
				int string = note.getNotations().getTechnical().getString();
				if (note.getChord() == null) 
				{
		           	d.draw(x,y);
		           	double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);
		           	DrawNote noteDrawer = new DrawNote(this.pane, fret, x+25, positionY+3+y );
					
		            x+=50;
		            noteDrawer.drawFret();
				}
				else 
				{
					double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);
		            DrawNote noteDrawer = new DrawNote(this.pane, fret, x-25, positionY+3+y );
		            noteDrawer.drawFret();
				}
			}
		}
	}


	public void playNote() {
		for(int i = 0;  i < measureList.size(); i++) {
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();
			for(int j = 0; j<noteList.size(); j++) {
				String noteSteps = noteList.get(j).getPitch().getStep();
				System.out.println(noteSteps); // Temporary: print to console for now
			}
		}
	}





}
