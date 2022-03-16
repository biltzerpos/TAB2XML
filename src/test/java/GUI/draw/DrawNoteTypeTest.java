package GUI.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import converter.Score;
import instruments.Guitar;
import javafx.scene.layout.Pane;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.note.Note;
import utility.MusicXMLCreator;

public class DrawNoteTypeTest {

	private DrawNoteType dNT;
	private Pane p = new Pane();
	private final double x = 10.0;
	private final double y = 40.00;

	@BeforeEach
	public void setUp() throws Exception {

		String input = """
				|-----------0-----|-0---------------|
				|---------0---0---|-0---------------|
				|-------1-------1-|-1---------------|
				|-----2-----------|-2---------------|
				|---2-------------|-2---------------|
				|-0---------------|-0---------------|
				 """;
		Score score = new Score(input);
		MusicXMLCreator mxlc = new MusicXMLCreator(score);
		ScorePartwise scorePartwise = mxlc.getScorePartwise();
		Guitar g = new Guitar(scorePartwise, null, 50);
		Measure m1 = g.getMeasureList().get(0);
		List<Note> noteList = m1.getNotesBeforeBackup();
		Note n = noteList.get(0);

		this.dNT = new DrawNoteType(p, n , x, y);
	}

	// tests the getPane Method o make sure the method returns the expected pane
	@Test
	void testGetPane() {
		Pane pane = this.dNT.getPane();
		assertSame(p, pane);
	}

	// tests the setPane method to make sure method sets the pane correctly
	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.dNT.setPane(pane);
		Pane actual = this.dNT.getPane();

		assertSame(pane, actual);
		assertNotSame(p, actual);

	}

	// Tests the getStartX method to make sure that correct x coordinate of the bar
	// is returned
	@Test
	void testGetStartX() {
		double x1 = this.dNT.getStartX();
		assertEquals(x, x1);
	}

	// Test to make sure setStartX sets the x value correctly.
	@Test
	void testSetStartX() {
		double expected = 200;
		this.dNT.setStartX(expected);
		double actual = this.dNT.getStartX();

		assertEquals(expected, actual);
		assertNotEquals(x, actual);
	}

	// Tests the getStartY to make sure that correct y coordinate of the bar is
	// returned
	@Test
	void testGetStartY() {
		double y1 = this.dNT.getStartY();
		assertEquals(y, y1);
	}

	// Test to make sure setStartY sets the Y value correctly.
	@Test
	void testSetStartY() {
		double expected = 200;
		this.dNT.setStartY(expected);
		double actual = this.dNT.getStartY();

		assertEquals(expected, actual);
		assertNotEquals(y, actual);
	}

}
