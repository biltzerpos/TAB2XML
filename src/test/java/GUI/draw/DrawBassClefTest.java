package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;
import models.measure.attributes.Clef;

class DrawBassClefTest {
	private DrawBassClef dClef;
	private Pane p = new Pane();
	private Clef c = new Clef("TAB", 5);
	private final double x = 30.00;
	private final double y = 40.00;

	@BeforeEach
	public void setUp() throws Exception {
		this.dClef = new DrawBassClef(p, c, x, y);
	}

	@Test
	void testGetClef() {
		Clef c1 = this.dClef.getClef();
		assertEquals(c, c1);
	}

	@Test
	void testSetClef() {
		Clef expected = new Clef("New", 3);
		this.dClef.setClef(expected);
		Clef actual = this.dClef.getClef();
		assertEquals(expected, actual);
		assertNotEquals(x, actual);
	}

	@Test
	void testGetPane() {
		Pane pane = this.dClef.getPane();
		assertSame(p, pane);
	}

	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.dClef.setPane(pane);
		Pane actual = this.dClef.getPane();

		assertSame(pane, actual);
		assertNotSame(p, actual);

	}

	@Test
	void testGetX() {
		double x1 = this.dClef.getX();
		assertEquals(x, x1);
	}

	@Test
	void testSetX() {
		double expected = -3;
		this.dClef.setX(expected);
		double actual = this.dClef.getX();

		assertEquals(expected, actual);
		assertNotEquals(x, actual);
	}

	@Test
	void testGetY() {
		double y1 = this.dClef.getY();
		assertEquals(y, y1);
	}

	@Test
	void testSetY() {
		double expected = 200;
		this.dClef.setY(expected);
		double actual = this.dClef.getY();

		assertEquals(expected, actual);
		assertNotEquals(y, actual);
	}

}
