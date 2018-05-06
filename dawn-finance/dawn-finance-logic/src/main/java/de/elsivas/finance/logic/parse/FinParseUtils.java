package de.elsivas.finance.logic.parse;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.basic.filedao.CsvFileDao;
import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;
import de.elsivas.finance.logic.FinCsvLineParser;
import de.elsivas.finance.logic.FinFilenameUtils;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.portals.Portal;
import de.elsivas.finance.logic.portals.Portals;

public class FinParseUtils {

	private static final ShareValuePeriodFileDao SHARE_VALUE_PERIOD_FILE_DAO = ShareValuePeriodFileDao.getInstance();

	public static void parse(final FinProperties properties, Protocolant protocolant) {
		final String workdir = properties.getWorkdir();
		final Portal portal = properties.getPortal();

		final FinCsvLineParser csvLineParser = Portals.getCsvLineParser(portal);

		final File dir = new File(workdir);
		final String filePrefix = FinConfig.get(FinConfig.DOWNLOAD_FILE_PREFIX) + "_" + portal.toString();
		final List<File> allFiles = Arrays.asList(dir.listFiles());
		final List<File> portalDownloadFiles = allFiles.stream().filter(e -> e.getName().startsWith(filePrefix))
				.collect(Collectors.toList());

		final Set<ShareValuePeriod> set = new TreeSet<>();

		for (File file : portalDownloadFiles) {
			final Csv csv = CsvFileDao.read(file);
			while (csv.hasNext()) {
				final CsvLine line = csv.next();
				final Wertpapier wertpapier = FinFilenameUtils.extractWertpapier(file.getName());
				set.add(parse(csvLineParser.parse(line), wertpapier));
			}
		}

		final String filename = workdir + "/" + FinConfig.get(FinConfig.IMPORT_FILE_PREFIX) + "_" + portal.toString() + "_" + +System.currentTimeMillis()
				+ ".csv";

		SHARE_VALUE_PERIOD_FILE_DAO.saveAll(set, filename);
	}

	private static ShareValuePeriod parse(ShareValuePeriod preParsedPeriod, Wertpapier wertpapier) {
		return new ShareValuePeriod() {

			@Override
			public Date getDate() {
				return preParsedPeriod.getDate();
			}

			@Override
			public BigDecimal getHighest() {
				return preParsedPeriod.getHighest();
			}

			@Override
			public BigDecimal getLowest() {
				return preParsedPeriod.getLowest();
			}

			@Override
			public BigDecimal getFirst() {
				return preParsedPeriod.getFirst();
			}

			@Override
			public BigDecimal getLast() {
				return preParsedPeriod.getLast();
			}

			@Override
			public BigDecimal getVolume() {
				return preParsedPeriod.getVolume();
			}

			@Override
			public Wertpapier getValue() {
				return wertpapier;
			}
		};
	}

	public static Options getOptions() {
		final Options options = new Options();
		options.addOption(new Option(FinProperties.ARG_WORKDIR, true, "Workdir"));
		options.addOption(new Option(FinProperties.ARG_PORTAL, true, "Portal"));
		return options;
	}
}
