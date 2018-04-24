package de.elsivas.finance.logic.portals.onvista;

import de.elsivas.basic.ESConsoleUtils;
import de.elsivas.finance.logic.Wertpapier;

public class ESFinOnvistaDownloadUtils {

	private static final String TMP_FILE_LOCATION = "/tmp/export.csv";

	/**
	 * Filename where downloaded
	 */
	public static String downloadToFile(Wertpapier wp) {
		final String downloadLink = ESFinOnvistaDownloadLinkBuilder.buildDownloadLink(wp.getIsin());
		final StringBuilder sb = new StringBuilder();
		sb.append("wget -O " + TMP_FILE_LOCATION + " ");
		sb.append(downloadLink);
		final String command = sb.toString();
		ESConsoleUtils.runConsoleCommand(command);
		return TMP_FILE_LOCATION;
	}

}
