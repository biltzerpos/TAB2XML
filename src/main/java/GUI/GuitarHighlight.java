package GUI;


import java.util.ArrayList;
import java.util.List;

import instruments.Guitar;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class GuitarHighlight extends Thread {
	List<Rectangle> r;
	PreviewMusic pm;
	List <Double> dur;
	Guitar g;
	@FXML
	Pane pane;
	private boolean playing;
	
	public GuitarHighlight(Guitar g, List<Rectangle> r, ArrayList<Double> dur) {
		this.r=r;
		this.dur=dur;
		this.g=g;
	}
	
	
	@Override
	public void run() {
		//System.out.println("Highlight thread running");
		for (int i = 0; i < r.size() && g.playing; i++) {
			r.get(i).setStyle("-fx-stroke: red;");
			if (dur.get(i) != null) {
				try {
					Thread.sleep(dur.get(i).intValue()*2);
					//System.out.println("Sleeping for"+dur.get(i).intValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				r.get(i).setStyle("-fx-stroke: TRANSPARENT;");
			}
		}	
	}
}
