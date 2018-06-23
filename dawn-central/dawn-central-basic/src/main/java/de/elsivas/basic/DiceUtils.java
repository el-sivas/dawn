package de.elsivas.basic;

public class DiceUtils {

	public static int dice(int upTo) {
		return (int) (Math.random() * upTo);
	}

	public static int dice() {
		return 1 + dice(6);
	}

}
