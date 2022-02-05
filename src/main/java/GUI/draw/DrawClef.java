package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.measure.attributes.Clef;

public class DrawClef {
	private Clef clef;
	@FXML private Pane pane;
	public DrawClef(Pane pane, Clef clef) {
		super();
		this.clef = clef;
		this.pane = pane;
		draw(pane, clef, 10, 15);
	}
	
	
	private void draw(Pane pane, Clef clef, double x, double y) {
		String name = clef.getSign();
		for(int i = 0; i <name.length(); i++) {
			char c = name.charAt(i);
			String s = Character.toString(c);
			Text text = new Text(x, y,  s);
			text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
			pane.getChildren().add(text);
			y += 15;
		}
	}
		


	public Clef getClef() {
		return clef;
	}
	public void setClef(Clef clef) {
		this.clef = clef;
	}
	public Pane getPane() {
		return pane;
	}
	public void setPane(Pane pane) {
		this.pane = pane;
	}
	
	

}
