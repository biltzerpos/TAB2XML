package instruments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;
import models.measure.note.Notehead;

import GUI.draw.DrawClef;
import GUI.draw.DrawDrumsetBar;
import GUI.draw.DrawDrumsetMusicLines;
import GUI.draw.DrawNote;
import GUI.draw.DrawDrumsetNote;

public class Drumset {

	private ScorePartwise scorePartwise;
	@FXML private Pane pane;
	private List<Measure> measureList;
	private Clef clef;
	private HashMap<Measure, Double> xCoordinates;
	private HashMap<Measure, Double> yCoordinates;

	public Drumset(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
		this.clef = this.scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
		xCoordinates = new HashMap<>();
		yCoordinates = new HashMap<>();
		
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

				// Get the y-position based on the octave and step
				double positionY = d.getYPositionFromOctaveAndStep(octave, step);

				if (note.getChord() == null) {
					// Only draw music lines if not a chord.
					// This is because if it is a chord, the music lines from the last chord will be used.
					// Also draw the music lines before drawing the note so that the note appears on top.
					d.draw(x,y);

					DrawDrumsetNote noteDrawer = new DrawDrumsetNote(this.pane, note, x+25, positionY+3);
					noteDrawer.drawDrumClef1();
					noteDrawer.drawDrumClef2();

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
					DrawDrumsetNote noteDrawer = new DrawDrumsetNote(this.pane, note, x-25, positionY+3 );

					// If note head exists and is an x, then draw "x", otherwise draw "o"
					if (symbol != null && symbol.getType().equals("x")) {
						noteDrawer.drawX();
					}
					else {
						noteDrawer.drawO();
					}
				}
			}
			xCoordinates.put(measure, x);
			yCoordinates.put(measure, y);
			// Draw bar line after every measure
			DrawDrumsetBar bar = new DrawDrumsetBar(this.pane);
			bar.draw(x, y);
		}
	}

	public void highlightMeasureArea(Measure measure) {
		double x = 0;
		double y = 0;

		ObservableList children = pane.getChildren();
		ArrayList<Rectangle> removeRect = new ArrayList<Rectangle>();
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object instanceof Rectangle) {
				System.out.println("Rectangle Added to removal list.");
				removeRect.add((Rectangle) object);
			}
		}

		for (Iterator iterator = removeRect.iterator(); iterator.hasNext();) {
			Rectangle rect = (Rectangle) iterator.next();
			System.out.println("Rectangle Removed as part of reset.");
			pane.getChildren().remove(rect);
		}
		System.out.println("Reset Done");
		x = 0;
		y = 0;
		// now iterate again to highlight it in red
		for (int i = 0; i < measureList.size(); i++) {
			System.out.println("X Coordinates:" + getXCoordinatesForGivenMeasure(measureList.get(i)));
			System.out.println("Y Coordinates:" + getYCoordinatesForGivenMeasure(measureList.get(i)));
			double w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
			double yf = getYCoordinatesForGivenMeasure(measureList.get(i));
			if (yf > y) {
				System.out.println("Jumped to new Line");
				// we have moved on to new Line
				x = 0;
				w = getXCoordinatesForGivenMeasure(measureList.get(i)) - x;
				y = yf;
			}
			if (measure.equals(measureList.get(i))) {
				System.out.println("Four Rectangle values are:(" + x + "," + y + "," + w + "," + 50 + ")");
				System.out.println("X Coordinates to Highlight:" + getXCoordinatesForGivenMeasure(measure));
				System.out.println("Y Coordinates to Highlight:" + getYCoordinatesForGivenMeasure(measure));
				Rectangle rectangle = new Rectangle(x, yf, w, 50);
				rectangle.setFill(Color.TRANSPARENT);
				rectangle.setStyle("-fx-stroke: red;");
				pane.getChildren().add(rectangle);
			}
			x = getXCoordinatesForGivenMeasure(measureList.get(i));
			y = yf;
		}
	}

	// return X coordinates for given measure
	public double getXCoordinatesForGivenMeasure(Measure measure) {
		return xCoordinates.get(measure);
	}

	// return Y coordinates for given measure
	public double getYCoordinatesForGivenMeasure(Measure measure) {
		return yCoordinates.get(measure);
	}
	
	public double getPositionYFromOctaveAndStep(int octave, String step) {
		return 0;
	}

	// returns a list of measures
	public List<Measure> getMeasureList() {
		return measureList;
	}
	
}