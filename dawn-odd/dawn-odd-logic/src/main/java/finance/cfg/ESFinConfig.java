package finance.cfg;

import java.util.HashMap;
import java.util.Map;

import de.elsivas.basic.EsRuntimeException;

public class ESFinConfig {
	
	public static final String WMA_MIN = "WMA_MIN";

	private static boolean initialized = false;

	private static Map<String, Object> map = new HashMap<>();

	public static <T extends Object> T get(String string, Class<T> type) {
		if (!initialized) {
			throw new EsRuntimeException("not initialized");
		}
		return type.cast(map.get(string));
	}
	
	public static void init(Map<String,Object> config) {
		map.clear();
		map.putAll(config);
		initialized = true;
	}
}
