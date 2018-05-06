package de.elsivas.finance.logic.download;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import de.elsivas.basic.ConsoleUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.FinDownloadLinkBuilder;
import de.elsivas.finance.logic.FinFilenameUtils;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.portals.Portal;
import de.elsivas.finance.logic.portals.Portals;

public class FinDownloadUtils {

	public static void download(final FinProperties properties, Protocolant protocolant) {
		configureAndAssertProperties(properties);
		final Collection<String> downloads = properties.getDownloads();
		final String workdir = properties.getWorkdir();
		final Portal portal = properties.getPortal();

		final FinDownloadLinkBuilder downloadLinkBuilder = Portals.getDownloadLinkBuilder(portal);

		for (String valueToDownload : downloads) {
			protocolant.append("download: " + valueToDownload);
			StringBuilder commandBuilder = new StringBuilder();

			commandBuilder.append("wget -O ");
			commandBuilder.append(workdir + "/");
			commandBuilder.append(FinFilenameUtils.generateDownloadFilename(portal, Wertpapier.of(valueToDownload)));
			commandBuilder.append(" ");
			commandBuilder.append(downloadLinkBuilder.buildDownloadLink(Wertpapier.of(valueToDownload)));
			ConsoleUtils.runConsoleCommand(commandBuilder.toString());
		}
	}

	public static void download(final FinProperties properties, StringBuilder sb) {
		final Protocolant protocolant = Protocolant.instance();
		download(properties, protocolant);
		sb.append(protocolant.toProtocol());

	}

	private static void configureAndAssertProperties(FinProperties properties) {
		if (properties.getPortal() == null) {
			throw new EsRuntimeException("portal null");
		}
		if (StringUtils.isBlank(properties.getWorkdir())) {
			throw new EsRuntimeException("workdir null");
		}
		if (properties.getDownloads() == null) {
			throw new EsRuntimeException("downloads null");
		}
	}

	public static CommandLine parseArgs(String... args) {
		final CommandLineParser parser = new DefaultParser();
		try {
			return parser.parse(getOptions(), args);
		} catch (ParseException e) {
			throw new EsRuntimeException("error parsing args", e);
		}
	}

	public static Options getOptions() {
		final Options options = new Options();
		options.addOption(new Option(FinProperties.ARG_WORKDIR, true, "Work dir"));
		options.addOption(new Option(FinProperties.ARG_PORTAL, true, "Portal"));
		options.addOption(new Option(FinProperties.ARG_DOWNLOADS, true, "Downloads (comma separated)"));
		return options;
	}

	public static FinProperties parseArgsToProperties(String... args) {
		final CommandLine cl = parseArgs(args);

		final String workdir = cl.getOptionValue(FinProperties.ARG_WORKDIR);
		final Portal portal = Portal.valueOf(cl.getOptionValue(FinProperties.ARG_PORTAL).toUpperCase());
		final Collection<String> downloads = Arrays.asList(cl.getOptionValue(FinProperties.ARG_DOWNLOADS).split(","));

		return new FinProperties() {

			public String getWorkdir() {
				return workdir;
			}

			public Portal getPortal() {
				return portal;
			}

			public Collection<String> getDownloads() {
				return downloads;
			}
		};
	}

};