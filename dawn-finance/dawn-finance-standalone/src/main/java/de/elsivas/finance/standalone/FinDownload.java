package de.elsivas.finance.standalone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.FinPropertyUtils;
import de.elsivas.finance.logic.download.FinDownloadUtils;

public class FinDownload {

	private static final Log LOG = LogFactory.getLog(FinDownload.class);

	private static final String VERSION = "0.3-SN";

	public static void main(String[] args) {
		Configurator.setLevel("de.elsivas", Level.INFO);
		LOG.info(FinDownload.class.getSimpleName());
		LOG.info("Version: " + VERSION);

		final FinProperties properties = FinPropertyUtils.parseToProperties(FinDownloadUtils.getOptions(), args);
		final Protocolant protocolant = Protocolant.instance();
		FinDownloadUtils.download(properties, protocolant);
		LOG.info(protocolant.toProtocol());
	}

}
