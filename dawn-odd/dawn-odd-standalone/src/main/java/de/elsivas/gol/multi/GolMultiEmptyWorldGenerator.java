package de.elsivas.gol.multi;

import de.elsivas.gol.AbstractRandomContentGenerator;

public class GolMultiEmptyWorldGenerator extends AbstractRandomContentGenerator<RichParcelContent> {

	@Override
	protected RichParcelContent[][] createArray(int size) {
		return new RichParcelContent[size][size];
	}

	@Override
	protected RichParcelContent create() {
		return RichParcelContent.getInstance("000");
	}

}
