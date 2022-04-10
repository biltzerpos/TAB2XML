//package GUI.draw;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import javafx.scene.layout.Pane;
//import javafx.scene.shape.Ellipse;
//import javafx.scene.shape.Line;
//import javafx.scene.Node;
//import javafx.scene.text.Text;
//
//import converter.Score;
//
//import models.ScorePartwise;
//import models.measure.Measure;
//import models.measure.note.Note;
//
//import instruments.Drumset;
//
//import custom_exceptions.TXMLException;
//
//public class DrawNoteTest {
//
//	@Test
//	void testDrawFret() {
//		try {
//		    String input ="""
//				|-----------0-----|-0---------------|
//				|---------0---0---|-0---------------|
//				|-------1-------1-|-1---------------|
//				|-----2-----------|-2---------------|
//				|---2-------------|-2---------------|
//				|-0---------------|-0---------------|
//		    """;
//
//		    Pane pane = new Pane();
//
//		    Score score = new Score(input);
//	
//		    ScorePartwise scorePartwise = score.getModel();
//
//		    double x = 0;
//		    double y = 0;
//
//		    for (Measure measure : scorePartwise.getParts().get(0).getMeasures()) {
//
//		    	for (Note note : measure.getNotesBeforeBackup()) {
//		    		// Draw note
//		    		int fret = note.getNotations().getTechnical().getFret();
//		    		DrawNote drawNote = new DrawNote(pane, note, x, y, 12);
//		    		drawNote.drawFret();
//
//		    		// Check pane to make sure the note was added
//		    		Node addedNode = pane.getChildren().get(pane.getChildren().size()-1);
//		    		if (addedNode instanceof Text) {
//		    			Text textNode = (Text) addedNode;
//
//		    			assertEquals(x, textNode.getX(), "Text node has incorrect x-coordinate: expected " + x + ", but received " + textNode.getX());
//		    			assertEquals(y, textNode.getY(), "Text node has incorrect y-coordinate: expected " + y + ", but received " + textNode.getY());
//		    			assertEquals(Integer.toString(fret), textNode.getText(), "Text node has incorrect text value: expected " + fret + ", but receieved" + textNode.getText());
//		    		} else {
//		    			fail("Text node was not added");
//		    		}
//		    	}
//		    }
//		} catch (TXMLException e) {
//			fail("TXMLException thrown\n" + e.getMessage());
//		}
//	}
//
//	@Test
//	void testDrawO() {
//		try {
//		    String input ="""
//				CC|x---------------|--------x-------|
//				HH|--x-x-x-x-x-x-x-|----------------|
//				SD|----o-------o---|oooo------------|
//				HT|----------------|----oo----------|
//				MT|----------------|------oo--------|
//				BD|o-------o-------|o-------o-------|
//		    """;
//
//		    Pane pane = new Pane();
//
//		    Score score = new Score(input);
//	
//		    ScorePartwise scorePartwise = score.getModel();
//
//		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane);
//
//		    drumsetDrawer.draw();
//
//		    List<Ellipse> noteList = new ArrayList<>();
//
//		    for (Node node : pane.getChildren()) {
//		    	if (node.getId() != null && node.getId().equals("drum-note-o")) {
//		    		noteList.add((Ellipse) node);
//		    	}
//		    }
//
//		    // Non-cymbal notes from first measure
//		    assertEquals(28.0, noteList.get(0).getCenterX());
//		    assertEquals(46.0, noteList.get(0).getCenterY());
//		    assertEquals(128.0, noteList.get(1).getCenterX());
//		    assertEquals(26.0, noteList.get(1).getCenterY());
//		    assertEquals(228.0, noteList.get(2).getCenterX());
//		    assertEquals(46.0, noteList.get(2).getCenterY());
//		    assertEquals(328.0, noteList.get(3).getCenterX());
//		    assertEquals(26.0, noteList.get(3).getCenterY());
//
//		    // Non-cymbal notes from second measure
//		    assertEquals(428.0, noteList.get(4).getCenterX());
//		    assertEquals(26.0, noteList.get(4).getCenterY());
//		    assertEquals(428.0, noteList.get(5).getCenterX());
//		    assertEquals(46.0, noteList.get(5).getCenterY());
//		    assertEquals(478.0, noteList.get(6).getCenterX());
//		    assertEquals(26.0, noteList.get(6).getCenterY());
//		    assertEquals(528.0, noteList.get(7).getCenterX());
//		    assertEquals(26.0, noteList.get(7).getCenterY());
//		    assertEquals(578.0, noteList.get(8).getCenterX());
//		    assertEquals(26.0, noteList.get(8).getCenterY());
//		    assertEquals(628.0, noteList.get(9).getCenterX());
//		    assertEquals(16.0, noteList.get(9).getCenterY());
//		    assertEquals(678.0, noteList.get(10).getCenterX());
//		    assertEquals(16.0, noteList.get(10).getCenterY());
//		    assertEquals(728.0, noteList.get(11).getCenterX());
//		    assertEquals(21.0, noteList.get(11).getCenterY());
//		    assertEquals(778.0, noteList.get(12).getCenterX());
//		    assertEquals(21.0, noteList.get(12).getCenterY());
//		    assertEquals(828.0, noteList.get(13).getCenterX());
//		    assertEquals(46.0, noteList.get(13).getCenterY());
//
//		    assertEquals(14, noteList.size(), "Pane should have 14 \"o\" notes, but has " + noteList.size() + "instead");
//		} catch (TXMLException e) {
//			fail("TXMLException thrown\n" + e.getMessage());
//		}
//	}
//
//	@Test
//	void testDrawX() {
//		try {
//		    String input ="""
//				CC|x---------------|--------x-------|
//				HH|--x-x-x-x-x-x-x-|----------------|
//				SD|----o-------o---|oooo------------|
//				HT|----------------|----oo----------|
//				MT|----------------|------oo--------|
//				BD|o-------o-------|o-------o-------|
//		    """;
//
//		    Pane pane = new Pane();
//
//		    Score score = new Score(input);
//	
//		    ScorePartwise scorePartwise = score.getModel();
//
//		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane);
//
//		    drumsetDrawer.draw();
//
//		    List<Line> noteList = new ArrayList<>();
//
//		    for (Node node : pane.getChildren()) {
//		    	if (node.getId() != null && node.getId().equals("drum-note-x-1")) {
//		    		noteList.add((Line) node);
//		    	}
//		    }
//
//		    assertEquals(9, noteList.size(), "Pane should have 9 top-left to bottom-right lines for the \"x\" notes, but has " + noteList.size() + "instead");
//
//		    // Cymbal notes from first measure
//		    assertEquals(32.0, noteList.get(0).getStartX());
//		    assertEquals(4.0, noteList.get(0).getStartY());
//		    assertEquals(82.0, noteList.get(1).getStartX());
//		    assertEquals(9.0, noteList.get(1).getStartY());
//		    assertEquals(132.0, noteList.get(2).getStartX());
//		    assertEquals(9.0, noteList.get(2).getStartY());
//		    assertEquals(182.0, noteList.get(3).getStartX());
//		    assertEquals(9.0, noteList.get(3).getStartY());
//		    assertEquals(232.0, noteList.get(4).getStartX());
//		    assertEquals(9.0, noteList.get(4).getStartY());
//		    assertEquals(282.0, noteList.get(5).getStartX());
//		    assertEquals(9.0, noteList.get(5).getStartY());
//		    assertEquals(332.0, noteList.get(6).getStartX());
//		    assertEquals(9.0, noteList.get(6).getStartY());
//		    assertEquals(382.0, noteList.get(7).getStartX());
//		    assertEquals(9.0, noteList.get(7).getStartY());
//
//		    // Cymbal notes from second measure
//		    assertEquals(832.0, noteList.get(8).getStartX());
//		    assertEquals(4.0, noteList.get(8).getStartY());
//
//		    noteList = new ArrayList<>();
//
//		    for (Node node : pane.getChildren()) {
//		    	if (node.getId() != null && node.getId().equals("drum-note-x-2")) {
//		    		noteList.add((Line) node);
//		    	}
//		    }
//
//		    assertEquals(9, noteList.size(), "Pane should have 9 top-right to bottom-left lines for the \"x\" notes, but has " + noteList.size() + "instead");
//		} catch (TXMLException e) {
//			fail("TXMLException thrown\n" + e.getMessage());
//		}
//	}
//}
