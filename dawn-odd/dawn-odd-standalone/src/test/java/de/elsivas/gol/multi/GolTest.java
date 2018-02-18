package de.elsivas.gol.multi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import de.elsivas.basic.SleepUtils;
import de.elsivas.gol.gui.CellContent;
import de.elsivas.gol.gui.CellContentFactory;
import de.elsivas.gol.gui.GolGui;

public class GolTest {

	@Test
	public void test2() {
		String path = "/tmp";
		GolGui.doStart(path, 3, cellContentFactory());
		int i = 0;
		GolMultiLandscapeContentGenerator generator = new GolMultiLandscapeContentGenerator();
		CellContent[][] world = generator.generate(20);
		write(world, path + "/" + System.currentTimeMillis() + ".gol");
		while (i++ < 1000) {
			world = World.turn(world);
			write(world, path + "/" + System.currentTimeMillis() + ".gol");
			SleepUtils.sleepFor(10000);
		}

	}

	@Test
	public void test() {
		String path = "/tmp";
		GolGui.doStart(path, 3, cellContentFactory());
		int i = 0;
		while (i++ < 1000) {
			write(new GolMultiRandomContentGenerator().generate(20), path + "/" + System.currentTimeMillis() + ".gol");
			SleepUtils.sleepFor(1000);
		}
	}

	private CellContentFactory cellContentFactory() {
		return new CellContentFactory() {

			@Override
			public CellContent create(String value) {
				TestCellContent t = new TestCellContent();
				t.setValue(value);
				return t;
			}
		};
	}

	private void write(CellContent[][] array, String file) {
		try (FileOutputStream fos = new FileOutputStream(new File(file))) {
			try (final BufferedOutputStream bos = new BufferedOutputStream(fos)) {
				bos.write(toByteArray(array));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] toByteArray(CellContent[][] array) {
		final StringBuilder sb = new StringBuilder();
		for (int y = 0; y < array.length; y++) {
			final CellContent[] line = array[y];
			for (int x = 0; x < line.length; x++) {
				sb.append(line[x].value());
			}
			sb.append("\n");
		}
		return sb.toString().getBytes();
	}

}
