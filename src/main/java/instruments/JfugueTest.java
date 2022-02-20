package instruments;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class JfugueTest {

	public static void main(String[] args) {
		Player player = new Player();
		//Pattern pattern = new Pattern("C D E F G A B");
		Pattern pattern = new Pattern("G4qi G3s A3is B2is D+F+A+C");
		player.play(pattern);
		System.exit(0); // If using Java 1.4 or lower

	}

}
