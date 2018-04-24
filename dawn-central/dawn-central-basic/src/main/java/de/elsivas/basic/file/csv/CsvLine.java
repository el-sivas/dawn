package de.elsivas.basic.file.csv;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvLine {

	private List<String> title = new ArrayList<>();

	private List<String> data = new ArrayList<>();

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

	public <T extends Object> T getValue(String valueTitle, Class<T> type) {
		final int indexOf = title.indexOf(valueTitle);
		final String value = data.get(indexOf);

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
}
