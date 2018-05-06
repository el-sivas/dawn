package de.elsivas.basic.protocol;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import de.elsivas.basic.SleepUtils;

/**
 * A slow but syncronzied Protocolant.
 *
 * @author sivas
 *
 */
public class Protocolant {

	private static final String SDF_FORMAT = "yyyy-MM-dd_HH:mm:ss.mmm";

	private static final int MAX_LENGTH = 60;

	private final Map<Long, String> map = new HashMap<>();

	private Protocolant() {

	}

	public static Protocolant instance() {
		return new Protocolant();
	}

	public void append(final String s) {
		synchronized (this) {
			SleepUtils.sleepFor(1);
			map.put(System.currentTimeMillis(), s);
		}
	}

	public String toProtocol() {
		final StringBuilder sb = new StringBuilder();
		final Set<Long> keySet = map.keySet();
		for (final Long timestamp : keySet) {
			final StringBuilder line = new StringBuilder();
			line.append(timestamp(timestamp));
			line.append(": ");
			line.append(StringUtils.abbreviate(map.get(timestamp), MAX_LENGTH - line.toString().length()));
			sb.append(line + "\n");
		}
		return sb.toString();

	}

	private String timestamp(final Long timestamp) {
		return new SimpleDateFormat(SDF_FORMAT).format(new Date(timestamp));
	}
}
