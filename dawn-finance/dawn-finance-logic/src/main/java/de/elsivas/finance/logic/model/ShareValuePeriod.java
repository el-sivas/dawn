package de.elsivas.finance.logic.model;

import java.math.BigDecimal;
import java.util.Date;

import de.elsivas.finance.logic.Wertpapier;

/**
 * Represent a period of a share value, i.e. a day.
 */
public interface ShareValuePeriod {
	
	public enum Value {
		DATE,HIGHEST,LOWEST,FIRST,LAST,VOLUME, ISIN
	}

	Date getDate();

	BigDecimal getHighest();

	BigDecimal getLowest();

	BigDecimal getFirst();

	BigDecimal getLast();
	
	BigDecimal getVolume();
	
	Wertpapier getValue();

}
