package de.elsivas.finance.data.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represent a period of a share value, i.e. a day.
 */
public interface ShareValuePeriod extends Comparable<ShareValuePeriod> {
	
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	public enum Value {
		DATE, HIGHEST, LOWEST, FIRST, LAST, VOLUME, ISIN
	}

	Date getDate();

	BigDecimal getHighest();

	BigDecimal getLowest();

	BigDecimal getFirst();

	BigDecimal getLast();

	BigDecimal getVolume();

	Wertpapier getValue();

	@Override
	default int compareTo(ShareValuePeriod o) {
		return getDate().compareTo(o.getDate());
	}

}
