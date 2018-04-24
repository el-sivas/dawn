package de.elsivas.finance.logic;

import de.elsivas.basic.SleepUtils;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.portals.Portal;

public class FinFilenameUtils {

	private static final String DOT_CSV = ".csv";

	private static final String SEPARATOR = "_";

	public static final String DOWNLOAD = "DOWNLOAD";

	public static final String IMPORT = "IMPORT";

	public static String generateDownloadFilename(Portal portal, Wertpapier wertpapier) {
		SleepUtils.sleepFor(10);
		final StringBuilder sb = new StringBuilder();
		sb.append(FinConfig.get(FinConfig.DOWNLOAD_FILE_PREFIX));
		sb.append(SEPARATOR);
		sb.append(portal.toString());
		sb.append(SEPARATOR);
		sb.append(wertpapier.toString());
		sb.append(SEPARATOR);
		sb.append(System.currentTimeMillis());
		sb.append(DOT_CSV);
		return sb.toString();

	}

	public static String generateImportFilename(String downloadFilename, Portal portal, Wertpapier wertpapier) {
		final StringBuilder sb = new StringBuilder();
		sb.append(FinConfig.get(FinConfig.DOWNLOAD_FILE_PREFIX));
		sb.append(SEPARATOR);
		sb.append(portal.toString());
		sb.append(SEPARATOR);
		sb.append(wertpapier.toString());
		sb.append(SEPARATOR);
		sb.append(extractId(downloadFilename));
		sb.append(DOT_CSV);
		return sb.toString();
	}

	private static String extractId(String filename) {
		return filename.split(SEPARATOR)[3];
	}

	public static Wertpapier extractWertpapier(String filename) {
		return Wertpapier.of(filename.split(SEPARATOR)[2]);
	}

}
