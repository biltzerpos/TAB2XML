package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;

class DrawBarTest {

	@Test
	void testSetGetStart() {
		DrawBar bar = new DrawBar();
		bar.setStartX(30);
		bar.setStartY(40);
		
		assertEquals(30.0, bar.getStartX());
		assertEquals(40.0, bar.getStartY());
	}
	@Test
	void testPane() {
		Pane pane = new Pane();
		//String s1 = ("|1--2--3|2--3--4|");
		DrawBar bar = new DrawBar(pane, 30, 30);
		
		assertEquals(30.0, bar.getStartX());
		assertEquals(30.0, bar.getStartY());
		assertEquals(pane, bar.getPane());
	}

}
