package design2;

// Offset calculated using first group as reference frame
// First Global y = 901
// Second Global y = 726 (change in y 901 - 726 = 175)
// so on.....
public enum Offset {
	E2offsety(96, 7), // 1
	F2offsety(90, 6), // 2
	G2offsety(84, 6), // 3
	A2offsety(78, 5), // 4
	B2offsety(72, 5), // 5

	C3offsety(66, 4), // 6
	D3offsety(60, 4), // 7
	E3offsety(54, 3), // 8
	F3offsety(48, 3), // 9
	G3offsety(42, 2), // 10
	A3offsety(36, 2), // 11
	B3offsety(30, 1), // 12

	C4offsety(24, 1), // 13
	D4offsety(18, 0), // 14
	E4offsety(12, 0), // 15
	F4offsety(6, 0), // 16
	G4offsety(0, 0), // 17
	A4offsety((-6), 0), // 18
	B4offsety((-12), 0), // 19

	C5offsety((-18), 0), // 20
	D5offsety((-24), 0), // 21
	E5offsety((-30), 0), // 22
	F5offsety((-36), 0), // 23
	G5offsety((-42), 0), // 24
	A5offsety((-48), (-1)), // 25
	B5offsety((-54), (-1)), // 26

	C6offsety((-60), (-2)), // 27
	D6offsety((-66), (-2)), // 28
	E6offsety((-72), (-23)); // 29

	protected final Integer offset;
	protected final Integer lines;

	Offset(int i, int j) {
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
