package utility;

public enum DrumPiece {

	BASS_DRUM (-24), 
//	TODO: CHANGE TO ACOUSTIC_BASS_DRUM
	Bass_Drum_2 (-30), 
	SIDE_STICK (-54), 
	ACOUSTIC_SNARE (-54), 
	LO_FLOOR_TOM (-36), 
	CLOSED_HI_HAT (-78),
	HIGH_FLOOR_TOM (-42),
	PEDAL_HI_HAT (-18), 
	LO_TOM (-48),
	OPEN_HI_HAT (-78),
	LO_MID_TOM (-60), 
	HI_MID_TOM (-66), 
	CRASH_CYMBAL_1 (-84),
	HI_TOM (-72),
	RIDE_CYMBAL_1 (-72), 
	CHINESE_CYMBAL (-96),
	RIDE_BELL (-72),
	TAMBOURINE (-48), 
	SPLASH_CYMBAL (-90),
	COWBELL (-66), 
	CRASH_CYMBAL_2 (-108), 
	RIDE_CYMBAL_2 (-102); 
//	OPEN_HI_CONGA, 
//	LO_CONGA
	
	protected final int offset;
	
	public int getOffset() {
		return offset;
	}

	DrumPiece(int offset) {
		this.offset = offset;
	}
	
	
}

