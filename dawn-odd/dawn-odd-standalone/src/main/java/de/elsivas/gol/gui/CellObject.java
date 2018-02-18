package de.elsivas.gol.gui;

public class CellObject implements CellContent {

	private boolean b;

	CellObject(boolean b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return b ? "1" : "0";
	}

	@Override
	public int getContentSize() {
		return 1;
	}

	@Override
	public boolean isContented() {
		return b;
	}

	@Override
	public String value() {
		return toString();
	}
}
