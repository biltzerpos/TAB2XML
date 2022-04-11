package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;

class DrawBassBarTest {

	private DrawBassBar bar;
	private Pane p = new Pane();
	private final double x = 30.00;
	private final double y = 40.00;

	@BeforeEach
	public void setUp() throws Exception {

		this.bar = new DrawBassBar(p, x, y);
	}

	@Test
	void testGetPane() {
		Pane pane = this.bar.getPane();
		assertSame(p, pane);
	}

	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.bar.setPane(pane);

		assertSame(pane, bar.getPane());
		assertNotSame(p, bar.getPane());

	}
	
	@Test
	void testGetStartX() {
		double x1 = this.bar.getStartX();
		assertEquals(x, x1);
	}

	@Test
	void testSetStartX() {
		double expected = 200;
		this.bar.setStartX(expected);
		double actual = this.bar.getStartX();

		assertEquals(expected, actual);
		assertNotEquals(x, actual);
	}

	@Test
	void testGetStartY() {
		double y1 = this.bar.getStartY();
		assertEquals(y, y1);
	}

	
	@Test
	void testSetStartY() {
		double expected = 200;
		this.bar.setStartY(expected);
		double actual = this.bar.getStartY();

		assertEquals(expected, actual);
		assertNotEquals(y, actual);
	}


}
