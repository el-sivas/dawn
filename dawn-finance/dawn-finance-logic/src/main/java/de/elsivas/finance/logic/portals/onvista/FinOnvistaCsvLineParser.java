package de.elsivas.finance.logic.portals.onvista;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.FinCsvLineParser;

public class FinOnvistaCsvLineParser implements FinCsvLineParser {

	private static final String COL_SCHLUSS = "Schluss";

	private static final String COL_TIEF = "Tief";

	private static final String COL_HOCH = "Hoch";

	private static final String COL_EROEFFNUNG = "Eroeffnung";

	private static final String COL_VOLUMEN = "Volumen";

	private static final String COL_DATUM = "Datum";

	private final static String ONVISTA_SIMPLE_DATE_FORMAT = "dd.MM.yyyy";

	public static FinOnvistaCsvLineParser instance() {
		return new FinOnvistaCsvLineParser();
	}

	@Override
	public ShareValuePeriod parse(CsvLine line) {
		return new ShareValuePeriod() {

			@Override
			public BigDecimal getVolume() {
				return BigDecimal.valueOf(parseToDouble(line.getValue(COL_VOLUMEN)));
			}

			@Override
			public Wertpapier getValue() {
				return null;
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
