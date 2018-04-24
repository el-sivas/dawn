package de.elsivas.finance.logic.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.EsRuntimeException;

public class FinConfig implements FinConfigurable {
	
	private static final Log LOG = LogFactory.getLog(FinConfig.class);

	public static final String WORKDIR = "WORKDIR";

	public static final String WMA_MIN = "WMA_MIN";

	public static final String PORTAL = "portal";

	public static final String DOWNLOAD_FILE_PREFIX = "DOWNLOAD_FILE_PREFIX";
	public static final String IMPORT_FILE_PREFIX = "IMPORT_FILE_PREFIX";

	private static boolean initialized = false;

	private static Map<String, String> map = new HashMap<>();

	private static Map<String, String> defaults = new HashMap<>();

	static {
		defaults.put(DOWNLOAD_FILE_PREFIX, "DL");
		defaults.put(IMPORT_FILE_PREFIX, "IM");
		defaults.put(WORKDIR, "/tmp/fin");
	}

	public static String get(String key) {
		final String value = getInternal(key);
		LOG.debug(key + ": " + value);
		return value;
	}

	private static String getInternal(String key) {
		if (!initialized) {
			throw new EsRuntimeException("not initialized");
		}
		final String value = map.get(key);
		if(!StringUtils.isBlank(value)) {
			return value;
		}
		final String defaultValue = defaults.get(key);
		if(!StringUtils.isBlank(defaultValue)) {
			return defaultValue;
		}
		throw new EsRuntimeException("not configured: " + key);
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
