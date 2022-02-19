package instrument;




import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import converter.Score;
import instruments.Guitar;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Clef;
import utility.MusicXMLCreator;

public class GuitarTest extends ApplicationTest{
	
	
	@Test
	void testExtractClef() {
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
		ScorePartwise scorePartwise = mxlc.getScorePartwise();
		Guitar g = new Guitar(scorePartwise, null);
		Measure m = g.getMeasureList().get(0);
		Clef c1 = g.extractClef(m);
		
		Clef c2 = scorePartwise.getParts().get(0).getMeasures().get(0).getAttributes().getClef();
		
		assertEquals(c1,c2);
	}

}
