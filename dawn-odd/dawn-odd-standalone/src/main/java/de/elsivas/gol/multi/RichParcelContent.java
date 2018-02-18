package de.elsivas.gol.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.elsivas.gol.gui.CellContent;

public class RichParcelContent implements CellContent {

	private Map<Rich, Integer> map = new HashMap<>();

	private RichParcelContent(Map<Rich, Integer> map) {
		this.map.putAll(map);
	}

	public static RichParcelContent getInstance(final String hex) {
		Rich[] values = Rich.values();
		final Map<Rich, Integer> map = new HashMap<>();
		for (int i = 0; i < hex.length(); i++) {
			map.put(values[i], Integer.decode("0x" + hex.toCharArray()[i]));
		}
		return new RichParcelContent(map);
	}

	@Override
	public int getContentSize() {
		return map.size();
	}

	@Override
	public boolean isContented() {
		for (Rich rich : map.keySet()) {
			Integer integer = map.get(rich);
			if (integer != null && Integer.valueOf(0).compareTo(integer) > 0) {
				return true;
			}
		}
		return false;
	}

	public int getCount(Rich rich) {
		return map.get(rich);
	}

	@Override
	public String value() {
		StringBuilder sb = new StringBuilder();
		List<Rich> list = new ArrayList<>(map.keySet());
		Collections.sort(list);
		for (Rich rich : list) {
			sb.append(Integer.toHexString(map.get(rich)));
		}
		return sb.toString();
	}
	
	public void set(Rich rich, int count) {
		map.put(rich, count);
	}
}
