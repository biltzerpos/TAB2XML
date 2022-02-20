package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DrawClefTest {

	@Test
	void test() {
		DrawClef clef = new DrawClef();
		clef.setX(3);
		clef.setY(40);
		
		assertEquals(3, clef.getX());
		assertEquals(4, clef.getY());
	}
	
	

}
