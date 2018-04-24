package de.elsivas.finance.x;

import java.util.Arrays;
import java.util.List;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.logic.ESFinConfig;
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

	@Override
	public List<String> getConfig() {
		return Arrays.asList(DOWNLOAD_LIST,PORTAL);
	}

	@Override
	public void run() {
		final List<String> downloadList = Arrays.asList(ESFinConfig.get(DOWNLOAD_LIST).split(";"));
		final Portals portal = Portals.valueOf(ESFinConfig.get(PORTAL).toUpperCase());
		switch (portal) {
		case ONVISTA:
			downloadOnvista(downloadList);
			break;

		default:
			throw new EsRuntimeException("invalid portal :" + ESFinConfig.get(PORTAL).toUpperCase());
		}
	}

	private void downloadOnvista(List<String> downloadList) {
		for (String wertpapier : downloadList) {
			final String download = ESFinOnvistaDownloadUtils.downloadToFile(Wertpapier.valueOf(wertpapier));
			
		}		
	}

}
