package de.elsivas.basic;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.elsivas.basic.filedao.KeyValueDao;

public class BasicConfigUtils {

	private static final String BASE_CONFIG_FILE = "elsivas-config.txt";

	public static void saveBasicConfig(final Map<String, String> config) {
		KeyValueDao.write(basicConfigPath(), config);
	}

	public static Map<String, String> loadBasicConfig() {
		if (!hasBasicConfig()) {
			throw new EsRuntimeException("no basic config");
		}
		return KeyValueDao.read(basicConfigPath());
	}

	public static boolean hasBasicConfig() {
		return new File(basicConfigPath()).exists();
	}

	private static String basicConfigPath() {
		final String determineUserHome = ConsoleUtils.determineUserHome();
		final String separator = FileSystems.getDefault().getSeparator();
		final String configuredConfigFile = BasicConfig.getPathToConfigFile();
		if (!StringUtils.isBlank(configuredConfigFile)) {
			return configuredConfigFile;
		}
		return determineUserHome + separator + BASE_CONFIG_FILE;
	}

	public static void createBaseConfig() {
		if (hasBasicConfig()) {
			throw new EsRuntimeException("base config already exists");
		}
		try {
			new File(basicConfigPath()).createNewFile();
		} catch (final IOException e) {
			throw new EsRuntimeException("error create basic config", e);
		}
	}
}
