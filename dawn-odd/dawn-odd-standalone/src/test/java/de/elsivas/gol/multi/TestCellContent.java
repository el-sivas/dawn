package de.elsivas.gol.multi;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import de.elsivas.gol.gui.DefaultCellContent;

public class TestCellContent extends DefaultCellContent {

	private Map<Character, Integer> map = new HashMap<>();

	public TestCellContent() {
		char[] charArray = Hex.CHARS.toCharArray();
		int i = 0;
		for (char c : charArray) {
			map.put(c, i++);
		}
	}

	@Override
	public void format(Component c) {
		char[] charArray = value().toCharArray();
		int r = color(map.get(charArray[Rich.CARNIVORE.ordinal()]));
		int g = color(map.get(charArray[Rich.PLANT.ordinal()]));
		int b = color(map.get(charArray[Rich.HERBIVORE.ordinal()]));

		float[] rgBtoHSB = Color.RGBtoHSB(r, g, b, new float[3]);
		Color hsbColor = Color.getHSBColor(rgBtoHSB[0], rgBtoHSB[1], rgBtoHSB[2]);
		c.setBackground(hsbColor);
	}

	private int color(Integer integer) {
		return integer * 16 + integer;
	}
}
