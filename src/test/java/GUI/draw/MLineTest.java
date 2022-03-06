package GUI.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.layout.Pane;

public class MLineTest {

	private MLine ml;
	private Pane p = new Pane();
	private final double startX = 30.00;
	private final double startY = 40.00;
	private final double endX = 60.00;
	private final double endY = 40.00;
	private final int tag = 2;

	@BeforeEach
	public void setUp() throws Exception {

		this.ml = new MLine(p, startX, startY, endX, endY, tag);
	}

	// tests the getPane Method to make sure the method returns the expected pane
	@Test
	void testGetPane() {
		Pane pane = this.ml.getPane();
		assertSame(p, pane);
	}

	// tests the setPane method to make sure method sets the pane correctly
	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.ml.setPane(pane);
		Pane actual = this.ml.getPane();

		assertSame(pane, actual);
		assertNotSame(p, actual);

	}

	// Test getLineTag() to make sure it returns the correct tag
	@Test
	void testGetLineTag() {
		int actual = this.ml.getLineTag();
		assertEquals(tag, actual);
	}

	// Test setLineTag() to make it set the tag properly
	@Test
	void testSetLineTag() {
		int expected = 4;
		this.ml.setLineTag(expected);
		int actual = this.ml.getLineTag();
		assertEquals(expected, actual);
		assertNotEquals(tag, actual);
	}

	// Test getStartX(int tag) to make sure given a tag the method return the
	// correct x coordinates
	@Test
	void testGetStartX() {
		double actual = this.ml.getStartX(tag);
		assertEquals(startX, actual);
	}

	// Test getStartY(int tag) to make sure given a tag the method return the
	// correct y coordinates
	@Test
	void testGetStartY() {
		double actual = this.ml.getStartY(tag);
		assertEquals(startY, actual);
	}
}
