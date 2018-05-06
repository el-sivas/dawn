package de.elsivas.finance.logic.portals.onvista;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.ConsoleUtils;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.FinConfigurable;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.FinDownloader;
import de.elsivas.finance.logic.FinFilenameUtils;
import de.elsivas.finance.logic.portals.Portal;

public class FinOnvistaDownloadUtils implements FinDownloader, FinConfigurable {

	private static final Log LOG = LogFactory.getLog(FinOnvistaDownloadUtils.class);

	private static FinOnvistaDownloadUtils instance;

	private final static String DOWNLOAD_LIST = "download-list";

	@Override
	public void downloadAndSave() {
		final String downloadList = FinConfig.get(DOWNLOAD_LIST);
		for (String valueToDownload : downloadList.split(";")) {
			LOG.info("download: " + valueToDownload);
			StringBuilder commandBuilder = new StringBuilder();
			commandBuilder.append("wget -O ");
			commandBuilder.append(FinConfig.get(FinConfig.WORKDIR) + "/");
			commandBuilder
					.append(FinFilenameUtils.generateDownloadFilename(Portal.ONVISTA, Wertpapier.of(valueToDownload)));
			commandBuilder.append(" ");
			commandBuilder.append(FinOnvistaDownloadLinkBuilder.instance()
					.buildDownloadLink(Wertpapier.of(valueToDownload)));
			ConsoleUtils.runConsoleCommand(commandBuilder.toString());
		}
	}

	@Override
	public List<String> getConfig() {
		return Arrays.asList(DOWNLOAD_LIST, FinConfig.PORTAL);
	}

	public static FinOnvistaDownloadUtils getInstance() {
		if (instance == null) {
			instance = new FinOnvistaDownloadUtils();
		}
		return instance;
	}

}
