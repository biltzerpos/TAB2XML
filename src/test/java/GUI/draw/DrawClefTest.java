package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;
import models.measure.attributes.Clef;

class DrawClefTest {
	private DrawClef dClef;
	private Pane p = new Pane();
	private Clef c = new Clef("TAB", 5);
	private final double x = 30.00;
	private final double y = 40.00;
	private String f; 

	@BeforeEach
	public void setUp() throws Exception {
		this.dClef = new DrawClef(p, c, x, y, f);
	}

	// Tests getClef() method of DrawClef class to make sure method returns the
	// correct
	// clef
	@Test
	void testGetClef() {
		Clef c1 = this.dClef.getClef();
		assertEquals(c, c1);
	}

	// Tests setClef method of the DrawClef class to make sure the method sets the
	// correct
	// values for clef
	@Test
	void testSetClef() {
		Clef expected = new Clef("New", 3);
		this.dClef.setClef(expected);
		Clef actual = this.dClef.getClef();
		assertEquals(expected, actual);
		assertNotEquals(x, actual);
	}

	// tests the getPane Method from the DrawClef class
	// to make sure the method returns the expected pane
	@Test
	void testGetPane() {
		Pane pane = this.dClef.getPane();
		assertSame(p, pane);
	}

	// tests the setPane method of the DrawClef class to
	// make sure method sets the pane correctly
	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.dClef.setPane(pane);
		Pane actual = this.dClef.getPane();

		assertSame(pane, actual);
		assertNotSame(p, actual);

	}

	// Tests the getX method of DrawClef class to
	// make sure that correct x coordinate of the clef is returned
	@Test
	void testGetX() {
		double x1 = this.dClef.getX();
		assertEquals(x, x1);
	}

	// Test to make sure setX sets the x value correctly.
	@Test
	void testSetX() {
		double expected = -3;
		this.dClef.setX(expected);
		double actual = this.dClef.getX();

		assertEquals(expected, actual);
		assertNotEquals(x, actual);
	}

	// Tests the getY method of DrawClef class to
	// make sure that correct y coordinate of the bar is returned
	@Test
	void testGetY() {
		double y1 = this.dClef.getY();
		assertEquals(y, y1);
	}

	// Test to make sure setY sets the Y value correctly.
	@Test
	void testSetY() {
		double expected = 200;
		this.dClef.setY(expected);
		double actual = this.dClef.getY();

		assertEquals(expected, actual);
		assertNotEquals(y, actual);
	}
}