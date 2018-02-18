package de.elsivas.gol.gui;

public class DefaultCellContentFactory implements CellContentFactory {

	@Override
	public CellContent create(String value) {
		DefaultCellContent d = new DefaultCellContent();
		d.setValue(value);
		return d;
	}

}
