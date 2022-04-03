package GUI;

import java.util.List;

import javax.sound.midi.Sequence;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import models.Part;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.barline.BarLine;
import models.measure.note.Chord;
import models.measure.note.Grace;
import models.measure.note.Note;
import models.measure.note.notations.Tied;

public class MusicPlayer {

	private ScorePartwise scorePartwise;
	@FXML
	private Pane pane;
	private List<Measure> measureList;
	
	
	public MusicPlayer() {

	}

	public MusicPlayer(ScorePartwise scorePartwise) {
		super();
		this.scorePartwise = scorePartwise;
		this.measureList = this.scorePartwise.getParts().get(0).getMeasures();
	}
	
	public Sequence getGuitarString() {
		Player player = new Player();
		StringBuilder vocals = new StringBuilder("V0 I[Guitar] ");
		StringBuilder repeat = new StringBuilder();
		boolean addRepeat = false;

		for (Part i : scorePartwise.getParts()) {
			for (Measure j : measureList) {
				if (getBarLineInfo(j.getBarlines(), "left") != null) {
					addRepeat = true;
				}
				if (j.getNotesBeforeBackup() != null) {
					for (Note n : j.getNotesBeforeBackup()) {
						String format = "%s%s%s";
						String ns = new String();
						
						if (n.getRest() != null) {
							ns = "R";
						} else {
							if (n.getGrace() != null) {
								n.getGrace();
								continue;
							}else {
								int octave = n.getPitch().getOctave();
								ns = n.getPitch().getStep() + getAlter(n)+ octave;
							}
						}

						
						String dur = addDuration(n);
						String tie = getDurationWithTies(dur, n);
						String chord = createChord(n.getChord());

						vocals.append(String.format(format, chord, ns, tie));

						if(addRepeat) {
							repeat.append(String.format(format, chord, ns, tie));
						}

					}

				}
				BarLine rightBar = getBarLineInfo(j.getBarlines(), "right");
				if(rightBar != null) {
					int time = Integer.valueOf(rightBar.getRepeat().getTimes());
					vocals.append(repeat.toString().repeat(time -1));
					addRepeat = false;
				}
			}
		}

		System.out.println("Guitar: " + vocals.toString());

		return player.getSequence(vocals.toString());
	}
	
	public Sequence getDrumString() {
		Player player = new Player();
		StringBuilder vocals = new StringBuilder("V9 ");
		StringBuilder repeat = new StringBuilder();
		boolean addRepeat = false;

		for (Part i : scorePartwise.getParts()) {
			for (Measure j : measureList) {
				if (getBarLineInfo(j.getBarlines(), "left") != null) {
					addRepeat = true;
				}
				if (j.getNotesBeforeBackup() != null) {
					for (Note n : j.getNotesBeforeBackup()) {
						String format = "%s%s%s";
						String drumId = "";
						String ns = new String();
						
						if (n.getRest() != null) {
							ns = "R";
						} else {
							drumId = n.getInstrument().getId();
							ns = "[" + getDrumNoteFullName(drumId) + "]";
						}

						
						String dur = addDuration(n);
						String chord = createChord(n.getChord());

						vocals.append(String.format(format, chord, ns, dur));

						if(addRepeat) {
							repeat.append(String.format(format, chord, ns, dur));
						}

					}

				}
				BarLine rightBar = getBarLineInfo(j.getBarlines(), "right");
				if(rightBar != null) {
					int time = Integer.valueOf(rightBar.getRepeat().getTimes());
					vocals.append(repeat.toString().repeat(time -1));
					addRepeat = false;
				}
			}
		}

		System.out.println("Drum: " + vocals.toString());

		return player.getSequence(vocals.toString());
	}
	
	public Sequence getBassString() {
		Player player = new Player();
		StringBuilder vocals = new StringBuilder("T180 V0 I[Acoustic_Bass] ");
		StringBuilder repeat = new StringBuilder();
		boolean addRepeat = false;

		for (Part i : scorePartwise.getParts()) {
			for (Measure j : measureList) {
				if (getBarLineInfo(j.getBarlines(), "left") != null) {
					addRepeat = true;
				}
				if (j.getNotesBeforeBackup() != null) {
					for (Note n : j.getNotesBeforeBackup()) {
						String format = "%s%s%s";
						String ns = new String();
						
						if (n.getRest() != null) {
							ns = "R";
						} else {
							if (n.getGrace() != null) {
								n.getGrace();
								continue;
							}else {
								int octave = n.getPitch().getOctave();
								ns = n.getPitch().getStep() + getAlter(n)+ octave;
							}
						}

						
						String dur = addDuration(n);
						String tie = getDurationWithTies(dur, n);
						String chord = createChord(n.getChord());

						vocals.append(String.format(format, chord, ns, tie));

						if(addRepeat) {
							repeat.append(String.format(format, chord, ns, tie));
						}

					}

				}
				BarLine rightBar = getBarLineInfo(j.getBarlines(), "right");
				if(rightBar != null) {
					int time = Integer.valueOf(rightBar.getRepeat().getTimes());
					vocals.append(repeat.toString().repeat(time -1));
					addRepeat = false;
				}
			}
		}

		System.out.println("Bass: " + vocals.toString());

		return player.getSequence(vocals.toString());
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
	
	public String getDurationWithTies(String duration, Note note) {

		if (note.getNotations() != null && note.getNotations().getTieds() != null) {
			String s = duration;
			for (Tied i : note.getNotations().getTieds()) {

				if (i.getType().equals("start")) s += "-";
				if (i.getType().equals("stop")) s = "-" + s;

			}
			return s;
		}
		return duration;
	}
	
	public String createChord(Chord chord) {
		if (chord != null) {
			return "+";
		}
		return " ";
	}
	
	public String getAlter(Note note) {
		String res = "";
		Integer alter = note.getPitch().getAlter();
		
		if (alter == null) {
			res = "";
		}else if (alter == -1) {
			res = "b";
		}else if (alter == 1) {
			res = "#";
		}
		return res;
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
			fullName = "Lo_Floor_Tom";
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
	
	private BarLine getBarLineInfo(List<BarLine> barlines,  String location) {
    	if (barlines != null) {
			for (BarLine info : barlines) {
				   if (info.getLocation().equals(location)) {
					   return info;
				   }
			}
		}
    	return null;
		
    }





}