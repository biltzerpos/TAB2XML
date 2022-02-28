package instrument;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import converter.Score;
import instruments.Guitar;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import models.measure.note.Note;
import models.measure.note.notations.Notations;
import utility.MusicXMLCreator;

public class GuitarTest extends ApplicationTest{
	
	private ScorePartwise scorePartwise; 
	private Guitar g;
	private Measure m1;
	
	@BeforeEach 
	public void setUp() throws Exception
	{
		String input =  
				"""
				|-----------0-----|-0---------------|
				|---------0---0---|-0---------------|
				|-------1-------1-|-1---------------|
				|-----2-----------|-2---------------|
				|---2-------------|-2---------------|
				|-0---------------|-0---------------|
				 """;
		Score score = new Score(input);
		MusicXMLCreator mxlc = new MusicXMLCreator(score);
		scorePartwise = mxlc.getScorePartwise();
		g = new Guitar(scorePartwise, null);
		m1 = g.getMeasureList().get(0);
		
	}
	
	//Testing extractClef() to see if the extracted clef from measure input is same as the actual clef
	@Test
	void testExtractClef() {
		
		//clef from the extractClef
		Clef c1 = g.extractClef(m1);
		
		//Actual clef
		Clef c2 = scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
		
		assertEquals(c1,c2);
	}
	
	//given a note with a chord test if method returns true
	@Test
	void testNoteHasChordTrue() {
		//note from the actual input measure 2 note 2 which has chord
		List<Note> noteList = scorePartwise.getParts().get(0).getMeasures().get(1).getNotesBeforeBackup();
		Note n = noteList.get(1);
		Boolean res = g.noteHasChord(n);
		assertTrue(res, "note has chord");
				
	}
	
	//given a note without a chord test if method returns false
	@Test
	void testNoteHasChordFalse() {
		//note from the actual input measure 1 note 2 which has no chord
		List<Note> noteList = scorePartwise.getParts().get(0).getMeasures().get(0).getNotesBeforeBackup();
		Note n = noteList.get(1);
		Boolean res = g.noteHasChord(n);
		assertFalse(res, "note has no chord");
	}
	
	//Given a note that has technical attributes, checks if method returns true
	@Test
	void testNoteHasTechnicalTrue() {
		//note from the actual input measure 2 note 2 which has technical
		List<Note> noteList = scorePartwise.getParts().get(0).getMeasures().get(1).getNotesBeforeBackup();
		Note n = noteList.get(1);
		Boolean res = g.noteHasTechnical(n);
		assertTrue(res, "note has technical attributes");
	}
	
	//Given a note that doesnt have technical attributes, checks if method returns false
	@Test
	void testNoteHasTechnicalFalse() {
		//all notes from input have technical so we create a local note without any
		Note n = new Note();
		Notations notation = new Notations();
		notation.setTechnical(null);
		n.setNotations(notation);
		Boolean res = g.noteHasTechnical(n);
		assertFalse(res, "note has technical attributes");
	}
	
	//Test that getMeasureList() returns the correct list
	@Test
	void testGetMeasureList() {
		//Actual measure list-->expected
		List<Measure> m = scorePartwise.getParts().get(0).getMeasures();
		
		//measure list from the Guitar class
		List<Measure> m0 = g.getMeasureList();
		
		assertSame(m, m0);
	}
	
	//Test that setMeasureList() set the correct list
	@Test
	void testSetMeasureList() {
		//Actual measure list-->expected
		List<Measure> m = scorePartwise.getParts().get(0).getMeasures();
		
		//create a new measure list to set 
		String input1 =  
				"""
				|-----------0-----|-0---------------|
				|---------0---0---|-0---------------|
				|-------1-------1-|-1---------------|
				|-----2-----------|-2---------------|
				|---2-------------|-2---------------|
				|-0---------------|-0---------------|
				
				|-----------0-----|-0---------------|
				|---------0---0---|-0---------------|
				|-------1-------1-|-1---------------|
				|-----2-----------|-2---------------|
				|---2-------------|-2---------------|
				|-0---------------|-0---------------|
				  """;
		Score score1 = new Score(input1);
		MusicXMLCreator mxlc1 = new MusicXMLCreator(score1);
		ScorePartwise scorePartwise1 = mxlc1.getScorePartwise();
		Guitar g1 = new Guitar(scorePartwise1, null);
		List<Measure> newList = g1.getMeasureList();
		
		//setting the meaasure of the g to newList
		g.setMeasureList(newList);
		
		//Getting the new list from g which should be different from m
		List<Measure> m0 = g.getMeasureList();
		
		assertNotSame(m, m0);
		assertSame(newList, m0);
	}
	
	//Tests Is44Beat returns true for a measure with 4/4 beat
	@Test
	void testIs44BeatTrue() {
		//Getting the first measure from input which has 8 1/8 notes (4 1/4)
		List<Note> n = scorePartwise.getParts().get(0).getMeasures().get(0).getNotesBeforeBackup();
		Boolean res = g.is44Beat(n);
		assertTrue(res, "This measure is 4/4");
		
	}
	
	//Tests is44Beat returns false for a measure without 4/4 beat
	@Test
	void testIs44BeatFalse() {
		//Getting the 2nd measure from input which has different durations
		List<Note> n = scorePartwise.getParts().get(0).getMeasures().get(1).getNotesBeforeBackup();
		Boolean res = g.is44Beat(n);
		assertFalse(res, "This measure is 4/4");
		
	}
		
	

}