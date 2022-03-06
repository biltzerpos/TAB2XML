package GUI.draw;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import models.measure.note.Note;

// This class draws note type under the music lines
public class DrawNoteType {

	@FXML
	private Pane pane;
	private double startX;
	private double startY;
	private final double HalfNoteLength = 15;
	private final double QuarterNoteLength = 30;
	private List<Note> noteList;
	private int counter = 3;

	public DrawNoteType() {

	}

	public DrawNoteType(Pane pane, List<Note> noteList, double startX, double startY) {
		super();
		this.pane = pane;
		this.startX = startX;
		this.startY = startY;
		this.noteList = noteList;
	}

	public void drawDuration() {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (note.getChord() == null) {
				startX += 25;

				String current = note.getType();
				String next = "";
				if (!(i < 0 || i >= noteList.size() - 1)) {
					next = noteList.get(i + 1).getType();
				}

				if (current == "half") {
					drawHalfNotes();
				} else if (current == "quarter") {
					drawQuarterNotes();
				} else if (current == "eighth") {
					drawEighthNotes();
					if (next == "eighth") {
						if (counter > 0) {
							drawBeam();
							counter--;
						} else {
							counter = 3;
						}
					}
				}
				startX += 25;

			}
		}
	}

	// shorter stick
	private void drawHalfNotes() {
		double y1 = startY + 37;
		Line l = new Line();
		l.setStartX(startX);
		l.setEndX(startX);
		l.setStartY(y1);
		l.setEndY(y1 - HalfNoteLength);
		pane.getChildren().add(l);

	}

	// longer stick
	private void drawQuarterNotes() {
		double y1 = startY + 37;
		Line l = new Line();
		l.setStartX(startX);
		l.setEndX(startX);
		l.setStartY(y1);
		l.setEndY(y1 - QuarterNoteLength);
		pane.getChildren().add(l);

	}

	// draw notes with beam
	private void drawEighthNotes() {
		double y1 = startY + 37;
		Line l = new Line();
		l.setStartX(startX);
		l.setEndX(startX);
		l.setStartY(y1);
		l.setEndY(y1 - QuarterNoteLength);
		pane.getChildren().add(l);

	}

	// draw the beam itself
	private void drawBeam() {
		double y1 = startY + 37;
		Line l = new Line();
		l.setStartX(startX);
		l.setEndX(startX + 50);
		l.setStartY(y1);
		l.setEndY(y1);
		l.setStrokeWidth(3);
		pane.getChildren().add(l);
	}

	// setters
	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

}
