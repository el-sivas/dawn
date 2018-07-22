package de.elsivas.finance.data.persist;

import java.math.BigDecimal;
import java.util.Date;

import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;

public class ShareValuePeriodData implements ShareValuePeriod {

	private Date date;
	private BigDecimal highest;
	private BigDecimal lowest;
	private BigDecimal first;
	private BigDecimal volume;
	private Wertpapier value;
	private BigDecimal last;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getHighest() {
		return highest;
	}

	public void setHighest(BigDecimal highest) {
		this.highest = highest;
	}

	public BigDecimal getLowest() {
		return lowest;
	}

	public void setLowest(BigDecimal lowest) {
		this.lowest = lowest;
	}

	public BigDecimal getFirst() {
		return first;
	}

	public void setFirst(BigDecimal first) {
		this.first = first;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Wertpapier getValue() {
		return value;
	}

	public void setValue(Wertpapier value) {
		this.value = value;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "ShareValuePeriodData [date=" + date + ", highest=" + highest + ", lowest=" + lowest + ", first=" + first
				+ ", volume=" + volume + ", value=" + value + ", last=" + last + "]";
	}

}
