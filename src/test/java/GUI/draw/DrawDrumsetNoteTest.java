package GUI.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.Node;
import javafx.scene.text.Text;

import converter.Score;

import models.ScorePartwise;
import models.measure.Measure;
import models.measure.note.Note;

import instruments.Drumset;

import custom_exceptions.TXMLException;

public class DrawDrumsetNoteTest {

	@Test
	void testDrawO() {
		try {
		    String input ="""
				CC|x---------------|--------x-------|
				HH|--x-x-x-x-x-x-x-|----------------|
				SD|----o-------o---|oooo------------|
				HT|----------------|----oo----------|
				MT|----------------|------oo--------|
				BD|o-------o-------|o-------o-------|
		    """;

		    Pane pane = new Pane();

		    Score score = new Score(input);
	
		    ScorePartwise scorePartwise = score.getModel();

		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 50, 10, 10, 100);

		    drumsetDrawer.draw();

		    List<Ellipse> noteList = new ArrayList<>();

		    for (Node node : pane.getChildren()) {
		    	if (node.getId() != null && node.getId().equals("drum-note-o")) {
		    		noteList.add((Ellipse) node);
		    	}
		    }

		    assertEquals(14, noteList.size(), "Pane should have 14 \"o\" notes, but has " + noteList.size() + "instead");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testDrawX() {
		try {
		    String input ="""
				CC|x---------------|--------x-------|
				HH|--x-x-x-x-x-x-x-|----------------|
				SD|----o-------o---|oooo------------|
				HT|----------------|----oo----------|
				MT|----------------|------oo--------|
				BD|o-------o-------|o-------o-------|
		    """;

		    Pane pane = new Pane();

		    Score score = new Score(input);
	
		    ScorePartwise scorePartwise = score.getModel();

		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 50, 10, 10, 100);

		    drumsetDrawer.draw();

		    List<Line> noteList = new ArrayList<>();

		    for (Node node : pane.getChildren()) {
		    	if (node.getId() != null && node.getId().equals("drum-note-x-1")) {
		    		noteList.add((Line) node);
		    	}
		    }

		    assertEquals(9, noteList.size(), "Pane should have 9 top-left to bottom-right lines for the \"x\" notes, but has " + noteList.size() + "instead");

		    noteList = new ArrayList<>();

		    for (Node node : pane.getChildren()) {
		    	if (node.getId() != null && node.getId().equals("drum-note-x-2")) {
		    		noteList.add((Line) node);
		    	}
		    }

		    assertEquals(9, noteList.size(), "Pane should have 9 top-right to bottom-left lines for the \"x\" notes, but has " + noteList.size() + "instead");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testDrawRest() {
		try {
			String input = """
								Quarter rest and 32nd notes
				C |R-----------------------X---X---|X-------------------------------|
				F |------------ddddddddddd---------|--------------------------------|
				B |------------------------o---o---|o-------------------------------|
			""";
	
		    Pane pane = new Pane();
	
		    Score score = new Score(input);
	
		    ScorePartwise scorePartwise = score.getModel();
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 50, 10, 10, 100);
	
		    drumsetDrawer.draw();

		    boolean foundRestSVG = false;

		    for (int i = 0; i < pane.getChildren().size() && !foundRestSVG; i++) {
		    	if (pane.getChildren().get(i) instanceof  SVGPath) {
		    		foundRestSVG = true;
		    	}
		    }

		    assertTrue(foundRestSVG, "Rest note SVG not found");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testDrawBeam() {
		try {
			String input = """
								Quarter rest and 32nd notes
				C |R-----------------------X---X---|X-------------------------------|
				F |------------ddddddddddd---------|--------------------------------|
				B |------------------------o---o---|o-------------------------------|
			""";

		    Pane pane = new Pane();
	
		    Score score = new Score(input);
	
		    ScorePartwise scorePartwise = score.getModel();
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 50, 10, 10, 100);
	
		    drumsetDrawer.draw();

		    int numberOfBeams = 0;

		    for (int i = 0; i < pane.getChildren().size(); i++) {
		    	if (pane.getChildren().get(i) instanceof  Rectangle) {
		    		numberOfBeams++;
		    	}
		    }

		    assertEquals(40, numberOfBeams, "20 beams should have been drawn, but " + numberOfBeams + " beams were drawn instead");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testDrawTie() {
		try {
			String input = """
			  6/4                      13/8
			  C |X---------------------X-|X-------------------------|
			  S |------------------------|--------------------o-----|
			  t |------------------------|---------------------o-o--|
			  T |------------------------|----------------------o-oo|
			  B |o---------------------o-|o-------------------------|
			    |1 + 2 + 3 + 4 + 5 + 6 + |1+2+3+4+5+6+7+8+9+0+1+2+3+|
			""";

		    Pane pane = new Pane();
	
		    Score score = new Score(input);
	
		    ScorePartwise scorePartwise = score.getModel();
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 50, 10, 10, 100);
	
		    drumsetDrawer.draw();

		    int numberOfTies = 0;

		    for (int i = 0; i < pane.getChildren().size(); i++) {
		    	if (pane.getChildren().get(i) instanceof  QuadCurve) {
		    		numberOfTies++;
		    	}
		    }

		    assertEquals(6, numberOfTies , "6 ties should have been drawn, but " + numberOfTies + " ties were drawn instead");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

}
