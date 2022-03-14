package player;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import GUI.MainViewController;
import GUI.PreviewSheetMusicController;
import converter.Score;
import converter.measure.TabMeasure;
import converter.note.TabNote;
import custom_exceptions.TXMLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import models.ScorePartwise;
import pdfbox.pdfbuilder;

class DrumInstrumentTest {

	@Test
	void test() throws TXMLException, IOException {
		String gtab = "CC|x---------------|--------x-------|\r\n"
					+ "HH|--x-x-x-x-x-x-x-|----------------|\r\n"
					+ "SD|----o-------o---|oooo------------|\r\n"
					+ "HT|----------------|----oo----------|\r\n"
					+ "MT|----------------|------oo--------|\r\n"
					+ "BD|o-------o-------|o-------o-------|";


		Score score = new Score(gtab);
		
		DrumInstrument drumPlayer = new DrumInstrument();
		ScorePartwise scorePartWise = score.getModel();
		
		drumPlayer.setupDrums(scorePartWise.getParts().get(0).getMeasures(), score);
		
		
	
		
	}

}
