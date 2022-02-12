package player;

import converter.Score;
import custom_exceptions.TXMLException;
import models.Part;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.attributes.Attributes;
import models.part_list.PartList;
import models.part_list.ScorePart;
import org.jfugue.player.Player;

import java.util.HashMap;
import java.util.List;

public class MXLPlayer{
	private ScorePartwise score;
	private Player player = new Player();
	private HashMap<String,ScorePart> scorePartMap = new HashMap<>();
	public MXLPlayer(Score score) throws TXMLException {
		this.score = score.getModel();
	}
	/**
	 * this method will play music from given duration.
	 *
	 * @param partID which part should player start
	 * @param measureID which measure should player start
	 * @param duration  when should player start in a measure.
	 * */
	public void play(int partID,int measureID, int duration){
		initPartList();
		StringBuilder musicString = new StringBuilder();
		int partCount = 0;
		for (Part part:score.getParts()){
			if (partCount>partID){
				musicString.append(getPart(part,-1,-1));
			}else if (partCount==partID){
				musicString.append(getPart(part,measureID,duration));
			}
			partCount++;
		}
		player.play(musicString.toString());
	}
	public void initPartList(){
		List<ScorePart> list = score.getPartList().getScoreParts();
		for (ScorePart scorePart:list){
			scorePartMap.put(scorePart.getId(),scorePart);
		}
	}
	public String getPart(Part part,int measureID, int duration){
		StringBuilder musicString = new StringBuilder();
			int measureCount = 0;
			for (Measure measure:part.getMeasures()){
				if (measureCount>measureID){
					musicString.append(getMeasure(measure,part.getId(),-1));
				}else if (measureCount==measureID){
					musicString.append(getMeasure(measure,part.getId(),duration));
				}
				measureCount++;
			}

		return musicString.toString();
	}
	public String getMeasure(Measure measure,String partID,int duration){
		StringBuilder musicString = new StringBuilder();

		return musicString.toString();
	}
	public String getNote(Attributes attributes){
		StringBuilder musicString = new StringBuilder();

		return musicString.toString();
	}

}
