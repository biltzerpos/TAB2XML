package player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

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

		/*
		kick "P1-I36", "Bass Drum 1", "F", 4);
		snare  "P1-I39", "Snare", "C", 5);
		closedHiHat  "P1-I43", "Closed Hi-Hat", "G", 5);
		openHiHat  "P1-I47", "Open Hi-Hat", "E", 5);
		ride  "P1-I52", "Ride Cymbal 1", "F", 5);
		rideBell  "P1-I54", "Ride Bell", "F", 5);
		crash "P1-I50", "Crash Cymbal 1", "A", 5);
		china  "P1-I53", "Chinese Cymbal 1", "C", 6);
		highTom  "P1-I48", "Low-Mid Tom", "E", 5);
		midTom  "P1-I46", "Low Tom", "D", 5);
		floorTom  "P1-I44", "High Floor Tom", "A", 4);
		lowFloorTom  "P1-I42", "Low Floor Tom", "G", 4);
		pedalHiHat  "P1-I45", "Pedal Hi-Hat", "D", 4);

		drumSet.put(DrumPiece.Bass_Drum_1, kick);
		drumSet.put(DrumPiece.Snare, snare);
		drumSet.put(DrumPiece.Open_Hi_Hat, openHiHat);
		drumSet.put(DrumPiece.Closed_Hi_Hat, closedHiHat);
		drumSet.put(DrumPiece.Ride_Cymbal_1, ride);
		drumSet.put(DrumPiece.Ride_Bell, rideBell);
		drumSet.put(DrumPiece.Crash_Cymbal_1, crash);
		drumSet.put(DrumPiece.Chinese_Cymbal, china);
		drumSet.put(DrumPiece.Low_Mid_Tom, highTom);
		drumSet.put(DrumPiece.Low_Tom, midTom);
		drumSet.put(DrumPiece.High_Floor_Tom, floorTom);
		drumSet.put(DrumPiece.Low_Floor_Tom, lowFloorTom);
		drumSet.put(DrumPiece.Pedal_Hi_Hat, pedalHiHat);
		 */

		Map<DrumPiece, DrumPieceInfo> map = DrumUtils.drumSet;

		String str = "T120 V9 ";
		wholeString += str;

		for(Measure measure : measureList) {
			for(int i = 0; i < measure.getNotesBeforeBackup().size(); i++) {	
				String noteInstrum = "";

				for(Entry<DrumPiece, DrumPieceInfo> drumPieceInfo : DrumUtils.drumSet.entrySet()) {
					if(measure.getNotesBeforeBackup().get(i).getInstrument().getId().equals(drumPieceInfo.getValue().getMidiID())) {
						noteInstrum += drumPieceInfo.getKey().toString();
						wholeString += "[" + noteInstrum + "]"; 
						break;
					}
				}

				
				switch (measure.getNotesBeforeBackup().get(i).getDuration()) {
				case 1/2:
					wholeString += "0";
					break;
				case 1:
					wholeString += "X";
					break;
				case 2:
					wholeString += "T";
					break;
				default:
					break;
				}
				
				
//				if(noteLengthList.get(i).equals(64)) {
//					total += "W";
//				}
//
//				else if(noteLengthList.get(i).equals(32)) {
//					total += "H";
//				}
//
//				else if(noteLengthList.get(i).equals(16)) {
//					total += "Q";
//				}
//
//				else if(noteLengthList.get(i).equals(8)) {
//					total += "I";
//				}
//
//				else if(noteLengthList.get(i).equals(4)) {
//					total += "S";
//				}
//
//				else if(noteLengthList.get(i).equals(2)) {
//					total += "T";
//				}
//
//				else if(noteLengthList.get(i).equals(1)) {
//					total += "X";
//				}
//
//				else if(noteLengthList.get(i).equals(1/2)) {
//					total += "O";
//				}
//
//				if((i+1)<chordList.size() && chordList.get(i+1)==0) 
//				{
//					total+="+";
//				}
//
//				else {
//					total += " ";
//				}
//
//				if(nNPMCounter<nNPM.size() && i == (nNPM.get(nNPMCounter) - 1)) {
//					total += "| ";
//					nNPMCounter ++;
//				}

			}
		}

		System.out.println(wholeString);
		
		Sequence sequence = new
		
		player.play(wholeString);
		//		nNPMCounter = 0;
	}
}
