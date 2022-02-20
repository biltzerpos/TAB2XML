package GUI.draw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;
import models.measure.attributes.Clef;

class DrawClefTest {

	
	@Test
	void testPane() {
		Clef clef=new Clef("clef",3);
		Pane pane = new Pane();
		
		DrawClef clef2 = new DrawClef(pane, clef, 30, 30);
		
		assertEquals(30.0, clef2.getX());
		assertEquals(30.0, clef2.getY());
		assertEquals(pane, clef2.getPane());
		assertEquals(clef, clef2.getClef());
	}

}
