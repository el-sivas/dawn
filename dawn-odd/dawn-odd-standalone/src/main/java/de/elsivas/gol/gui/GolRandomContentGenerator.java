package de.elsivas.gol.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.elsivas.basic.SleepUtils;
import de.elsivas.gol.AbstractRandomContentGenerator;

public class GolRandomContentGenerator extends AbstractRandomContentGenerator<CellObject> {

	public static void main(String[] args) throws IOException {
		int i = 0;
		while (i++ < 100) {
			CellContent[][] generate = generateContent();
			StringBuilder sb = new StringBuilder();
			for (CellContent[] cellContents : generate) {
				for (CellContent cellContent : cellContents) {
					sb.append(cellContent.isContented() ? "1" : "0");
				}
				sb.append("\n");
			}
			try (BufferedWriter br = new BufferedWriter(
					new FileWriter(new File("/home/sebastian/test/gol/" + System.currentTimeMillis() + ".gol")))) {
				br.write(sb.toString());
			}

			SleepUtils.sleepFor(2000);
		}
	}
	
	public static CellContent[][] generateContent() {
		return new GolRandomContentGenerator().generate();
	}

	@Override
	protected CellObject create() {
		return new CellObject(Math.random() > 0.5);
	}



	@Override
	protected CellObject[][] createArray(int size) {
		return new CellObject[size][size];
	}
}