package de.elsivas.gol.multi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.elsivas.gol.gui.CellContent;
import de.elsivas.gol.gui.GolContentValidator;
import de.elsivas.gol.multi.validation.WorldValidator;

public class World {

	/**
	 * @return turned World
	 */
	public static RichParcelContent[][] turn(CellContent[][] world) {
		RichParcelContent[][] worldInternal = (RichParcelContent[][]) world;
		WorldValidator.validate(worldInternal);
		final int size = world.length;
		final GolMultiEmptyWorldGenerator golMultiEmptyWorldGenerator = new GolMultiEmptyWorldGenerator();
		final RichParcelContent[][] growWorld = (RichParcelContent[][]) golMultiEmptyWorldGenerator.generate(size);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				RichParcelContent parcel = worldInternal[y][x];
				RichParcelContent growParcel = growWorld[y][x];
				Rich[] values = Rich.values();
				RichParcelContent[] neigbors = neigbors(growWorld, x, y);
				for (Rich rich : values) {
					int count = parcel.getCount(rich);
					if (count == 0) {
						continue;
					}
					growParcel.set(rich, count);
					for (int i = 0; i < count; i++) {
						RichParcelContent neigbor = neigbors[(int) (Math.random() * neigbors.length)];
						if (neigbor == null) {
							continue;
						}
						neigbor.set(rich, growValue(neigbor.getCount(rich) + 1));
					}
				}
			}
		}
		WorldValidator.validate(growWorld);
		RichParcelContent[][] newWorld = (RichParcelContent[][]) golMultiEmptyWorldGenerator.generate(size);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				for (Rich rich : Rich.values()) {
					RichParcelContent cellContent = newWorld[y][x];
					int count = worldInternal[y][x].getCount(rich);
					int count2 = growWorld[y][x].getCount(rich);
					cellContent.set(rich, growValue(count + count2));
				}
			}
		}
		WorldValidator.validate(newWorld);
		
		GolContentValidator.validate(newWorld);

		return newWorld;
	}

	private static int growValue(int i) {
		if (i >= Hex.CHARS.length()) {
			return Hex.CHARS.length();
		}
		return i++;
	}

	private static RichParcelContent[] neigbors(RichParcelContent[][] world, int x, int y) {
		Collection<RichParcelContent> c = new ArrayList<>();
		c.add(getParcel(world, x - 1, y));
		c.add(getParcel(world, x + 1, y));
		c.add(getParcel(world, x - 1, y - 1));
		c.add(getParcel(world, x, y - 1));
		c.add(getParcel(world, x + 1, y - 1));
		c.add(getParcel(world, x - 1, y + 1));
		c.add(getParcel(world, x, y + 1));
		c.add(getParcel(world, x + 1, y + 1));
		ArrayList<RichParcelContent> arrayList = new ArrayList<>(c);
		Collections.shuffle(arrayList);
		return arrayList.toArray(new RichParcelContent[0]);
	}

	private static RichParcelContent getParcel(RichParcelContent[][] world, int x, int y) {
		int length = world.length;
		if (x < 0 || x > length - 1 || y < 0 || y > length - 1) {
			return null;
		}
		return world[y][x];
	}

}
