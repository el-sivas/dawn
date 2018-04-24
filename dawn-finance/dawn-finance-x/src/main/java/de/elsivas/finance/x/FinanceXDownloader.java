package de.elsivas.finance.x;

import java.util.Arrays;
import java.util.List;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.logic.ESFinConfig;
import de.elsivas.finance.logic.FinConfiguration;
import de.elsivas.finance.logic.Wertpapier;
import de.elsivas.finance.logic.portals.onvista.ESFinOnvistaDownloadUtils;

public class FinanceXDownloader implements FinanceX, FinConfiguration {

	private final static String DOWNLOAD_LIST = "download-list";

	private final static String ONVISTA = "onvista";

	private final static String PORTAL = "portal";

	@Override
	public List<String> getConfig() {
		return Arrays.asList(DOWNLOAD_LIST,PORTAL);
	}

	@Override
	public void run() {
		final List<String> downloadList = Arrays.asList(ESFinConfig.get(DOWNLOAD_LIST).split(";"));
		final String portal = ESFinConfig.get(PORTAL);
		switch (portal) {
		case ONVISTA:
			downloadOnvista(downloadList);
			break;

		default:
			throw new EsRuntimeException("invalid portal :" + portal);
		}
	}

	private void downloadOnvista(List<String> downloadList) {
		for (String wertpapier : downloadList) {
			final String download = ESFinOnvistaDownloadUtils.downloadToFile(Wertpapier.valueOf(wertpapier));
		}		
	}

}
