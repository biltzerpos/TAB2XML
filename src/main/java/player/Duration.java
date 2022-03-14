package player;

public enum Duration {
	Whole(64), Half(32), Quarter(16), Eighth(8), Sixteenth(4), ThirtySecond(2), SixtyFourth(1), OneTwentyEighth(1 / 2);

	protected final int duration;

	Duration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return this.duration;
	}
}
