package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Sequencer;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class HighlightNote extends Thread {
	List<Rectangle> r;
	List <Double> dur;
	@FXML
	Pane pane;
	
	public HighlightNote (List<Rectangle> r, ArrayList<Double> dur) {
		this.r=r;
		this.dur=dur;
	}
	
	@Override
	public void run() {
		System.out.println("Highlight thread running");
		for (int i = 0; i < r.size(); i++) {
			r.get(i).setStyle("-fx-stroke: red;");
			if (dur.get(i) != null) {
				try {
					Thread.sleep(dur.get(i).intValue());
					System.out.println("Sleeping for"+dur.get(i).intValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				r.get(i).setStyle("-fx-stroke: TRANSPARENT;");
			}
		}
		
		}
}
