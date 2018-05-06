package de.elsivas.basic;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BasicConfig {

	private static final Log LOG = LogFactory.getLog(BasicConfig.class);

	private static String pathToConfigFile;

	public static void setConfigFile(String pathToFile) throws EsLogicException {
		if (StringUtils.isEmpty(pathToFile)) {
			LOG.warn("config file null, continue.");
			return;
		}
		if (!new File(pathToFile).exists()) {
			throw new EsLogicException("file not exist: " + pathToFile);
		}
		if (!StringUtils.isBlank(pathToConfigFile)) {
			throw new EsLogicException("file already set: " + pathToConfigFile);
		}
		pathToConfigFile = pathToFile;
	}

	static String getPathToConfigFile() {
		return pathToConfigFile;
	}

	public static String get(String key) {
		return BasicConfigUtils.loadBasicConfig().get(key);
	}

	public static ConfigValue get(String context, String key) {
		return ConfigValue.create(get(contextedKey(context, key)));
	}

	public static void set(String context, String key, String value) {
		set(contextedKey(context, key), value);
	}

	public static void set(String context, String key, ConfigValue value) {
		set(contextedKey(context, key), value.asString());
	}

	private static String contextedKey(String context, String key) {
		return context + "." + key;
	}

	public static void set(String key, String value) {
		final Map<String, String> basicConfig = BasicConfigUtils.loadBasicConfig();
		basicConfig.put(key, value);
		BasicConfigUtils.saveBasicConfig(basicConfig);
	}
}
