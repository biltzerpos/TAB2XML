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

public class Guitar {

	private ScorePartwise scorePartwise;
	@FXML private Pane pane;
	private List<Measure> measureList;
	private Clef clef;
	private double x; 
	private double y;
	private DrawMusicLines d;
	


	public Guitar(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.clef = this.scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
		this.x = 0; 
		this.y = 0;
		this.d = new DrawMusicLines(this.pane);
	}

	/*
	 * This method is where the actual drawing of the guitar elements happen*/
	
	public void draw() 
	{
		//the initial x and y value
		d.draw(x,y);
		DrawClef dc = new DrawClef(this.pane, this.clef, x+5, y+15);
		dc.draw();
		x+=50;
		for(int i = 0;  i < measureList.size(); i++) 
		{
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();
			if(x < 900)
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
				DrawBar bar = new DrawBar(this.pane, x, y);
				bar.draw();
			}
			else
			{
				x = 0;
				y += 100;
				for(int j = 0; j<noteList.size(); j++) 
				{
					//DrawMusicLines d1 = new DrawMusicLines(this.pane, x, y);
					Note note = noteList.get(j);
					if(note.getNotations().getTechnical() != null) 
					{
						int fret = note.getNotations().getTechnical().getFret();
						int string = note.getNotations().getTechnical().getString();
						if (note.getChord() == null) 
						{
				           	d.draw(x,y);
				           	double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);
				           	DrawNote noteDrawer = new DrawNote(this.pane, fret, x+25, positionY+y+3 );
							
				            x+=50;
				            noteDrawer.drawFret();
						}
						else 
						{
							double positionY = d.getMusicLineList().get(string-1).getStartY(string-1);
				            DrawNote noteDrawer = new DrawNote(this.pane, fret, x-25, positionY+ y+3 );
				            noteDrawer.drawFret();
						}
					}
				}
				DrawBar bar = new DrawBar(this.pane, x, y);
				bar.draw();
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
