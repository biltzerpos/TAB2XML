package GUI.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import models.measure.attributes.Clef;

public class DrawSlurTest {
	private DrawSlur dSlur; 
	private Pane p = new Pane();
	
	@BeforeEach
	public void setUp() throws Exception {
		this.dSlur = new DrawSlur(p);
	}
	

	@Test
	void testSetStartX() {
		double expected = -3;
		this.dSlur.setStartX(expected);
		double actual = this.dSlur.getStartX();
		assertEquals(expected, actual);
		
	}
	

	@Test
	void testSetStartY() {
		double expected = -3;
		this.dSlur.setStartY(expected);
		double actual = this.dSlur.getStartY();

		assertEquals(expected, actual);
	}

	@Test
	void testSetEndX() {
		double expected = -3;
		this.dSlur.setEndX(expected);
		double actual = this.dSlur.getEndX();

		assertEquals(expected, actual);
		
	}
	
	@Test
	void testGetPane() {
		Pane pane = this.dSlur.getPane();
		assertSame(p, pane);
	}
	
	@Test
	void testSetPane() {
		Pane pane = new Pane();
		this.dSlur.setPane(pane);
		Pane actual = this.dSlur.getPane();

		assertSame(pane, actual);
		assertNotSame(p, actual);

	}
	
	
	@Test
	void testSetPlace() {
		Integer place = 7;
		this.dSlur.setPlace(place);
		Integer actual = this.dSlur.getPlace();

		assertSame(place, actual);
		assertNotSame(8, actual);

	}
	
	
	@Test
	void testSetStrokeWidth() {
		
		int expected = 3;
		this.dSlur.setStrokeWidth(expected);
		int x = this.dSlur.getStrokeWidth();
		assertEquals(expected, x);
	}

}
