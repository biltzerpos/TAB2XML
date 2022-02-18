package instruments;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;
import models.measure.note.Notehead;
import GUI.draw.DrawBar;
import GUI.draw.DrawClef;
import GUI.draw.DrawMusicLines;
import GUI.draw.DrawNote;

public class Drumset {

	private ScorePartwise scorePartwise;
	@FXML private Pane pane;
	private List<Measure> measureList;
	private Clef clef;

	public Drumset(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.clef = this.scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
	}

	public void draw() {

		// Initialize x and y coordinates
		double x = 0;
		double y = 0;

		// Draw the initial music lines
		DrawMusicLines d = new DrawMusicLines(this.pane);
		d.draw(x,y);

		// Iterate through the list of measures
		for (Measure measure : measureList) {

			// Iterate through the notes in the current measure
			for (Note note : measure.getNotesBeforeBackup()) {
				
				Notehead symbol = note.getNotehead();
				
				if(symbol != null) {
					
					String drumnote=symbol.toString();
					String step = note.getUnpitched().getDisplayStep();
					int octave = note.getUnpitched().getDisplayOctave();
				
					DrawMusicLines d1 = new DrawMusicLines(this.pane);
					
				 if (note.getChord() == null) 
					{
						double positionY = d.getMusicLineList().get(octave-1).getStartY(octave-1);
						DrawNote noteDrawer = new DrawNote(this.pane, octave, x+25, positionY+3 );
						
						if (drumnote.equals("x")) {
							noteDrawer.drawX();
						}
						else {
							noteDrawer.drawO();
						}
						
						d1.draw(x,y);
						
						x+=50;
					}
					
				 else {
						double positionY = d.getMusicLineList().get(octave-1).getStartY(octave-1);
						DrawNote noteDrawer = new DrawNote(this.pane, octave, x-25, positionY+3 );
						
						if (drumnote.equals("x")) {
							noteDrawer.drawX();
						}
						else {
							noteDrawer.drawO();
						}
						d1.draw(x,y);
						x+=50;
					}

				/*
				 * Get the symbol of the note with:
				 *
				 * note.getNotehead();
				 *
				 * If it's "x", just draw an x,
				 * if not, then just a circle for now (or the letter "o" or something), until we figure out what else to do?
				 */

				/*
				 * If note.getChord() != null, then the note must be drawn in the same column as the last note.
				 */
			}

		}
	}}}
