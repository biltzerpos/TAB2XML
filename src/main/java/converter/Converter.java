package converter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import GUI.MainViewController;
import utility.MusicXMLCreator;
import utility.ValidationError;

public class Converter {

	private Score score;
	private MusicXMLCreator mxlc;
	private MainViewController mvc;
	
	public Converter(MainViewController mvc) {
		this.mvc = mvc;
	}
	
	public void update() {
		dummyMethod1();
		score = new Score(mvc.mainText.getText());
		mxlc = new MusicXMLCreator(score);
	}
	
	public String getMusicXML() {
		dummyMethod2();
		return mxlc.generateMusicXML();
	}
	
	public Score getScore() {
		dummyMethod1();
		return score;
	}
	
	public List<ValidationError> validate() {
		return score.validate();
	}
    public void saveMusicXMLFile(File file) {
    	dummyMethod1();
    	
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(mxlc.generateMusicXML());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void dummyMethod1() {
    	System.out.println("Hello World");
    }
    
    private void dummyMethod2() {
    	System.out.println("Lol");
    }
}
