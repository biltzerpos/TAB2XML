package previewer;

// Offset calculated using first group as reference frame
// First Global y = 901
// Second Global y = 726 (change in y 901 - 726 = 175)
// so on.....
public enum NotesOffset {
	E2offsety(60, 7), // 1
	F2offsety(54, 6), // 2
	G2offsety(48, 6), // 3
	A2offsety(42, 5), // 4
	B2offsety(36, 5), // 5

	C3offsety(30, 4), // 6
	D3offsety(24, 4), // 7
	E3offsety(18, 3), // 8
	F3offsety(12, 3), // 9
	G3offsety(6, 2), // 10
	A3offsety(0, 2), // 11
	B3offsety((-6), 1), // 12

	C4offsety((-12), 1), // 13
	D4offsety((-18), 0), // 14
	E4offsety((-24), 0), // 15
	F4offsety((-30), 0), // 16
	G4offsety((-36), 0), // 17
	A4offsety((-42), 0), // 18
	B4offsety((-48), 0), // 19

	C5offsety((-54), 0), // 20
	D5offsety((-60), 0), // 21
	E5offsety((-66), 0), // 22
	F5offsety((-72), 0), // 23
	G5offsety((-78), 0), // 24
	A5offsety((-84), (-1)), // 25
	B5offsety((-90), (-1)), // 26

	C6offsety((-96), (-2)), // 27
	D6offsety((-112), (-2)), // 28
	E6offsety((-118), (-23)); // 29

	protected final Integer offset;
	protected final Integer lines;

	NotesOffset(int i, int j) {
		this.offset = i;
		this.lines = j;
	}

	public int offset() {
		return (this.offset / 2);
	}

	public int lines() {
		return this.lines;
	}
}