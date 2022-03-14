package player;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.jfugue.midi.MidiParserListener;
import org.jfugue.player.Player;
import org.staccato.StaccatoParser;

import converter.Score;
import custom_exceptions.TXMLException;
import models.measure.Measure;
import models.measure.note.Note;

public class StringInstruments {
	private String wholeString = "";
	public Player player;
	public StaccatoParser parser = new StaccatoParser();
	public MidiParserListener midilistener = new MidiParserListener();

	public void setupStringInstruments(Score score) throws TXMLException {
		player = new Player();

		wholeString += "T180 I[Guitar] ";

		for (Measure measure : score.getModel().getParts().get(0).getMeasures()) {
			if (measure.getNotesBeforeBackup() != null) {
				for (int i = 0; (i < measure.getNotesBeforeBackup().size()); i++) {					
					if (measure.getNotesBeforeBackup().get(i).getChord() != null) {
						wholeString += String.format("+%s%s", checkIrregularNotes(measure.getNotesBeforeBackup().get(i)), getDurationMIDI(measure.getNotesBeforeBackup().get(i).getDuration()));
					} else if (measure.getNotesBeforeBackup().get(i).getChord() == null) {
						wholeString += String.format(" %s%s", checkIrregularNotes(measure.getNotesBeforeBackup().get(i)), getDurationMIDI(measure.getNotesBeforeBackup().get(i).getDuration()));
					}
				}
			}
		}
		
		parser.addParserListener(midilistener);
		parser.parse(wholeString);

//		player.play(midilistener.getSequence());
	}

	public String checkIrregularNotes(Note note) {
		String result = note.getPitch().getStep();

		if(note.getPitch().getAlter() != null) {
			if(note.getPitch().getAlter() == 1) {
				result += "#";
			}
			else if(note.getPitch().getAlter() == -1) {
				result += "b";
			}
		}
		result += note.getPitch().getOctave();
		return result;
	}

	public String getDurationMIDI(int duration) {
		String durationMIDI = "";

		// get duration of note
		switch (duration) {
		case 1 / 2:
			durationMIDI = "O";
			break;
		case 1:
			durationMIDI = "X";
			break;
		case 2:
			durationMIDI = "T";
			break;
		case 4:
			durationMIDI = "S";
			break;
		case 8:
			durationMIDI = "I";
			break;
		case 16:
			durationMIDI = "Q";
			break;
		case 32:
			durationMIDI = "H";
			break;
		case 64:
			durationMIDI = "W";
			break;
		default:
			break;
		}
		return durationMIDI;
	}

	// Play Music Player
	public void play() throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
		player.getManagedPlayer().start(midilistener.getSequence());
	}

	// Pause Music Player
	public void pause() throws MidiUnavailableException, InvalidMidiDataException {
		if (player.getManagedPlayer().isPlaying() || !player.getManagedPlayer().isFinished()) {
			player.getManagedPlayer().pause();
		}
	}

	// Rewind Music Player
	public void rewind() {
		player.getManagedPlayer().seek(0);
	}

	// Stop Music Player
	public void stop() throws MidiUnavailableException {
		player.getManagedPlayer().reset();
		player.getManagedPlayer().finish();
	}
}
