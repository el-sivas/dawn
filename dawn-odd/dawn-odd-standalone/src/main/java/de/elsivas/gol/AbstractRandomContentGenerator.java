package de.elsivas.gol;

import de.elsivas.gol.gui.CellContent;

public abstract class AbstractRandomContentGenerator<T extends CellContent> {
	
	public CellContent[][] generate(int size) {
		final CellContent[][] objects = createArray(size);
		fillRandom(objects);
		return objects;
		
	}
	
	public CellContent[][] generate() {
		return generate(30);
	}
	
	protected abstract T[][] createArray(int size);

	private void fillRandom(CellContent[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				a[j][i] = create();
			}
		}
	}
	
	protected abstract T create();
}
