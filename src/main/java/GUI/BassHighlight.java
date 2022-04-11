package GUI;

import java.util.ArrayList;
import java.util.List;

import instruments.Bass;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class BassHighlight extends Thread {
	List<Rectangle> r;
	PreviewMusic pm;
	List <Double> dur;
	Bass b;
	@FXML
	Pane pane;
	public BassHighlight (Bass b, List<Rectangle> r, ArrayList<Double> dur) {
		this.r=r;
		this.dur=dur;
		this.b=b;
	}

	
	@Override
	public void run() {
		//System.out.println("Highlight thread running");
		for (int i = 0; i < r.size() && b.playing; i++) {
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