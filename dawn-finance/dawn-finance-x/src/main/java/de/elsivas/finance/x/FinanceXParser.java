package de.elsivas.finance.x;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.logic.FinParser;
import de.elsivas.finance.logic.portals.Portal;
import de.elsivas.finance.logic.portals.onvista.FinOnvistaDataParserUtils;

public class FinanceXParser implements FinanceX {
	
	private FinParser finParser;

	@Override
	public void run() {
		final Portal portal = Portal.of(FinConfig.get(FinConfig.PORTAL));

		switch (portal) {
		case ONVISTA:
			finParser = FinOnvistaDataParserUtils.instance;
			break;

		default:
			throw new EsRuntimeException("not supported: " + portal);
		}
		finParser.parseAndSave();
	}

}
