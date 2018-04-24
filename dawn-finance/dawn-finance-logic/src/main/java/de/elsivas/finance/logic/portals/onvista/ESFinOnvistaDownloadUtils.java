package de.elsivas.finance.logic.portals.onvista;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.ESConsoleUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.SleepUtils;
import de.elsivas.finance.logic.FinConfig;
import de.elsivas.finance.logic.Wertpapier;

public class ESFinOnvistaDownloadUtils {
	
	private static final Log LOG = LogFactory.getLog(ESFinOnvistaDownloadUtils.class);

	public static String downloadToFile(Wertpapier wp) {
		final String target = "target-file";
		downloadToFile(wp, target);
		return target;
	}

	public static void downloadToFile(Wertpapier wp, String target) {
		final String downloadLink = ESFinOnvistaDownloadLinkBuilder.buildDownloadLink(wp.getIsin());

		final String workdir = FinConfig.get(FinConfig.WORKDIR);
		if (!new File(workdir).isDirectory()) {
			throw new EsRuntimeException("no dir: " + workdir);
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("wget -O " + workdir + "/DL-" + target + " ");
//		sb.append("\"");
		sb.append(downloadLink);
//		sb.append("\"");
	
		final String command = sb.toString();
		LOG.info("exec: " + command);
		ESConsoleUtils.runConsoleCommand(command);
		SleepUtils.sleepFor(2000);
	}

}
