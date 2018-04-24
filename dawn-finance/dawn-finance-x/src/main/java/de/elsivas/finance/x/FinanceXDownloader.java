package de.elsivas.finance.x;

import java.util.Arrays;
import java.util.List;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.logic.FinConfig;
import de.elsivas.finance.logic.FinConfiguration;
import de.elsivas.finance.logic.Wertpapier;
import de.elsivas.finance.logic.portals.Portals;
import de.elsivas.finance.logic.portals.onvista.ESFinOnvistaDownloadUtils;

/**
 *
 */
public class FinanceXDownloader implements FinanceX, FinConfiguration {

	private final static String DOWNLOAD_LIST = "download-list";

	private final static String PORTAL = "portal";

	private final static String TARGET = "target";

	@Override
	public List<String> getConfig() {
		return Arrays.asList(DOWNLOAD_LIST, PORTAL, TARGET);
	}

	@Override
	public void run() {
		final List<String> downloadList = Arrays.asList(FinConfig.get(DOWNLOAD_LIST).split(";"));
		final Portals portal = Portals.valueOf(FinConfig.get(PORTAL).toUpperCase());
		final String targetFile = FinConfig.get(TARGET);
		switch (portal) {
		case ONVISTA:
			downloadOnvista(downloadList, targetFile);
			break;

		default:
			throw new EsRuntimeException("invalid portal :" + FinConfig.get(PORTAL).toUpperCase());
		}
	}

	private void downloadOnvista(List<String> downloadList, String targetFile) {
		for (String wertpapier : downloadList) {
			ESFinOnvistaDownloadUtils.downloadToFile(Wertpapier.valueOf(wertpapier), targetFile);
		}
	}

}
