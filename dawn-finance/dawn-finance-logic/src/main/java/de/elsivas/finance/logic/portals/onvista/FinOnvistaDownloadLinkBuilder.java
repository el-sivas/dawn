package de.elsivas.finance.logic.portals.onvista;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.elsivas.basic.DateUtils;
import de.elsivas.finance.logic.Wertpapier;

public class FinOnvistaDownloadLinkBuilder {

	private static final Map<String, String> map = new HashMap<>();

	private static final String BASE_URL = "https://www.onvista.de/onvista/boxes/historicalquote/export.csv";

	private static final String DEFAULT_SDF_FORMAT = "dd.MM.yyyy";

	static {
		map.put("DE0008469008", "20735");
	}

	public static String buildDownloadLink(Wertpapier wp) {
		return buildDownloadLink(wp.getIsin());
	}

	public static String buildDownloadLink(String isin) {
		final String notationId = map.get(isin);
		final StringBuilder sb = new StringBuilder();
		sb.append(BASE_URL);
		sb.append("?notationId=" + notationId);
		Date start = DateUtils.toDate(LocalDate.now().minusMonths(1));
		sb.append("&dateStart=" + new SimpleDateFormat(DEFAULT_SDF_FORMAT).format(start));
		sb.append("&interval=M1");
		return sb.toString();
	}

}
