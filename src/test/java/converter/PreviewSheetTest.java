package converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import converter.measure.TabMeasure;
import converter.note.TabNote;
import custom_exceptions.TXMLException;

class PreviewSheetTest {

	@Disabled
	@Test
	void test() throws TXMLException {
		String gtab = "|-----------0-----|-0---------------|\r\n"
				+ "|---------0---0---|-0---------------|\r\n"
				+ "|-------1-------1-|-1---------------|\r\n"
				+ "|-----2-----------|-2---------------|\r\n"
				+ "|---2-------------|-2---------------|\r\n"
				+ "|-0---------------|-0---------------|";


		Score score = new Score(gtab);
//		assertEquals(1, score.getTabSectionList().size(), "one measure was expected but found " + score.getTabSectionList().size() + ".");
		TabMeasure measure = score.getMeasure(1);
//		assertTrue(measure.isRepeatEnd());
//		assertTrue(measure.isRepeatStart());
		List<TabNote> noteList = measure.getSortedNoteList();
//		assertEquals(3, noteList.size(), "three notes were expected in the following measure, but found " + noteList.size() + "."	+ "\nMeasure:\n" + gtab);
		// Matcher matcher = Pattern.compile("<words[^>]*>[^<0-9]*" + expectedRepeatCount + "[^<0-9]*</words>").matcher(MusicXMLCreator.generateMusicXML(score));
		// assertTrue(matcher.find(), "repeat count not properly detected and applied");
		
		
		
		
		assertEquals("E0", score.getModel().getParts().get(0).measures.get(0).getNotesBeforeBackup().get(0).getPitch().getStep() + 
								score.getModel().getParts().get(0).measures.get(0).getNotesBeforeBackup().get(0).getNotations().getTechnical().getFret());
		assertEquals("F1", score.getModel().getParts().get(0).measures.get(0).getNotesBeforeBackup().get(0));

	}

}
