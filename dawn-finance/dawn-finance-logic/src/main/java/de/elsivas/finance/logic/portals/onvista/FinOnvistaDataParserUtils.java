package de.elsivas.finance.logic.portals.onvista;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.basic.filedao.CsvFileDao;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;
import de.elsivas.finance.logic.FinFilenameUtils;
import de.elsivas.finance.logic.FinParser;
import de.elsivas.finance.logic.portals.Portal;

@Deprecated
public class FinOnvistaDataParserUtils implements FinParser {
	private static final String COL_SCHLUSS = "Schluss";

	private static final String COL_TIEF = "Tief";

	private static final String COL_HOCH = "Hoch";

	private static final String COL_EROEFFNUNG = "Eroeffnung";

	private static final String COL_VOLUMEN = "Volumen";

	private static final String COL_DATUM = "Datum";

	private final static String ONVISTA_SIMPLE_DATE_FORMAT = "dd.MM.yyyy";

	private static FinOnvistaDataParserUtils instance;

	private ShareValuePeriodFileDao shareValuePeriodFileDao = ShareValuePeriodFileDao.getInstance();

	private FinOnvistaDataParserUtils() {

	}

	public static FinParser getInstance() {
		if (instance == null) {
			instance = new FinOnvistaDataParserUtils();
		}
		return instance;
	}

	@Override
	public void parseAndSave() {
		final String workdir = FinConfig.get(FinConfig.WORKDIR);
		final Portal portal = Portal.ONVISTA;

		final File dir = new File(workdir);
		final String filePrefix = FinConfig.get(FinConfig.DOWNLOAD_FILE_PREFIX) + "_" + portal.toString();
		final List<File> allFiles = Arrays.asList(dir.listFiles());
		final List<File> onvistaDownloadFiles = allFiles.stream().filter(e -> e.getName().startsWith(filePrefix))
				.collect(Collectors.toList());

		final Set<ShareValuePeriod> set = new TreeSet<>();

		for (File file : onvistaDownloadFiles) {
			final Csv csv = CsvFileDao.read(file);
			while (csv.hasNext()) {
				final CsvLine line = csv.next();
				set.add(parse(line, FinFilenameUtils.extractWertpapier(file.getName())));
			}
		}

		final String filename = workdir + "/IMP_" + portal.toString() + "_"
				+ +System.currentTimeMillis() + ".csv";

		shareValuePeriodFileDao.saveAll(set, filename);

	}

	private ShareValuePeriod parse(final CsvLine line, final Wertpapier wertpapier) {
		return new ShareValuePeriod() {

			@Override
			public BigDecimal getVolume() {
				return BigDecimal.valueOf(parseToDouble(line.getValue(COL_VOLUMEN)));
			}

			@Override
			public Wertpapier getValue() {
				return wertpapier;
			}

			@Override
			public BigDecimal getLowest() {
				return BigDecimal.valueOf(parseToDouble(line.getValue(COL_TIEF)));
			}

			@Override
			public BigDecimal getLast() {
				return BigDecimal.valueOf(parseToDouble(line.getValue(COL_SCHLUSS)));
			}

			@Override
			public BigDecimal getHighest() {
				return BigDecimal.valueOf(parseToDouble(line.getValue(COL_HOCH)));
			}

			@Override
			public BigDecimal getFirst() {
				return BigDecimal.valueOf(parseToDouble(line.getValue(COL_EROEFFNUNG)));
			}

			@Override
			public Date getDate() {
				return line.getValue(COL_DATUM, new SimpleDateFormat(ONVISTA_SIMPLE_DATE_FORMAT));
			}

			/**
			 * parst europaeische Schreibweise nach technischer,
			 */
			private Double parseToDouble(String s) {
				if (StringUtils.isEmpty(s)) {
					return 0d;
				}
				final String trim = s.trim();
				final String replace = trim.replaceAll("\\.", "");
				final String replaceAll = replace.replaceAll(",", ".");
				return Double.valueOf(replaceAll);
			}
		};
	}
}
