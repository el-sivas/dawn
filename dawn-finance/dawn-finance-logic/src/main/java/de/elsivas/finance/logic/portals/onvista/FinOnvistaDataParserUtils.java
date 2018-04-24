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

import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.basic.filedao.CsvFileDao;
import de.elsivas.finance.FinConfig;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.FinFilenameUtils;
import de.elsivas.finance.logic.FinParser;
import de.elsivas.finance.logic.portals.Portal;

public class FinOnvistaDataParserUtils implements FinParser {
	private static final String COL_SCHLUSS = "Schluss";

	private static final String COL_TIEF = "Tief";

	private static final String COL_HOCH = "Hoch";

	private static final String COL_EROEFFNUNG = "Eroeffnung";

	private static final String COL_VOLUMEN = "Volumen";

	private static final String COL_DATUM = "Datum";

	private final static String SIMPLE_DATE_FORMAT = "dd.MM.yyyy";

	public static FinOnvistaDataParserUtils instance;

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
		final File dir = new File(workdir);
		final String filePrefix = FinConfig.DOWNLOAD_FILE_PREFIX + "_" + Portal.ONVISTA.toString();
		final List<File> allFiles = Arrays.asList(dir.listFiles());
		final List<File> onvistaDownloadFiles = allFiles.stream().filter(e -> e.getName().startsWith(filePrefix))
				.collect(Collectors.toList());

		final Set<ShareValuePeriod> set = new TreeSet<>(new Comparator<ShareValuePeriod>() {

			@Override
			public int compare(ShareValuePeriod o1, ShareValuePeriod o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});

		for (File file : onvistaDownloadFiles) {
			final Csv csv = CsvFileDao.read(file);
			final CsvLine line = csv.next();
			set.add(parse(line, FinFilenameUtils.extractWertpapier(file.getName())));
		}
	}

	private ShareValuePeriod parse(final CsvLine line, final Wertpapier wertpapier) {
		return new ShareValuePeriod() {

			@Override
			public BigDecimal getVolume() {
				return line.getValue(COL_VOLUMEN, BigDecimal.class);
			}

			@Override
			public Wertpapier getValue() {
				return wertpapier;
			}

			@Override
			public BigDecimal getLowest() {
				return line.getValue(COL_TIEF, BigDecimal.class);
			}

			@Override
			public BigDecimal getLast() {
				return line.getValue(COL_SCHLUSS, BigDecimal.class);
			}

			@Override
			public BigDecimal getHighest() {
				return line.getValue(COL_HOCH, BigDecimal.class);
			}

			@Override
			public BigDecimal getFirst() {
				return line.getValue(COL_EROEFFNUNG, BigDecimal.class);
			}

			@Override
			public Date getDate() {
				return line.getValue(COL_DATUM, new SimpleDateFormat(SIMPLE_DATE_FORMAT));
			}			
		};
	}
}
