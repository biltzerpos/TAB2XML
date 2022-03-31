package GUI;

import java.util.List;

import javax.sound.midi.Sequence;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.note.Note;

public class MusicPlayer{

	private ScorePartwise scorePartwise;
	@FXML
	private Pane pane;
	private List<Measure> measureList;
	
	
	public MusicPlayer() {

	}

	public MusicPlayer(ScorePartwise scorePartwise, Pane pane) {
		super();
		this.scorePartwise = scorePartwise;
		this.pane = pane;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
	}
	
	
	public Sequence getGuitarString() {
		Player player = new Player();
		Pattern vocals = new Pattern();
		String noteSteps = "";
		int voice = 0;

		for (int i = 0; i < measureList.size(); i++) {
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();

			for (int j = 0; j < noteList.size(); j++) {
				String ns = new String();
				Note note = noteList.get(j);
				//					Grace gra = note.getGrace();
				//					List<Dot> dot = note.getDots();
				//					Rest res = note.getRest();
				//					Integer alt = note.getPitch().getAlter();
				int octave = note.getPitch().getOctave();
				String oct = Integer.toString(octave);
				String dur = addDuration(note);
				voice = note.getVoice();
				ns = note.getPitch().getStep() + oct + dur;
				// System.out.println(" gra: " + gra + " dot: " + dot + " res: " + res + " alt:
				// " + alt);

				if (!noteHasChord(note) && !noteHasTie(note)) {
					noteSteps += " " + ns;
				} else if (noteHasChord(note)) {
					noteSteps += "+" + ns;
				} else if (noteHasTie(note)) {
					noteSteps += "-" + ns;
				} else if (noteHasRest(note)) {
					noteSteps += " " + note.getPitch().getStep() + "R" + oct + dur;
				}
			}
		}

		vocals.add(noteSteps);
		System.out.println("Guitar: " + vocals.toString());
		vocals.setInstrument("GUITAR");
		vocals.setVoice(voice);
		
		return player.getSequence(vocals);

	}
	
	public Sequence getDrumString() {
		Player player = new Player();
		Pattern vocals = new Pattern();
		String drumNote = "V9 ";
		int voice = 0;

		for (int i = 0; i < measureList.size(); i++) {
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();

			for (int j = 0; j < noteList.size(); j++) {
				String drumId = "";
				String ns = new String();
				Note note = noteList.get(j);
				voice = note.getVoice();
				drumId = note.getInstrument().getId();
				ns = "[" + getDrumNoteFullName(drumId) + "]";
				String dur = addDuration(note);

				if (note.getChord() == null) {
					drumNote += " " + ns + dur;
				}else {
					drumNote += "+" + ns + dur;
				}
			}
		}

		vocals.add(drumNote);
		System.out.println("Drum: " + vocals.toString());
		vocals.setVoice(voice);
		
		return player.getSequence(vocals);
	}
	
	// This method plays the notes
	public void playBassNote() {
		Player player = new Player();
		Pattern vocals = new Pattern();
		String noteSteps = "";
		int voice = 0;

		for (int i = 0; i < measureList.size(); i++) {
			Measure measure = measureList.get(i);
			List<Note> noteList = measure.getNotesBeforeBackup();

			for (int j = 0; j < noteList.size(); j++) {
				String ns = new String();
				Note note = noteList.get(j);
				int octave = note.getPitch().getOctave();
				String oct = Integer.toString(octave);
				String dur = addDuration(note);
				voice = note.getVoice();
				ns = note.getPitch().getStep() + oct + dur;
				// System.out.println(" gra: " + gra + " dot: " + dot + " res: " + res + " alt:
				// " + alt);

				if (!noteHasChord(note) && !noteHasTie(note)) {
					noteSteps += " " + ns;
				} else if (noteHasChord(note)) {
					noteSteps += "+" + ns;
				} else if (noteHasTie(note)) {
					noteSteps += "-" + ns;
				} else if (noteHasRest(note)) {
					noteSteps += " " + note.getPitch().getStep() + "R" + oct + dur;
					;
				}
			}
		}

		vocals.add(noteSteps);
		System.out.println("Bass: " + vocals.toString());
		vocals.setInstrument("Acoustic_Bass");
		vocals.setVoice(voice);
		vocals.setTempo(120);
		player.play(vocals);

	}

	// returns string representation of a drum duration for a given note
	private String addDuration(Note note) {
		String res = "";
		String type = note.getType();

		if (type.equals("whole")) {
			res = "w";
		} else if (type.equals("half")) {
			res = "h";
		} else if (type.equals("quarter")) {
			res = "q";
		} else if (type.equals("eighth")) {
			res = "i";
		} else if (type.equals("16th")) {
			res = "s";
		} else if (type.equals("32nd")) {
			res = "t";
		} else if (type.equals("64th")) {
			res = "x";
		} else if (type.equals("128th")) {
			res = "o";
		} else {
			res = "q"; // Make it default for now
		}

		return res;
	}
//	// returns string representation of a guitar duration for a given note
//	public String getDuration(Note note) {
//		String res = "";
//		int duration = note.getDuration();
//		if (duration == 8) {
//			res = "i";
//		}
//		if (duration == 64) {
//			res = "w";
//		}
//		return res;
//	}
	

	private Boolean noteHasTie(Note n) {
		Boolean result = n.getNotations().getTieds() == null ? false : true;
		return result;
	}

	private Boolean noteHasRest(Note n) {
		Boolean result = n.getRest() == null ? false : true;
		return result;
	}

	// returns true if the guitar note has chord element
	private Boolean noteHasChord(Note n) {
		Boolean result = n.getChord() == null ? false : true;
		return result;
	}
	
	private String getDrumNoteFullName(String Id) {
		String fullName = "";

		if(Id == "P1-I50") {
			fullName = "Crash_Cymbal_1";
		} else if(Id == "P1-I36"){
			fullName = "Bass_Drum";
		} else if(Id == "P1-I39"){
			fullName = "Acoustic_Snare";
		} else if(Id == "P1-I43"){
			fullName = "Closed_Hi_Hat";
		} else if(Id == "P1-I47"){
			fullName = "Open_Hi_Hat";
		} else if(Id == "P1-I52"){
			fullName = "Ride_Cymbal_1";
		} else if(Id == "P1-I54"){
			fullName = "Ride_Bell";
		} else if(Id == "P1-I53"){
			fullName = "Chinese_Cymbal_1";
		} else if(Id == "P1-I48"){
			fullName = "Lo_Mid_Tom";
		} else if(Id == "P1-I46"){
			fullName = "Lo_Tom";
		} else if(Id == "P1-I44"){
			fullName = "High_Floor_Tom";
		} else if(Id == "P1-I42"){
			fullName = "Low_Floor_Tom";
		} else if(Id == "P1-I45"){
			fullName = "Pedal_Hi_Hat";
		} else {
			fullName = "Bass_Drum"; // Make it default for now
		}

		return fullName;
	}

	public ScorePartwise getScorePartwise() {
		return scorePartwise;
	}

	public void setScorePartwise(ScorePartwise scorePartwise) {
		this.scorePartwise = scorePartwise;
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}


	// returns a list of measures
	public List<Measure> getMeasureList() {
		return measureList;
	}

	public void setMeasureList(List<Measure> measureList) {
		this.measureList = measureList;
	}





}