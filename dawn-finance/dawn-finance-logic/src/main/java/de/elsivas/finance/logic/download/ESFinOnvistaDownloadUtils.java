package de.elsivas.finance.logic.download;

import de.elsivas.basic.ESConsoleUtils;
import de.elsivas.basic.EsIOUtils;
import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.filedao.FileCsvDao;
import de.elsivas.finance.logic.Wertpapier;

public class ESFinOnvistaDownloadUtils {

	private static final String TMP_FILE_LOCATION = "/tmp/export.csv";

	public static String download(Wertpapier wp) {
		final String downloadLink = ESFinOnvista.getDownloadLink(wp.getIsin());
		final StringBuilder sb = new StringBuilder();
		sb.append("wget -O " + TMP_FILE_LOCATION + " ");
		sb.append(downloadLink);
		final String command = sb.toString();
		ESConsoleUtils.runConsoleCommand(command);
		return EsIOUtils.readFile(TMP_FILE_LOCATION);
	}

}
