package instrument;

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

public class DrumTest {

	@Test
	void testPositioningValuesDefault() {
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
	
		    assertEquals(50.0, drumsetDrawer.getSpacing(), "spacing should be 50.0, but was " + drumsetDrawer.getSpacing() + " instead");
		    assertEquals(1.0, drumsetDrawer.getFontSize(), "font size should be 1.0, but was " + drumsetDrawer.getFontSize() + " instead");
		    assertEquals(0.0, drumsetDrawer.getMusicLineSpacing(), "music line spacing should be 0.0, but was " + drumsetDrawer.getMusicLineSpacing() + " instead");
		    assertEquals(100.0, drumsetDrawer.getStaffSpacing(), "staff spacing should be 100.0, but was " + drumsetDrawer.getStaffSpacing() + " instead");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testPositioningValuesModified() {
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
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 35, 12, 20, 150);
	
		    assertEquals(35.0, drumsetDrawer.getSpacing(), "spacing should be 35.0, but was " + drumsetDrawer.getSpacing() + " instead");
		    assertEquals(1.4, drumsetDrawer.getFontSize(), "font size should be 1.4, but was " + drumsetDrawer.getFontSize() + " instead");
		    assertEquals(10.0, drumsetDrawer.getMusicLineSpacing(), "music line spacing should be 10.0, but was " + drumsetDrawer.getMusicLineSpacing() + " instead");
		    assertEquals(240.0, drumsetDrawer.getStaffSpacing(), "staff spacing should be 240.0, but was " + drumsetDrawer.getStaffSpacing() + " instead");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testScorePartwise() {
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
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 35, 12, 20, 150);
	
		    assertEquals(scorePartwise, drumsetDrawer.getScorePartwise(), "ScorePartwise object not properly set");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testPane() {
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
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 35, 12, 20, 150);
	
		    assertEquals(pane, drumsetDrawer.getPane(), "Pane object not properly set");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testMeasureList() {
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
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 35, 12, 20, 150);
	
		    assertEquals(scorePartwise.getParts().get(0).getMeasures(), drumsetDrawer.getMeasureList(), "Measure list not properly set");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

	@Test
	void testClef() {
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
	
		    Drumset drumsetDrawer = new Drumset(scorePartwise, pane, 35, 12, 20, 150);
	
		    assertEquals(scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef(), drumsetDrawer.getClef(), "Measure list not properly set");
		} catch (TXMLException e) {
			fail("TXMLException thrown\n" + e.getMessage());
		}
	}

}
