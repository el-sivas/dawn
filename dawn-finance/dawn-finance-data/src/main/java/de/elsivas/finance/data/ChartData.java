package de.elsivas.finance.data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.finance.model.Chart;

public class ChartData implements Chart {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	private Map<Date, BigDecimal> mappedData = new HashMap<Date, BigDecimal>();

	private ChartData(final Csv csv) {
		while (csv.hasNext()) {
			final CsvLine line = csv.next();
			final Date date = parse(line.getValue(ChartDataType.DATE.toString()), Date.class);
			final BigDecimal price = parse(line.getValue(ChartDataType.PRICE.toString()), BigDecimal.class);
			mappedData.put(date, price);
		}
	}

	public Csv getRawData() {
		final List<String> formatTitle = formatTitle(ChartDataType.DATE, ChartDataType.PRICE);
		final Csv csv = Csv.createEmpty(formatTitle);
		final Set<Date> keySet = mappedData.keySet();
		for (Date date : keySet) {
			final BigDecimal bigDecimal = mappedData.get(date);
			final List<String> formatData = formatData(date, bigDecimal);
			final CsvLine line = CsvLine.create(formatTitle, formatData);
			csv.add(line);
		}

		return csv;
	}
	
	public static ChartData create() {
		final List<String> titles = Arrays.asList(ChartDataType.values()).stream().map(e -> e.toString())
				.collect(Collectors.toList());
		final Csv csv = Csv.createEmpty(titles);
		return ChartData.create(csv);
	}

	public static ChartData create(Csv csv) {
		final List<String> colTitles = Arrays.asList(ChartDataType.values()).stream().map(e -> e.toString())
				.collect(Collectors.toList());

		final List<String> csvColTitles = csv.getTitle();
		for (String title : csvColTitles) {
			if (!colTitles.contains(title)) {
				throw new EsRuntimeException("no valid col tilte: " + title);
			}
		}
		return new ChartData(csv);
	}

	public BigDecimal getData(final Date date) {
		if (!mappedData.containsKey(date)) {
			return BigDecimal.ZERO;
		}
		return mappedData.get(date);
	}

	public void add(final Date date, final BigDecimal price) {
		mappedData.put(date, price);
	}

	private List<String> formatData(Object... objects) {
		final List<String> list = new ArrayList<>();
		for (Object object : objects) {
			list.add(formatData(object));
		}
		return list;
	}

	private <T extends Object> T parse(String string, Class<T> type) {
		if (Date.class == type) {
			try {
				return type.cast(new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(string));
			} catch (ParseException e) {
				throw new EsRuntimeException("error parse date: " + string, e);
			}
		}
		if (BigDecimal.class == type) {
			return type.cast(BigDecimal.valueOf(Double.valueOf(string)));
		}
		throw new EsRuntimeException("not supported type: " + type);

	}

	private String formatData(Object object) {
		if (object instanceof Date) {
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(object);
		}
		if (object instanceof BigDecimal) {
			return String.valueOf(((BigDecimal) object).doubleValue());
		}
		throw new EsRuntimeException("not supported type: " + object);

	}

	private List<String> formatTitle(ChartDataType... types) {
		return Arrays.asList(types).stream().map(e -> e.toString()).collect(Collectors.toList());
	}

}
