package GUI;

import static org.junit.jupiter.api.Assertions.*;

import org.jfugue.pattern.Pattern;
import org.junit.jupiter.api.Test;

import converter.Score;
import custom_exceptions.TXMLException;
import models.ScorePartwise;

class MusicPlayerTest {

	private Pattern getGuitarString(String input) throws TXMLException {

		Score score = new Score(input);
		ScorePartwise scorePartwise = score.getModel();
		MusicPlayer sequence = new MusicPlayer(scorePartwise);
		sequence.getGuitarString();
		return sequence.getPattern();
	}

	@Test
	void testGuitar() throws TXMLException {
		String input =  
				"""
|-----------0-----|-0---------------|
|---------0---0---|-0---------------|
|-------1-------1-|-1---------------|
|-----2-----------|-2---------------|
|---2-------------|-2---------------|
|-0---------------|-0---------------|

				 """;

		Pattern guitar = getGuitarString(input);

		assertTrue(guitar.toString().equals("V0 I[Guitar]  E2i B2i E3i G#3i B3i E4i B3i G#3i E4w+B3w+G#3w+E3w+B2w+E2w"));

	}
	private Pattern getDrumString(String input) throws TXMLException {

		Score score = new Score(input);
		ScorePartwise scorePartwise = score.getModel();
		MusicPlayer sequence = new MusicPlayer(scorePartwise);
		sequence.getDrumString();
		return sequence.getPattern();
	}
	@Test
	void testDrum() throws TXMLException {
		String input = """
CC|x---------------|--------x-------|
HH|--x-x-x-x-x-x-x-|----------------|
SD|----o-------o---|oooo------------|
HT|----------------|----oo----------|
MT|----------------|------oo--------|
BD|o-------o-------|o-------o-------|

				""";

		Pattern drum = getDrumString(input);

		assertEquals(drum.toString(), "V9  [Crash_Cymbal_1]i+[Bass_Drum]i [Closed_Hi_Hat]i [Closed_Hi_Hat]i+[Acoustic_Snare]i [Closed_Hi_Hat]i [Closed_Hi_Hat]i+[Bass_Drum]i [Closed_Hi_Hat]i [Closed_Hi_Hat]i+[Acoustic_Snare]i [Closed_Hi_Hat]i [Acoustic_Snare]s+[Bass_Drum]s [Acoustic_Snare]s [Acoustic_Snare]s [Acoustic_Snare]s [Lo_Mid_Tom]s [Lo_Mid_Tom]s [Lo_Tom]s [Lo_Tom]s [Crash_Cymbal_1]h+[Bass_Drum]h");
		//Drum: V9  [Crash_Cymbal_1]i+[Bass_Drum]i [Closed_Hi_Hat]i [Closed_Hi_Hat]i+[Acoustic_Snare]i [Closed_Hi_Hat]i [Closed_Hi_Hat]i+[Bass_Drum]i [Closed_Hi_Hat]i [Closed_Hi_Hat]i+[Acoustic_Snare]i [Closed_Hi_Hat]i [Acoustic_Snare]s+[Bass_Drum]s [Acoustic_Snare]s [Acoustic_Snare]s [Acoustic_Snare]s [Lo_Mid_Tom]s [Lo_Mid_Tom]s [Lo_Tom]s [Lo_Tom]s [Crash_Cymbal_1]h+[Bass_Drum]h
	}
	
	private Pattern getBassString(String input) throws TXMLException {

		Score score = new Score(input);
		ScorePartwise scorePartwise = score.getModel();
		MusicPlayer sequence = new MusicPlayer(scorePartwise);
		sequence.getBassString();
		return sequence.getPattern();
	}
	@Test
	void testBass() throws TXMLException {
		String input = """
Intro


   7/4
  REPEAT 8x
G---4-----------|
D----4----------|
A-2---2-----2-5-|
E-------2-5-----|

G---------------|-----------------------------|--4-----------|
D-4-4-4-------4-|-3---2-----------------------|---4----------|
A------4----4---|-------2---------0---2---5---|2---2-----2-5-|
E-------2-5-----|---------0---3---------------|------2-5-----|

				""";

		Pattern bass = getBassString(input);

		assertEquals(bass.toString(), "T180 V0 I[Acoustic_Bass]  B1q B2i F#2i B1q F#1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q F#2q F#2q F#2i C#2i F#1q A1q C#2q F#2q F2q E2i B1i E1q G1q A1q B1q D2q B1q B2i F#2i B1q F#1q A1q B1q D2q");
	}
	
}
