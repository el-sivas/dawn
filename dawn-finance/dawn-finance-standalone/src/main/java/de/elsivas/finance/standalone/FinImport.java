package de.elsivas.finance.standalone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.FinPropertyUtils;
import de.elsivas.finance.logic.imports.FinImportUtils;

public class FinImport {

	private static final Log LOG = LogFactory.getLog(FinImport.class);

	private static final String VERSION = "0.1";

	public static void main(String[] args) {
		Configurator.setLevel("de.elsivas", Level.INFO);
		LOG.info(FinImport.class.getSimpleName());
		LOG.info("Version: " + VERSION);

		final FinProperties properties = FinPropertyUtils.parseToProperties(FinImportUtils.getOptions(), args);
		final Protocolant protocolant = Protocolant.instance();
		FinImportUtils.importData(properties, protocolant);
		LOG.info(protocolant.toProtocol());
	}

}
