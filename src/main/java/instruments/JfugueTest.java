package instruments;

import java.util.List;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.barline.BarLine;

public class JfugueTest {
//	private ScorePartwise scorePartwise;
//	@FXML
//	private Pane pane;
//	private static List<Measure> measureList;
//	
//
//	
//	public static void main(String[] args) {
//		System.out.print("Repeat: " + getRepeatTime());
//	}
//	
//	public static int getRepeatTime() {
//		int time = 0;
//		if(noteHasRepeatLeft() && noteHasRepeatRight()) {
//			time = Integer.valueOf(getRightBarLine().getRepeat().getTimes());
//		}
//		return time;
//	}
//	public static BarLine getRightBarLine() {
//		BarLine rightBar = null;
//		for (int i = 0; i < measureList.size(); i++) {
//			Measure measure = measureList.get(i);
//			rightBar = getBarLine(measure.getBarlines(), "right");
//		}
//		return rightBar;
//	}
//	
//	public static BarLine getBarLine(List<BarLine> barlines,  String location) {
//    	if (barlines != null) {
//			for (BarLine info : barlines) {
//				   if (info.getLocation().equals(location)) {
//					   return info;
//				   }
//			}
//		}
//    	return null;
//    }
//	
//	private static Boolean noteHasRepeatLeft() {
//		boolean result = false;
//		for (int i = 0; i < measureList.size(); i++) {
//			Measure measure = measureList.get(i);
//			if (getBarLine(measure.getBarlines(), "left") != null) {
//				return true;
//			}
//		}
//		return result;
//	}
//	private static Boolean noteHasRepeatRight() {
//		boolean result = false;
//		for (int i = 0; i < measureList.size(); i++) {
//			Measure measure = measureList.get(i);
//			if (getBarLine(measure.getBarlines(), "right") != null) {
//				return true;
//			}
//		}
//		return result;
//	}

//	public static void main(String[] args) {
//		Player player = new Player();
//		Pattern vocals = new Pattern();
//		
////		vocals.add("G4qi G3s A3is B2is D+F+A+C");
////		vocals.add("G5is E5i | G5s E5q | G5q E5i D5q C5h");
////		vocals.add("G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs D5q rs C5h Rs");
////		vocals.add("Rh G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs D5q rs C5h Rs "
////				+ "C4i A5q G5isa50d0 Rs A5s E5i D5is Rs C5qis "
////				+ "Rqi A4s G5i E5i Rs | G5is Rs E5q | D5is C5i Rs C5q G4q Ri");
////
////		vocals.add("Rh G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs D5q rs C5h Rs");
////		
//	    vocals.add("E2i B2i E3i G3i B3i E4i B3i G3i  E4w+B3w+G3w+E3w+B2w+E2w");   // Guitar example
//		vocals.setInstrument("GUITAR");
//		
////		vocals.add("V9 [Acoustic_Snare] V9 [Lo_Mid_Tom] V9 [Hand_Clap]+[Crash_Cymbal_1]");  // Drum note
//		
////		ChordProgression cp = new ChordProgression("C D E F G A B");
////				
////		cp.setKey("C");
////		cp.distribute("7");
//
//		player.play(vocals);
//
//	}

}