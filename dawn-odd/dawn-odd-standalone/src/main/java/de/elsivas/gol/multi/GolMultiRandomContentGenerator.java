package de.elsivas.gol.multi;

import de.elsivas.gol.AbstractRandomContentGenerator;

public class GolMultiRandomContentGenerator extends AbstractRandomContentGenerator<RichParcelContent> {
	
	@Override
	protected RichParcelContent[][] createArray(int size) {
		return new RichParcelContent[size][size];
	}

	@Override
	protected RichParcelContent create() {
		return RichParcelContent.getInstance(random());
	}
	
	private String random() {
		final StringBuilder sb = new StringBuilder();
		sb.append(randomChar());
		sb.append(randomChar());
		sb.append(randomChar());
		return sb.toString();
	}
	
	private char randomChar() {
		char[] charArray = Hex.CHARS.toCharArray();
		return charArray[(int) (Math.random() * charArray.length)];
	}

}
