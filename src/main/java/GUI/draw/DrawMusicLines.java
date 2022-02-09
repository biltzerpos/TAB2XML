package GUI.draw;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

// this class draws music lines (group of 6 lines) with the lenght 50; 
public class DrawMusicLines extends Node{
	
	@FXML private Pane pane;
	private double startY;
	private double startX;
	private final double lenght = 50.0;
	private MLine musicLine;
	private List<MLine> musicLineList; 

	public DrawMusicLines() {
		
	}
	public DrawMusicLines(Pane pane, double x, double y) {
		super();
		this.pane = pane;
		this.startY = y;
		this.startX = x;
		this.musicLineList = new ArrayList<MLine>();
		
	}

	//Method that does the actual drawing
	public void draw() {
		for (int i = 0; i < 6; i++) {
        	musicLine = new MLine(pane, this.startX, this.startY, this.startX + this.lenght, this.startY, i+1);
        	this.startY = this.startY + 10;
        	musicLineList.add(musicLine);
    	}
	}

	public List<MLine> getMusicLineList() {
		return musicLineList;
	}
	public void setMusicLineList(List<MLine> musicLineList) {
		this.musicLineList = musicLineList;
	}
	public MLine getMusicLine() {
		return musicLine;
	}
	public void setMusicLine(MLine musicLine) {
		this.musicLine = musicLine;
	}
	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getLenght() {
		return lenght;
	}
	
	
	
	
	
	
	

	
	
}
