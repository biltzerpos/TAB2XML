package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;

class DrawBarTest {

	@Test
	void test01() {
		DrawBar bar = new DrawBar();
		bar.setStartX(30);
		
		assertEquals(30.0, bar.getStartX());
	}
	@Test
	void test02() {
		Pane pane = new Pane();
		//String s1 = ("|1--2--3|2--3--4|");
		DrawBar bar = new DrawBar(pane, 30, 30);
		
		assertEquals(30.0, bar.getStartX());
		assertEquals(30.0, bar.getStartY());
		assertEquals(pane, bar.getPane());
	}

}
