package GUI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GUI.draw.DrawBar;

class DrawBarTest {

	@Test
	void test() {
		//String s1 = ("|1--2--3|2--3--4|");
		DrawBar bar = new DrawBar();
		bar.setStartX(2);
		//bar.setStartY(4);
		assertEquals("2", bar.getStartX());


	}
}
