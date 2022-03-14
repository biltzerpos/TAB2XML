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
				pdfbuilder.arbitraryPath(Offset.E2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E2offsety.lines(), n, score, iteration, measure);
				break;
			case "F2": // 2
				pdfbuilder.arbitraryPath(Offset.F2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F2offsety.lines(), n, score, iteration, measure);
				break;
			case "G2": // 3
				pdfbuilder.arbitraryPath(Offset.G2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G2offsety.lines(), n, score, iteration, measure);
				break;
			case "A2": // 4
				pdfbuilder.arbitraryPath(Offset.A2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A2offsety.lines(), n, score, iteration, measure);
				break;
			case "B2": // 5
				pdfbuilder.arbitraryPath(Offset.B2offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B2offsety.lines(), n, score, iteration, measure);
				break;
			case "C3": // 6
				pdfbuilder.arbitraryPath(Offset.C3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C3offsety.lines(), n, score, iteration, measure);
				break;
			case "D3": // 7
				pdfbuilder.arbitraryPath(Offset.D3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D3offsety.lines(), n, score, iteration, measure);
				break;
			case "E3": // 8
				pdfbuilder.arbitraryPath(Offset.E3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E3offsety.lines(), n, score, iteration, measure);
				break;
			case "F3": // 9
				pdfbuilder.arbitraryPath(Offset.F3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F3offsety.lines(), n, score, iteration, measure);
				break;
			case "G3": // 10
				pdfbuilder.arbitraryPath(Offset.G3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G3offsety.lines(), n, score, iteration, measure);
				break;
			case "A3": // 11
				pdfbuilder.arbitraryPath(Offset.A3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A3offsety.lines(), n, score, iteration, measure);
				break;
			case "B3": // 12
				pdfbuilder.arbitraryPath(Offset.B3offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B3offsety.lines(), n, score, iteration, measure);
				break;
			case "C4": // 13
				pdfbuilder.arbitraryPath(Offset.C4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C4offsety.lines(), n, score, iteration, measure);
				break;
			case "D4": // 14
				pdfbuilder.arbitraryPath(Offset.D4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D4offsety.lines(), n, score, iteration, measure);
				break;
			case "E4": // 15
				pdfbuilder.arbitraryPath(Offset.E4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E4offsety.lines(), n, score, iteration, measure);
				break;
			case "F4": // 16
				pdfbuilder.arbitraryPath(Offset.F4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F4offsety.lines(), n, score, iteration, measure);
				break;
			case "G4": // 17
				pdfbuilder.arbitraryPath(Offset.G4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G4offsety.lines(), n, score, iteration, measure);
				break;
			case "A4": // 18
				pdfbuilder.arbitraryPath(Offset.A4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A4offsety.lines(), n, score, iteration, measure);
				break;
			case "B4": // 19
				pdfbuilder.arbitraryPath(Offset.B4offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B4offsety.lines(), n, score, iteration, measure);
				break;
			case "C5": // 20
				pdfbuilder.arbitraryPath(Offset.C5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C5offsety.lines(), n, score, iteration, measure);
				break;
			case "D5": // 21
				pdfbuilder.arbitraryPath(Offset.D5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D5offsety.lines(), n, score, iteration, measure);
				break;
			case "E5": // 22
				pdfbuilder.arbitraryPath(Offset.E5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E5offsety.lines(), n, score, iteration, measure);
				break;
			case "F5": // 23
				pdfbuilder.arbitraryPath(Offset.F5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.F5offsety.lines(), n, score, iteration, measure);
				break;
			case "G5": // 24
				pdfbuilder.arbitraryPath(Offset.G5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.G5offsety.lines(), n, score, iteration, measure);
				break;
			case "A5": // 25
				pdfbuilder.arbitraryPath(Offset.A5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.A5offsety.lines(), n, score, iteration, measure);
				break;
			case "B5": // 26
				pdfbuilder.arbitraryPath(Offset.B5offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.B5offsety.lines(), n, score, iteration, measure);
				break;
			case "C6": // 27
				pdfbuilder.arbitraryPath(Offset.C6offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.C6offsety.lines(), n, score, iteration, measure);
				break;
			case "D6": // 28
				pdfbuilder.arbitraryPath(Offset.D6offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.D6offsety.lines(), n, score, iteration, measure);
				break;
			case "E6": // 29
				pdfbuilder.arbitraryPath(Offset.E6offsety.offset(), n.getNotations().getTechnical().getFret(), Offset.E6offsety.lines(), n, score, iteration, measure);
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
