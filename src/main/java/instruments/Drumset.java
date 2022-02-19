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
import GUI.draw.DrawDrumsetBar;
import GUI.draw.DrawDrumsetMusicLines;
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
		DrawDrumsetMusicLines d = new DrawDrumsetMusicLines(this.pane);
		d.draw(x,y);

		// Iterate through the list of measures
		for (Measure measure : measureList) {

			// Iterate through the notes in the current measure
			for (Note note : measure.getNotesBeforeBackup()) {

				Notehead symbol = note.getNotehead();

				String step = note.getUnpitched().getDisplayStep();
				int octave = note.getUnpitched().getDisplayOctave();

				/*
				 * TODO: Given the step and the octave, we need to figure out which line it is at.
				 * For example, octave==4 and step=="E" means draw the note at the bottom line (which is line 5).
				 *
				 * Also notes can be between lines, but we could probably just ignore that until we get the line part working.
				 */
				int line = 0;

				double positionY = d.getMusicLineList().get(line).getStartY();

				if (note.getChord() == null) {
					// Only draw music lines if not a chord.
					// This is because if it is a chord, the music lines from the last chord will be used.
					// Also draw the music lines before drawing the note so that the note appears on top.
					d.draw(x,y);

					DrawNote noteDrawer = new DrawNote(this.pane, x+25, positionY+3);

					// If note head exists and is an x, then draw "x", otherwise draw "o"
					if (symbol != null && symbol.getType().equals("x")) {
						noteDrawer.drawX();
					}
					else {
						noteDrawer.drawO();
					}

					x+=50;
				}
				else {
					DrawNote noteDrawer = new DrawNote(this.pane, x-25, positionY+3 );

					// If note head exists and is an x, then draw "x", otherwise draw "o"
					if (symbol != null && symbol.getType().equals("x")) {
						noteDrawer.drawX();
					}
					else {
						noteDrawer.drawO();
					}
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

			// Draw bar line after every measure
			DrawDrumsetBar bar = new DrawDrumsetBar(this.pane);
			bar.draw(x, y);
		}
	}

	public double getPositionYFromOctaveAndStep(int octave, String step) {
		return 0;
	}

}
