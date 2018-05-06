package de.elsivas.finance.standalone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.FinPropertyUtils;
import de.elsivas.finance.logic.parse.FinParseUtils;

public class FinParse {

	private static final Log LOG = LogFactory.getLog(FinParse.class);

	private static final String VERSION = "0.2-SN";

	public static void main(String[] args) {
		Configurator.setLevel("de.elsivas", Level.INFO);
		LOG.info(FinParse.class.getSimpleName());
		LOG.info("Version: " + VERSION);

		final FinProperties properties = FinPropertyUtils.parseToProperties(FinParseUtils.getOptions(), args);
		final Protocolant protocolant = Protocolant.instance();
		FinParseUtils.parse(properties, protocolant);
		LOG.info(protocolant.toProtocol());
	}

}
