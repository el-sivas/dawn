package de.elsivas.gol.multi;

public enum Rich {
	PLANT(0b00000000), HERBIVORE(0b00100000), CARNIVORE(0b01000000);

	private int binary;

	private Rich(int binary) {
		this.binary = binary;
	}

	public int binary() {
		return binary;
	}
}
