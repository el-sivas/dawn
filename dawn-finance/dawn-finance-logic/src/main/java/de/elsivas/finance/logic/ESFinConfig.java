package de.elsivas.finance.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.elsivas.basic.EsRuntimeException;

public class ESFinConfig implements FinConfiguration {

	public static final String WORKDIR = "workdir";

	public static final String WMA_MIN = "WMA_MIN";

	private static boolean initialized = false;

	private static Map<String, String> map = new HashMap<>();

	public static String get(String key) {
		if (!initialized) {
			throw new EsRuntimeException("not initialized");
		}
		return map.get(key);
	}

	public static void init(Map<String, String> config) {
		map.clear();
		map.putAll(config);
		initialized = true;
	}

	public static void set(String key, String value) {
		map.put(key, value);
	}

	public static Map<String, String> getValues() {
		return map;
	}

	@Override
	public List<String> getConfig() {
		return Arrays.asList(WORKDIR);
	}
}
