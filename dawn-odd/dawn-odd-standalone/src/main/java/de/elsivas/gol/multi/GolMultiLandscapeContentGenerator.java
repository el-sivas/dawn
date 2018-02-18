package de.elsivas.gol.multi;

import de.elsivas.gol.AbstractRandomContentGenerator;

public class GolMultiLandscapeContentGenerator extends AbstractRandomContentGenerator<RichParcelContent> {

	@Override
	protected RichParcelContent[][] createArray(int size) {
		return new RichParcelContent[size][size];
	}

	@Override
	protected RichParcelContent create() {
		RichParcelContent instance = RichParcelContent.getInstance("000");

		instance.set(Rich.PLANT, Math.random() < 0.85 ? value(1) : 0);
		instance.set(Rich.HERBIVORE, Math.random() < 0.3 ? randomValue() : 0);
		instance.set(Rich.CARNIVORE, Math.random() < 0.1 ? randomValue() : 0);
		return instance;
	}

	private int value(double d) {
		return (int) (d * Hex.CHARS.length() - 1);
	}

	private int randomValue() {
		return value(Math.random());
	}

}
