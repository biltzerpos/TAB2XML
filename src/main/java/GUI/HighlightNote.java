package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import instruments.Drumset;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class HighlightNote extends Thread {
	List<Rectangle> r;
	PreviewMusic pm;
	List <Double> dur;
	Drumset d;
	@FXML
	Pane pane;
	private boolean playing;
	private int duration;
	public HighlightNote (Drumset d, List<Rectangle> r, ArrayList<Double> dur) {
		this.r=r;
		this.dur=dur;
		this.d=d;
	}

	
	@Override
	public void run() {
		//System.out.println("Highlight thread running");
		for (int i = 0; i < r.size() && d.playing; i++) {
			r.get(i).setStyle("-fx-stroke: red;");
			if (dur.get(i) != null) {
				try {
					Thread.sleep(dur.get(i).intValue());
					//System.out.println("Sleeping for"+dur.get(i).intValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				r.get(i).setStyle("-fx-stroke: TRANSPARENT;");
			}
		}	
	}
}
