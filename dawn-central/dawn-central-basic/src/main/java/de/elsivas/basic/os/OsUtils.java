package de.elsivas.basic.os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class OsUtils {

	private static final String LINUX = "Linux";

	private static final Collection<OperatingSystem> POSIX_OS = Collections.singletonList(OperatingSystem.LINUX);

	public static OperatingSystem determineOperatingSystem() {
		final String osName = System.getProperty("os.name");
		if (LINUX.equalsIgnoreCase(osName)) {
			return OperatingSystem.LINUX;
		}
		return null;
	}

	public static String getOsPathSeparator() {
		if (POSIX_OS.contains(determineOperatingSystem())) {
			return "/";
		}
		return "\\";
	}

	public static String buildPath(String... pathParts) {
		final String osPathSeparator = getOsPathSeparator();
		final StringBuilder sb = new StringBuilder();
		for (final String string : pathParts) {
			if (POSIX_OS.contains(determineOperatingSystem())) {
				sb.append("/");
			}
			if (sb.toString().length() != 0) {
				sb.append(osPathSeparator);
			}
			sb.append(string);
		}
		return sb.toString();
	}

	public static String[] getTmpDir() {
		final OperatingSystem determineOperatingSystem = determineOperatingSystem();
		switch (determineOperatingSystem) {
		case LINUX:
			return new String[] { "tmp" };
		case WINDOWS:
			return new String[] { "c", "temp" };
		default:
			break;
		}

		return null;
	}

	public static String buildPath(String[] tmpDir, String defaultConfigFile) {
		final Collection<String> s = new ArrayList<>();
		s.addAll(Arrays.asList(tmpDir));
		s.add(defaultConfigFile);
		final String[] array = s.toArray(new String[0]);
		return buildPath(array);

	}
}
