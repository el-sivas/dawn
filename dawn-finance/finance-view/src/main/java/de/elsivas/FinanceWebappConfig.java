package de.elsivas;

import java.util.HashMap;
import java.util.Map;

public class FinanceWebappConfig {

	private static final Map<String, Object> m = new HashMap<>();

	public static Object get(String k) {
		return m.get(k);
	}

	public static String getValue(String k) {
		return (String) m.get(k);
	}

	public static void set(String k, Object v) {
		m.put(k, v);
	}

}
