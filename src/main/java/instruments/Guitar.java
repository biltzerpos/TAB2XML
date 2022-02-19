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
				Clef c = extractClef(measure);
				drawMeasureCleft(c);
				x+=50;
			}
			if(x < 900)
			{
				drawMeasureNotes(measure);
			}
			else
			{
				x = 0;
				y += 100;
				drawMeasureNotes(measure);
			}
			DrawBar bar = new DrawBar(this.pane, x, y);
			bar.draw();

		}
	}
	
	//This method extracts a clef from a given measure
	public Clef extractClef(Measure m)
	{
		Clef clef = m.getAttributes().getClef();
		return clef;
	}
	//This method draws a given clef
	private void drawMeasureCleft(Clef c) 
	{
		DrawClef dc = new DrawClef(this.pane, c, x+5, y+15);
		dc.draw();
	}

	//this method draws the notes in a measure given measure m
	private void drawMeasureNotes(Measure measure ) 
	{
		List<Note> noteList = measure.getNotesBeforeBackup();
		for(int j = 0; j<noteList.size(); j++) 
		{
			Note note = noteList.get(j);
			//if a note has technical we use drawNote with techincal to draw fret values on string
			if(noteHasTechnical(note)) 
			{		
				drawNoteWithTechnical(note);
			}
			//if the guitar class has anything else we can add else and else-if arguments here
		}
	}
	
	//Given a Note with techinical attributes, this method draws it using drawNote method of Gui.draw. 
	private void drawNoteWithTechnical(Note note) {
		int fret = note.getNotations().getTechnical().getFret();
		int string = note.getNotations().getTechnical().getString();
		if (!noteHasChord(note)) 
		{
           	d.draw(x,y);
           	double positionY = getLineCoordinateY(d, string);
           	DrawNote noteDrawer = new DrawNote(this.pane, fret, x+25, positionY+3+y );
            x+=50;
            noteDrawer.drawFret();
		}
		else 
		{
			double positionY = getLineCoordinateY(d, string);
            DrawNote noteDrawer = new DrawNote(this.pane, fret, x-25, positionY+3+y );
            noteDrawer.drawFret();
		}
	}
	
	//gets the Y coordinate of specific group of music lines based on given string integer. 
	private double getLineCoordinateY(DrawMusicLines d, int string)
	{
		return  d.getMusicLineList().get(string-1).getStartY(string-1);

	}
	
	//returns true if the guitar note has chord element
	private Boolean noteHasChord(Note n) 
	{
		Boolean result = n.getChord() == null ? false : true;
		return result;
	}
	
	//return true if guitar note has technical attribute
	private Boolean noteHasTechnical(Note n) {
		Boolean result = n.getNotations().getTechnical() != null ? true : false;
		return result;
		
	}

	//This method plays the notes 
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

	public List<Measure> getMeasureList() {
		return measureList;
	}

	public void setMeasureList(List<Measure> measureList) {
		this.measureList = measureList;
	}




}
