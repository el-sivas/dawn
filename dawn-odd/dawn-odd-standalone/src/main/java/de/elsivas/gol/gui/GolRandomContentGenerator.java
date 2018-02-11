package de.elsivas.gol.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.elsivas.basic.SleepUtils;

public class GolRandomContentGenerator {

	public static void main(String[] args) throws IOException {
		int i = 0;
		while (i++ < 100) {
			CellContent[][] generate = generate();
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

	public static CellContent[][] generate() {
		GolRandomContentGenerator instance = getInstance();
		final CellContent[][] objects = new CellContent[50][50];
		instance.fillRandom(objects);
		return objects;
	}

	private static GolRandomContentGenerator getInstance() {
		return new GolRandomContentGenerator();
	}

	private void fillRandom(CellContent[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				boolean b = Math.random() > 0.5;

				a[j][i] = new CellObject(b);
			}
		}
	}

	private class CellObject implements CellContent {

		private boolean b;

		CellObject(boolean b) {
			this.b = b;
		}

		@Override
		public String toString() {
			return b ? "1" : null;
		}

		@Override
		public int getContentSize() {
			return 1;
		}

		@Override
		public boolean isContented() {
			return b;
		}
	}

}
