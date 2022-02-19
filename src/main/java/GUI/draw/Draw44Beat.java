package GUI.draw;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Draw44Beat {
	
	@FXML private Pane pane; 
	private double x; 
	private double y;
	
	public Draw44Beat(Pane pane, double x, double y) {
		super();
		this.pane = pane;
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		String label = "44";
		for(int i = 0; i < label.length(); i++) {
			Box box = new Box(15, 15, 1); 
			box.setTranslateX(x+7);
			box.setTranslateY(y-7);
			box.setTranslateZ(-15);
			PhongMaterial boxColor = new PhongMaterial();
			boxColor.setSpecularColor(Color.WHITE);
			boxColor.setDiffuseColor(Color.WHITE);
			boxColor.setSpecularPower(0);
			box.setMaterial(boxColor);
			pane.getChildren().add(box);
			
			char c = label.charAt(i);
			String s = Character.toString(c);
			Text text = new Text(this.x, this.y,  s);
			text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
			pane.getChildren().add(text);
			y += 20;
			
		}
	}
	
	

}
