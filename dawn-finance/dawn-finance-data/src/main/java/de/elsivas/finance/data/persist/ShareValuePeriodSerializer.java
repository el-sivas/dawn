package de.elsivas.finance.data.persist;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.ShareValuePeriod.Value;
import de.elsivas.finance.data.model.Wertpapier;

public class ShareValuePeriodSerializer {

	public static ShareValuePeriod deserialize(CsvLine line) {
		final ShareValuePeriodData svpd = new ShareValuePeriodData();

		svpd.setDate(
				line.getValue(ShareValuePeriod.Value.DATE, new SimpleDateFormat(ShareValuePeriod.SIMPLE_DATE_FORMAT)));
		svpd.setValue(Wertpapier.of(line.getValue(ShareValuePeriod.Value.ISIN.toString())));
		svpd.setFirst(line.getValue(ShareValuePeriod.Value.FIRST, BigDecimal.class));
		svpd.setLast(line.getValue(ShareValuePeriod.Value.LAST, BigDecimal.class));
		svpd.setLowest(line.getValue(ShareValuePeriod.Value.LOWEST, BigDecimal.class));
		svpd.setHighest(line.getValue(ShareValuePeriod.Value.HIGHEST,BigDecimal.class));
		svpd.setVolume(line.getValue(ShareValuePeriod.Value.VOLUME, BigDecimal.class));

		return svpd;
	}

	public static CsvLine serialize(final ShareValuePeriod period) {

		final List<String> titles = new ArrayList<>();
		final List<String> data = new ArrayList<>();

		for (Value title : ShareValuePeriod.Value.values()) {
			titles.add(title.toString());
			data.add(value(period, title));
		}
		return CsvLine.create(titles, data);
	}

	private static String value(ShareValuePeriod period, Value value) {
		switch (value) {
		case DATE:
			return new SimpleDateFormat(ShareValuePeriod.SIMPLE_DATE_FORMAT).format(period.getDate());
		case FIRST:
			return bigDecimalToString(period.getFirst());
		case HIGHEST:
			return bigDecimalToString(period.getHighest());
		case LAST:
			return bigDecimalToString(period.getLast());
		case LOWEST:
			return bigDecimalToString(period.getLowest());
		case VOLUME:
			return bigDecimalToString(period.getVolume());
		case ISIN:
			return period.getValue().getIsin();
		default:
			throw new EsRuntimeException("not supported: " + value);
		}
	}

	private static String bigDecimalToString(final BigDecimal volume) {
		return String.valueOf(volume.doubleValue());
	}

}
