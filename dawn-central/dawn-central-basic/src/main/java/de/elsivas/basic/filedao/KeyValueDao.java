package de.elsivas.basic.filedao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.elsivas.basic.EsIOUtils;

public class KeyValueDao {

	public static Map<String, String> read(String fileName) {
		final List<String> fileInLine = EsIOUtils.readFileToLines(fileName);
		Map<String, String> map = new HashMap<>();

		for (String line : fileInLine) {
			final String[] split = line.split("=");
			map.put(split[0], split[1]);
		}
		return map;

	}

	public static void write(String fileName, Map<String, String> values) {
		final Set<String> keySet = values.keySet();
		final List<String> lines = new ArrayList<>();
		for (String key : keySet) {
			lines.add(key + "=" + values.get(key));
		}
		EsIOUtils.write(fileName, lines);
	}
}
