package de.elsivas.finance.logic.portals;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.logic.FinDownloadLinkBuilder;
import de.elsivas.finance.logic.portals.onvista.FinOnvistaDownloadLinkBuilder;

public class Portals {

	public static FinDownloadLinkBuilder getDownloadLinkBuilder(final Portal portal) {
		switch (portal) {
		case ONVISTA:
			return FinOnvistaDownloadLinkBuilder.instance();
		default:
			throw new EsRuntimeException("not supported:" + portal);
		}
	}

}
