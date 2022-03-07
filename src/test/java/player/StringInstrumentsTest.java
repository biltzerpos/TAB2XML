package player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import converter.Score;
import custom_exceptions.TXMLException;
import models.ScorePartwise;
import models.part_list.ScoreInstrument;

class StringInstrumentsTest {

	@Test
	void test() throws TXMLException {
		String gtab = "|-----------0-----|-0---------------|\n"
				+ "|---------0---0---|-0---------------|\n"
				+ "|-------1-------1-|-1---------------|\n"
				+ "|-----2-----------|-2---------------|\n"
				+ "|---2-------------|-2---------------|\n"
				+ "|-0---------------|-0---------------|";


	Score score = new Score(gtab);
	
	StringInstruments stringPlayer = new StringInstruments();
	ScorePartwise scorePartWise = score.getModel();
	
	stringPlayer.playStringInstruments(score);
	}

}
