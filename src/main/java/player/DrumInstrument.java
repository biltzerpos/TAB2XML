package player;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfugue.midi.MidiParserListener;
import org.jfugue.player.Player;
import org.staccato.StaccatoParser;

import converter.Score;
import models.measure.Measure;
import utility.DrumPiece;
import utility.DrumPieceInfo;
import utility.DrumUtils;

public class DrumInstrument {
	private String drumInfo = "";
	private String wholeString = "";
	private Player player;

	public void playDrums(List<Measure> measureList, Score score) {
		player = new Player();

		Map<DrumPiece, DrumPieceInfo> map = DrumUtils.drumSet;
		wholeString += "T120 V[PERCUSSION]";

		for (Measure measure : measureList) {
			if (measure.getNotesBeforeBackup() != null) {
				for (int i = 0; (i < measure.getNotesBeforeBackup().size()); i++) {
					for (Entry<DrumPiece, DrumPieceInfo> drumSetElement : DrumUtils.drumSet.entrySet()) {
						// If the note is an valid enum, it exists in our set of notes
						if (measure.getNotesBeforeBackup().get(i).getInstrument().getId()
								.equals(drumSetElement.getValue().getMidiID())) {
							// append enum to string
							if (measure.getNotesBeforeBackup().get(i).getChord() != null) {
								wholeString += String.format("+[%s]%s", drumSetElement.getKey(),
										getDurationMIDI(measure.getNotesBeforeBackup().get(i).getDuration()));
							} else if (measure.getNotesBeforeBackup().get(i).getChord() == null) {
								wholeString += String.format(" [%s]%s", drumSetElement.getKey(),
										getDurationMIDI(measure.getNotesBeforeBackup().get(i).getDuration()));
							}
							break;
						}
					}
				}
			}
		}

		System.out.println(wholeString);

		StaccatoParser parser = new StaccatoParser();
		MidiParserListener midilistener = new MidiParserListener();
		parser.addParserListener(midilistener);
		parser.parse(wholeString);

		player.play(midilistener.getSequence());
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
}