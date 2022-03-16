package design2;

import java.io.IOException;

import converter.Score;
import custom_exceptions.TXMLException;
import models.measure.note.Note;
import pdfbox.pdfbuilder;
import utility.DrumPiece;

public class NoteIdentifier {
	public int identifyNote(String instr, String pitchFret) throws IOException, TXMLException {
		int offset = 0;

		if(instr.equals("Guitar")) {
			switch (pitchFret) {
			case "E2": // 1
				offset = Offset.E2offsety.offset();
				break;
			case "F2": // 2
				offset = Offset.F2offsety.offset();
				break;
			case "G2": // 3
				offset = Offset.G2offsety.offset();
				break;
			case "A2": // 4
				offset = Offset.A2offsety.offset();
				break;
			case "B2": // 5
				offset = Offset.B2offsety.offset();
				break;
			case "C3": // 6
				offset = Offset.C3offsety.offset();
				break;
			case "D3": // 7
				offset = Offset.D3offsety.offset();
				break;
			case "E3": // 8
				offset = Offset.E3offsety.offset();
				break;
			case "F3": // 9
				offset = Offset.F3offsety.offset();
				break;
			case "G3": // 10
				offset = Offset.G3offsety.offset();
				break;
			case "A3": // 11
				offset = Offset.A3offsety.offset();
				break;
			case "B3": // 12
				offset = Offset.B3offsety.offset();
				break;
			case "C4": // 13
				offset = Offset.C4offsety.offset();
				break;
			case "D4": // 14
				offset = Offset.D4offsety.offset();
				break;
			case "E4": // 15
				offset = Offset.E4offsety.offset();
				break;
			case "F4": // 16
				offset = Offset.F4offsety.offset();
				break;
			case "G4": // 17
				offset = Offset.G4offsety.offset();
				break;
			case "A4": // 18
				offset = Offset.A4offsety.offset();
				break;
			case "B4": // 19
				offset = Offset.B4offsety.offset();
				break;
			case "C5": // 20
				offset = Offset.C5offsety.offset();
				break;
			case "D5": // 21
				offset = Offset.D5offsety.offset();
				break;
			case "E5": // 22
				offset = Offset.E5offsety.offset();
				break;
			case "F5": // 23
				offset = Offset.F5offsety.offset();
				break;
			case "G5": // 24
				offset = Offset.G5offsety.offset();
				break;
			case "A5": // 25
				offset = Offset.A5offsety.offset();
				break;
			case "B5": // 26
				offset = Offset.B5offsety.offset();
				break;
			case "C6": // 27
				offset = Offset.C6offsety.offset();
				break;
			case "D6": // 28
				offset = Offset.D6offsety.offset();
				break;
			case "E6": // 29
				offset = Offset.E6offsety.offset();
				break;
			default:
				break;
			}
		}
		else if(instr.equals("Drumset")) {
			switch (pitchFret) {
			case "P1-I36": // 1
				offset = DrumPiece.BASS_DRUM.getOffset();
				break;
			case "P1-I39": // 2
				offset = DrumPiece.ACOUSTIC_SNARE.getOffset();
				break;
			case "P1-I43": // 3
				offset = DrumPiece.CLOSED_HI_HAT.getOffset();
				break;
			case "P1-I47": // 4
				offset = DrumPiece.OPEN_HI_HAT.getOffset();
				break;
			case "P1-I52": // 5
				offset = DrumPiece.RIDE_CYMBAL_1.getOffset();
				break;
			case "P1-I54": // 6
				offset = DrumPiece.RIDE_BELL.getOffset();
				break;
			case "P1-I50": // 7
				offset = DrumPiece.CRASH_CYMBAL_1.getOffset();
				break;
			case "P1-I53": // 8
				offset = DrumPiece.CHINESE_CYMBAL.getOffset();
				break;
			case "P1-I48": // 9
				offset = DrumPiece.LO_MID_TOM.getOffset();
				break;
			case "P1-I46": // 10
				offset = DrumPiece.LO_TOM.getOffset();
				break;
			case "P1-I44": // 11
				offset = DrumPiece.HIGH_FLOOR_TOM.getOffset();
				break;
			case "P1-I42": // 12
				offset = DrumPiece.LO_FLOOR_TOM.getOffset();
				break;
			case "P1-I45": // 13
				offset = DrumPiece.PEDAL_HI_HAT.getOffset();
				break;
			default:
				break;
			}
		}
		return offset;
	}
	
	public int noteLines(String instr, String pitchFret) {
		int offset = 0;

		if(instr.equals("Guitar")) {
			switch (pitchFret) {
			case "E2": // 1
				offset = Offset.E2offsety.lines() + Offset.E2offsety.onLine;
				break;
			case "F2": // 2
				offset = Offset.F2offsety.lines() + Offset.F2offsety.onLine;
				break;
			case "G2": // 3
				offset = Offset.G2offsety.lines() + Offset.G2offsety.onLine;
				break;
			case "A2": // 4
				offset = Offset.A2offsety.lines() + Offset.A2offsety.onLine;
				break;
			case "B2": // 5
				offset = Offset.B2offsety.lines() + Offset.B2offsety.onLine;
				break;
			case "C3": // 6
				offset = Offset.C3offsety.lines() + Offset.C3offsety.onLine;
				break;
			case "D3": // 7
				offset = Offset.D3offsety.lines() + Offset.D3offsety.onLine;
				break;
			case "E3": // 8
				offset = Offset.E3offsety.lines() + Offset.E3offsety.onLine;
				break;
			case "F3": // 9
				offset = Offset.F3offsety.lines() + Offset.F3offsety.onLine;
				break;
			case "G3": // 10
				offset = Offset.G3offsety.lines() + Offset.G3offsety.onLine;
				break;
			case "A3": // 11
				offset = Offset.A3offsety.lines() + Offset.A3offsety.onLine;
				break;
			case "B3": // 12
				offset = Offset.B3offsety.lines() + Offset.B3offsety.onLine;
				break;
			case "C4": // 13
				offset = Offset.C4offsety.lines() + Offset.C4offsety.onLine;
				break;
			case "D4": // 14
				offset = Offset.D4offsety.lines() + Offset.D4offsety.onLine;
				break;
			case "E4": // 15
				offset = Offset.E4offsety.lines() + Offset.E4offsety.onLine;
				break;
			case "F4": // 16
				offset = Offset.F4offsety.lines() + Offset.F4offsety.onLine;
				break;
			case "G4": // 17
				offset = Offset.G4offsety.lines() + Offset.G4offsety.onLine;
				break;
			case "A4": // 18
				offset = Offset.A4offsety.lines() + Offset.A4offsety.onLine;
				break;
			case "B4": // 19
				offset = Offset.B4offsety.lines() + Offset.B4offsety.onLine;
				break;
			case "C5": // 20
				offset = Offset.C5offsety.lines() + Offset.C5offsety.onLine;
				break;
			case "D5": // 21
				offset = Offset.D5offsety.lines() + Offset.D5offsety.onLine;
				break;
			case "E5": // 22
				offset = Offset.E5offsety.lines() + Offset.E5offsety.onLine;
				break;
			case "F5": // 23
				offset = Offset.F5offsety.lines() + Offset.F5offsety.onLine;
				break;
			case "G5": // 24
				offset = Offset.G5offsety.lines() + Offset.G5offsety.onLine;
				break;
			case "A5": // 25
				offset = Offset.A5offsety.lines() + Offset.A5offsety.onLine;
				break;
			case "B5": // 26
				offset = Offset.B5offsety.lines() + Offset.B5offsety.onLine;
				break;
			case "C6": // 27
				offset = Offset.C6offsety.lines() + Offset.C6offsety.onLine;
				break;
			case "D6": // 28
				offset = Offset.D6offsety.lines() + Offset.D6offsety.onLine;
				break;
			case "E6": // 29
				offset = Offset.E6offsety.lines() + Offset.E6offsety.onLine;
				break;
			default:
				break;
			}
		}
		else if(instr.equals("Drumset")) {
//			switch (pitchFret) {
//			case "P1-I36": // 1
//				offset = DrumPiece.BASS_DRUM.getOffset();
//				break;
//			case "P1-I39": // 2
//				offset = DrumPiece.ACOUSTIC_SNARE.getOffset();
//				break;
//			case "P1-I43": // 3
//				offset = DrumPiece.CLOSED_HI_HAT.getOffset();
//				break;
//			case "P1-I47": // 4
//				offset = DrumPiece.OPEN_HI_HAT.getOffset();
//				break;
//			case "P1-I52": // 5
//				offset = DrumPiece.RIDE_CYMBAL_1.getOffset();
//				break;
//			case "P1-I54": // 6
//				offset = DrumPiece.RIDE_BELL.getOffset();
//				break;
//			case "P1-I50": // 7
//				offset = DrumPiece.CRASH_CYMBAL_1.getOffset();
//				break;
//			case "P1-I53": // 8
//				offset = DrumPiece.CHINESE_CYMBAL.getOffset();
//				break;
//			case "P1-I48": // 9
//				offset = DrumPiece.LO_MID_TOM.getOffset();
//				break;
//			case "P1-I46": // 10
//				offset = DrumPiece.LO_TOM.getOffset();
//				break;
//			case "P1-I44": // 11
//				offset = DrumPiece.HIGH_FLOOR_TOM.getOffset();
//				break;
//			case "P1-I42": // 12
//				offset = DrumPiece.LO_FLOOR_TOM.getOffset();
//				break;
//			case "P1-I45": // 13
//				offset = DrumPiece.PEDAL_HI_HAT.getOffset();
//				break;
//			default:
//				break;
//			}
		}
		return offset;
	}
}
