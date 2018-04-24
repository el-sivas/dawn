package de.elsivas.finance.logic.portals.onvista;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.ESConsoleUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.SleepUtils;
import de.elsivas.finance.logic.FinDownloader;
import de.elsivas.finance.logic.FinFilenameUtils;
import de.elsivas.finance.logic.Wertpapier;
import de.elsivas.finance.logic.config.FinConfig;
import de.elsivas.finance.logic.config.FinConfigurable;
import de.elsivas.finance.logic.portals.Portal;

public class FinOnvistaDownloadUtils implements FinDownloader, FinConfigurable {

	private static final Log LOG = LogFactory.getLog(FinOnvistaDownloadUtils.class);

	private static FinOnvistaDownloadUtils instance;

	private final static String DOWNLOAD_LIST = "download-list";

	public static String downloadToFile(Wertpapier wp) {
		final String target = "target-file";
		downloadToFile(wp, target);
		return target;
	}

	public static void downloadToFile(Wertpapier wp, String target) {
		final String downloadLink = FinOnvistaDownloadLinkBuilder.buildDownloadLink(wp.getIsin());

		final String workdir = FinConfig.get(FinConfig.WORKDIR);
		if (!new File(workdir).isDirectory()) {
			throw new EsRuntimeException("no dir: " + workdir);
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("wget -O " + workdir + "/DL-" + target + " ");
		// sb.append("\"");
		sb.append(downloadLink);
		// sb.append("\"");

		final String command = sb.toString();
		LOG.info("exec: " + command);
		ESConsoleUtils.runConsoleCommand(command);
		SleepUtils.sleepFor(2000);
	}

	@Override
	public void downloadAndSave() {
		final String downloadList = FinConfig.get(DOWNLOAD_LIST);
		for (String valueToDownload : downloadList.split(";")) {
			StringBuilder commandBuilder = new StringBuilder();
			commandBuilder.append("wget -O ");
			commandBuilder.append(FinConfig.get(FinConfig.WORKDIR) + "/");
			commandBuilder.append(FinFilenameUtils.generateDownloadFilename(Portal.ONVISTA));
			commandBuilder.append(" ");
			commandBuilder.append(FinOnvistaDownloadLinkBuilder.buildDownloadLink(Wertpapier.of(valueToDownload)));
			ESConsoleUtils.runConsoleCommand(commandBuilder.toString());
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
