package de.elsivas.basic.file.csv;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.elsivas.basic.EsRuntimeException;

public class CsvLine {

	private final List<String> title = new ArrayList<>();

	private final List<String> data = new ArrayList<>();

	private CsvLine(final List<String> title, final List<String> data) {
		this.title.addAll(title);
		this.data.addAll(data);
	}

	public static CsvLine create(final List<String> title, final List<String> data) {
		return new CsvLine(title, data);
	}

	public List<String> getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return title + " " + data;
	}

	public String getValue(final String valueTitle) {
		return getValue(valueTitle, String.class);
	}

	/**
	 *
	 * @param colTitle
	 * @param type
	 *            supported: {@link String}, {@link Integer}, {@link Double},
	 *            {@link BigDecimal}
	 *
	 * @return
	 */
	public <T extends Object> T getValue(String colTitle, Class<T> type) {
		final int indexOf = title.indexOf(colTitle);
		final String value = data.get(indexOf).trim();

		if (String.class.equals(type)) {
			return type.cast(value);
		}

		if (Integer.class.equals(type)) {
			return type.cast(Integer.valueOf(value));
		}

		if (Double.class.equals(type)) {
			return type.cast(Double.valueOf(value));
		}

		if (BigDecimal.class.equals(type)) {
			return type.cast(BigDecimal.valueOf(Double.valueOf(value)));
		}

		if (LocalDate.class.equals(type)) {
			final String[] split = value.split("-");
			final Integer day = Integer.valueOf(split[2]);
			final Integer month = Integer.valueOf(split[1]);
			final Integer year = Integer.valueOf(split[0]);
			return type.cast(LocalDate.of(year, month, day));
		}

		return type.cast(value);
	}

	public Date getValue(String colTitle, SimpleDateFormat simpleDateFormat) {
		try {
			return simpleDateFormat.parse(getValue(colTitle));
		} catch (final ParseException e) {
			throw new EsRuntimeException("error parsing", e);
		}

	}
}
