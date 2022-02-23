package instruments;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

public class JfugueTest {

	public static void main(String[] args) {
		Player player = new Player();
		
//		Pattern pattern = new Pattern("C D E F G A B");
//		Pattern pattern = new Pattern("G4qi G3s A3is B2is D+F+A+C");
//		Pattern pattern = new Pattern("G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs D5q rs C5h Rs");
//		Pattern pattern = new Pattern("G5is E5i | G5s E5q | G5q E5i D5q C5h");
//		player.play(pattern);
//
//		String music = "Rh G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs D5q rs C5h Rs "
//				+ "C4i A5q G5isa50d0 Rs A5s E5i D5is Rs C5qis "
//				+ "Rqi A4s G5i E5i Rs | G5is Rs E5q | D5is C5i Rs C5q G4q Ri";
//		
		Pattern vocals = new Pattern();
		vocals.add("Rh G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs D5q rs C5h Rs");
		vocals.add("C4i A5q G5isa50d0 Rs A5s E5i D5is Rs C5qis");
		vocals.setTempo(180).setInstrument("GUITAR");;
		
//		ChordProgression cp = new ChordProgression("C D E F G A B");
//				
//		cp.setKey("C");
//		cp.distribute("7");

		player.play(vocals);

	}

}