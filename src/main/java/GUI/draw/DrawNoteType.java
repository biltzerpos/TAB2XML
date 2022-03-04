package GUI.draw;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import models.measure.note.Note;

public class DrawNoteType {

	@FXML
	private Pane pane;
	private Note note;
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
		// this.note = note;
		this.startX = startX;
		this.startY = startY;
		// this.noteType = this.note.getType();
		this.noteList = noteList;
	}

	public void drawDuration() {
		// double x = startX;
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

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
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
