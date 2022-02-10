package player;

import converter.Score;
import custom_exceptions.TXMLException;
import models.ScorePartwise;
import models.part_list.PartList;

public class MXLPlayer{
	private ScorePartwise score;

	public MXLPlayer(Score score) throws TXMLException {
		this.score = score.getModel();
	}
	public void play(int partID,int measureID, int duration){

	}
	public void playPart(int measureID, int duration, PartList partList){

	}
	public void playMeasure(int duration){

	}
	public void playNote(){

	}

}
