package previewer;

import java.io.IOException;

import converter.Score;
import custom_exceptions.TXMLException;
import models.measure.note.Note;
import utility.DrumPiece;

public class NoteIdentifier {
	public void identifyNote(String instr, String pitchFret, Note n, Score score, int iteration, int measure, pdfbuilder pdfbuilder) throws IOException, TXMLException {
		
		if(instr.equals("Guitar")) {
			switch (pitchFret) {
			case "E2": // 1
				pdfbuilder.arbitraryPath(NotesOffset.E2offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.E2offsety.lines(), n, score, iteration, measure);
				break;
			case "F2": // 2
				pdfbuilder.arbitraryPath(NotesOffset.F2offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.F2offsety.lines(), n, score, iteration, measure);
				break;
			case "G2": // 3
				pdfbuilder.arbitraryPath(NotesOffset.G2offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.G2offsety.lines(), n, score, iteration, measure);
				break;
			case "A2": // 4
				pdfbuilder.arbitraryPath(NotesOffset.A2offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.A2offsety.lines(), n, score, iteration, measure);
				break;
			case "B2": // 5
				pdfbuilder.arbitraryPath(NotesOffset.B2offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.B2offsety.lines(), n, score, iteration, measure);
				break;
			case "C3": // 6
				pdfbuilder.arbitraryPath(NotesOffset.C3offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.C3offsety.lines(), n, score, iteration, measure);
				break;
			case "D3": // 7
				pdfbuilder.arbitraryPath(NotesOffset.D3offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.D3offsety.lines(), n, score, iteration, measure);
				break;
			case "E3": // 8
				pdfbuilder.arbitraryPath(NotesOffset.E3offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.E3offsety.lines(), n, score, iteration, measure);
				break;
			case "F3": // 9
				pdfbuilder.arbitraryPath(NotesOffset.F3offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.F3offsety.lines(), n, score, iteration, measure);
				break;
			case "G3": // 10
				pdfbuilder.arbitraryPath(NotesOffset.G3offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.G3offsety.lines(), n, score, iteration, measure);
				break;
			case "A3": // 11
				pdfbuilder.arbitraryPath(NotesOffset.A3offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.A3offsety.lines(), n, score, iteration, measure);
				break;
			case "B3": // 12
				pdfbuilder.arbitraryPath(NotesOffset.B3offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.B3offsety.lines(), n, score, iteration, measure);
				break;
			case "C4": // 13
				pdfbuilder.arbitraryPath(NotesOffset.C4offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.C4offsety.lines(), n, score, iteration, measure);
				break;
			case "D4": // 14
				pdfbuilder.arbitraryPath(NotesOffset.D4offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.D4offsety.lines(), n, score, iteration, measure);
				break;
			case "E4": // 15
				pdfbuilder.arbitraryPath(NotesOffset.E4offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.E4offsety.lines(), n, score, iteration, measure);
				break;
			case "F4": // 16
				pdfbuilder.arbitraryPath(NotesOffset.F4offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.F4offsety.lines(), n, score, iteration, measure);
				break;
			case "G4": // 17
				pdfbuilder.arbitraryPath(NotesOffset.G4offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.G4offsety.lines(), n, score, iteration, measure);
				break;
			case "A4": // 18
				pdfbuilder.arbitraryPath(NotesOffset.A4offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.A4offsety.lines(), n, score, iteration, measure);
				break;
			case "B4": // 19
				pdfbuilder.arbitraryPath(NotesOffset.B4offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.B4offsety.lines(), n, score, iteration, measure);
				break;
			case "C5": // 20
				pdfbuilder.arbitraryPath(NotesOffset.C5offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.C5offsety.lines(), n, score, iteration, measure);
				break;
			case "D5": // 21
				pdfbuilder.arbitraryPath(NotesOffset.D5offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.D5offsety.lines(), n, score, iteration, measure);
				break;
			case "E5": // 22
				pdfbuilder.arbitraryPath(NotesOffset.E5offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.E5offsety.lines(), n, score, iteration, measure);
				break;
			case "F5": // 23
				pdfbuilder.arbitraryPath(NotesOffset.F5offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.F5offsety.lines(), n, score, iteration, measure);
				break;
			case "G5": // 24
				pdfbuilder.arbitraryPath(NotesOffset.G5offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.G5offsety.lines(), n, score, iteration, measure);
				break;
			case "A5": // 25
				pdfbuilder.arbitraryPath(NotesOffset.A5offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.A5offsety.lines(), n, score, iteration, measure);
				break;
			case "B5": // 26
				pdfbuilder.arbitraryPath(NotesOffset.B5offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.B5offsety.lines(), n, score, iteration, measure);
				break;
			case "C6": // 27
				pdfbuilder.arbitraryPath(NotesOffset.C6offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.C6offsety.lines(), n, score, iteration, measure);
				break;
			case "D6": // 28
				pdfbuilder.arbitraryPath(NotesOffset.D6offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.D6offsety.lines(), n, score, iteration, measure);
				break;
			case "E6": // 29
				pdfbuilder.arbitraryPath(NotesOffset.E6offsety.offset(), n.getNotations().getTechnical().getFret(), NotesOffset.E6offsety.lines(), n, score, iteration, measure);
				break;
			default:
				break;
			}
		}
		else if(instr.equals("Drumset")) {
			switch (pitchFret) {
			case "P1-I36": // 1
				pdfbuilder.arbitraryPath(DrumPiece.BASS_DRUM.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I39": // 2
				pdfbuilder.arbitraryPath(DrumPiece.ACOUSTIC_SNARE.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I43": // 3
				pdfbuilder.arbitraryPath(DrumPiece.CLOSED_HI_HAT.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I47": // 4
				pdfbuilder.arbitraryPath(DrumPiece.OPEN_HI_HAT.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I52": // 5
				pdfbuilder.arbitraryPath(DrumPiece.RIDE_CYMBAL_1.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I54": // 6
				pdfbuilder.arbitraryPath(DrumPiece.RIDE_BELL.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I50": // 7
				pdfbuilder.arbitraryPath(DrumPiece.CRASH_CYMBAL_1.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I53": // 8
				pdfbuilder.arbitraryPath(DrumPiece.CHINESE_CYMBAL.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I48": // 9
				pdfbuilder.arbitraryPath(DrumPiece.LO_MID_TOM.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I46": // 10
				pdfbuilder.arbitraryPath(DrumPiece.LO_TOM.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I44": // 11
				pdfbuilder.arbitraryPath(DrumPiece.HIGH_FLOOR_TOM.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I42": // 12
				pdfbuilder.arbitraryPath(DrumPiece.LO_FLOOR_TOM.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			case "P1-I45": // 13
				pdfbuilder.arbitraryPath(DrumPiece.PEDAL_HI_HAT.getOffset(), 0, 0, n, score, iteration, measure);
				break;
			default:
				break;
			}
		}
		
	}
}
