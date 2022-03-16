package design2;

// Offset calculated using first group as reference frame
// First Global y = 901
// Second Global y = 726 (change in y 901 - 726 = 175)
// so on.....
public enum Offset {
	E2offsety(15, 7, 0), // 1
	F2offsety(14, 6, 1), // 2
	G2offsety(13, 6, 0), // 3
	A2offsety(12, 5, 1), // 4
	B2offsety(11, 5, 0), // 5

	C3offsety(10, 4, 1), // 6
	D3offsety(9, 4, 0), // 7
	E3offsety(8, 3, 1), // 8
	F3offsety(7, 3, 0), // 9
	G3offsety(6, 2, 1), // 10
	A3offsety(5, 2, 0), // 11
	B3offsety(4, 1, 1), // 12

	C4offsety(3, 1, 0), // 13
	D4offsety(2, 0, 1), // 14
	E4offsety(1, 0, 0), // 15
	F4offsety(0, 0, 1), // 16
	G4offsety((-1), 0, 0), // 17
	A4offsety((-2), 0, 1), // 18
	B4offsety((-3), 0, 0), // 19

	C5offsety((-4), 0, 1), // 20
	D5offsety((-5), 0, 0), // 21
	E5offsety((-6), 0, 1), // 22
	F5offsety((-7), 0, 0), // 23
	G5offsety((-8), 0, 1), // 24
	A5offsety((-9), (-1), 0), // 25
	B5offsety((-10), (-1), 1), // 26

	C6offsety((-11), (-2), 0), // 27
	D6offsety((-12), (-2), 1), // 28
	E6offsety((-13), (-23), 0); // 29

	protected final Integer offset;
	protected final Integer onLine;
	protected final Integer lines;

	Offset(int offset, int lines, Integer onLine) {
		this.offset = offset;
		this.lines = lines;
		this.onLine = onLine;
	}

	public int offset() {
		return this.offset * 5;
	}
	
	public Integer onLine() { 
		return this.onLine * 5;
	}

	public int lines() {
		return this.lines;
	}
}
