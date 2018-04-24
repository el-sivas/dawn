package de.elsivas.finance.x;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.logic.FinDownloader;
import de.elsivas.finance.logic.config.FinConfig;
import de.elsivas.finance.logic.portals.Portal;
import de.elsivas.finance.logic.portals.onvista.FinOnvistaDownloadUtils;

public class FinanceXDownloader implements FinanceX {
	
	private FinDownloader downloader;

	@Override
	public void run() {
		final Portal portal = Portal.of(FinConfig.get(FinConfig.PORTAL));
		switch (portal) {
		case ONVISTA:
			downloader = FinOnvistaDownloadUtils.getInstance();
			break;
		default:
			throw new EsRuntimeException("not supported: " + portal);
		}
		downloader.downloadAndSave();		
	}
}
