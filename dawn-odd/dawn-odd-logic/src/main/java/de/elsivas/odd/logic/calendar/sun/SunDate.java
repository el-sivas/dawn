package de.elsivas.odd.logic.calendar.sun;

import java.time.LocalDate;

public class SunDate {

	private LocalDate date;

	private SunDate(final LocalDate date) {
		this.date = date;
	}

	public static SunDate now() {
		return of(LocalDate.now());
	}

	public static SunDate of(final int year) {
		return of(SunDateUtils.ZERO_DAY.plusYears(year));
	}

	public static SunDate ofEpocDay(final long epocDay) {
		return of(LocalDate.ofEpochDay(epocDay));
	}
	
	public static SunDate of(final LocalDate localDate) {
		return new SunDate(localDate);
	}

	public int dayOfYear() {
		return SunDateUtils.dayOfYear(this);
	}

	public LocalDate localDateValue() {
		return date;
	}

	public int year() {
		return SunDateUtils.year(this);
	}

	public SunMonth month() {
		return SunDateUtils.month(this);
	}

	@Override
	public String toString() {
		String y = Integer.toString(year());
		while (y.length() < 4) {
			y = "0" + y;

		}
		return y + "|" + month().getShort();
	}

}
