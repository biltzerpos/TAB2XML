package GUI.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;

public class DrawMusicLinesTest {

	private DrawMusicLines dML;
	private Pane p = new Pane();
	private double x;
	private double y;
	private final double length = 50;
	private final int noteSpacing = 30; 
	private final int staffSpacing = 20; 

	@BeforeEach
	public void setUp() throws Exception {

		this.dML = new DrawMusicLines(this.p, this.noteSpacing, this.staffSpacing);
		x = 0;
		y = 0;
		dML.draw(x, y);
	}

	@Test
	void testGetMusicLineList() {
		// Since we create a new MLine everytime, the two lists are not be the same
		// instead we compare different thing like size and x and y of each MLine
		// to make sure we get the same lists
		List<MLine> expected = new ArrayList<MLine>();
		for (int i = 0; i < 6; i++) {
			MLine m = new MLine(p, x, y, x + this.length, y, i + 1);
			y += 10;
			expected.add(m);
		}
		List<MLine> actual = this.dML.getMusicLineList();

		assertEquals(expected.size(), actual.size());

		for (int i = 0; i < 6; i++) {
			MLine eLine = expected.get(i);
			double eStartX = eLine.getStartX();
			double eStartY = eLine.getStartY();
			double eEndX = eLine.getEndX();
			double eEndY = eLine.getEndY();
			int etag = eLine.getLineTag();

			MLine aLine = actual.get(i);
			double aStartX = aLine.getStartX();
			double aStartY = aLine.getStartY();
			double aEndX = aLine.getEndX();
			double aEndY = aLine.getEndY();
			int atag = aLine.getLineTag();

			// check if the lines from the same index have same startX and startY values
			assertEquals(eStartX, aStartX);
			assertEquals(eStartY, aStartY);
			// check to see lines from the same index have same end x and y
			assertEquals(eEndX, aEndX);
			assertEquals(eEndY, aEndY);
			// make sure both lines have the same tag
			assertEquals(etag, atag);
		}

	}

	// tests the getPane Method to make sure the method returns the expected pane
	@Test
	void testGetPane() {
		Pane pane = this.dML.getPane();
		assertSame(p, pane);
	}

	// tests the setPane method make sure method sets the pane correctly
	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.dML.setPane(pane);
		Pane actual = this.dML.getPane();

		assertSame(pane, actual);
		assertNotSame(p, actual);

	}

}
