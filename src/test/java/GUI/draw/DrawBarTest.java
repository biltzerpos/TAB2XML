package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.layout.Pane;

class DrawBarTest {

	private DrawBar bar;
	private Pane p = new Pane();
	private final double x = 30.00;
	private final double y = 40.00;
	private final double len = 10; 

	@BeforeEach
	public void setUp() throws Exception {

		this.bar = new DrawBar(p, x, y, len);
	}

	// tests the getPane Method from the DrawBar class
	// to make sure the method returns the expected pane
	@Test
	void testGetPane() {
		Pane pane = this.bar.getPane();
		assertSame(p, pane);
	}

	// tests the setPane method of the DrawBar class to
	// make sure method sets the pane correctly
	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.bar.setPane(pane);

		assertSame(pane, bar.getPane());
		assertNotSame(p, bar.getPane());

	}

	// Tests the getStartX method of DrawBar class to
	// make sure that correct x coordinate of the bar is returned
	@Test
	void testGetStartX() {
		double x1 = this.bar.getStartX();
		assertEquals(x, x1);
	}

	// Test to make sure setStartX sets the x value correctly.
	@Test
	void testSetStartX() {
		double expected = 200;
		this.bar.setStartX(expected);
		double actual = this.bar.getStartX();

		// see if the bar is set to the new value for startX
		assertEquals(expected, actual);
		// check that this new value is not equal to the old value
		assertNotEquals(x, actual);
	}

	// Tests the getStartY method of DrawBar class to
	// make sure that correct y coordinate of the bar is returned
	@Test
	void testGetStartY() {
		double y1 = this.bar.getStartY();
		assertEquals(y, y1);
	}

	// Test to make sure setStartY sets the Y value correctly.
	@Test
	void testSetStartY() {
		double expected = 200;
		this.bar.setStartY(expected);
		double actual = this.bar.getStartY();

		// see if the bar is set to the new value for startY
		assertEquals(expected, actual);
		// check that this new value is not equal to the old value
		assertNotEquals(y, actual);
	}


}