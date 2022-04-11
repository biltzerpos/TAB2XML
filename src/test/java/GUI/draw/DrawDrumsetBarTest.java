package GUI.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;

public class DrawDrumsetBarTest {

		private DrawDrumsetBar bar;
		private Pane p = new Pane();
		private final double x = 20.0;
		private final double y = 20.0;

		@BeforeEach
		public void setUp() throws Exception {
			this.bar = new DrawDrumsetBar(p);
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
			double x = this.bar.getStartX();
			assertEquals(x, x);
		}

		@Test
		void testSetStartX() {
			double expected = 100;
			this.bar.setStartX(expected);
			double actual = this.bar.getStartX();

			assertEquals(expected, actual);
			assertNotEquals(x, actual);
		}

		@Test
		void testGetStartY() {
			double y = this.bar.getStartY();
			assertEquals(y, y);
		}

		@Test
		void testSetStartY() {
			double expected = 100;
			this.bar.setStartY(expected);
			double actual = this.bar.getStartY();

			assertEquals(expected, actual);
			assertNotEquals(y, actual);
		}


	}