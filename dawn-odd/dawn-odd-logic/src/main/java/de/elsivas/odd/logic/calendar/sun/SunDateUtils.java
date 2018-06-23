	package de.elsivas.odd.logic.calendar.sun;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.elsivas.basic.DateUtils;
import de.elsivas.logic.InternalLogicException;

public class SunDateUtils {

	public static final LocalDate ZERO_DAY = LocalDate.of(1970, 12, 21);

	private static final int BETWEEN_THE_YEARS = LocalDate.of(1999, 12, 21)
			.minusDays(LocalDate.of(2000, 1, 1).getDayOfYear()).getDayOfYear();

	private static final Map<SunMonth, Integer> DAYS_OF_MONTH = new HashMap<SunMonth, Integer>();

	static {
		Arrays.asList(SunMonth.values()).forEach(e -> DAYS_OF_MONTH.put(e, e.getDaysInMonth()));
	}

	public static SunMonth month(final SunDate date) {
		final Map<SunMonth, Integer> map = getCorrectDaysOfMonth(date);

		final int dayOfYear = date.dayOfYear();
		int sumDays = 0;
		for (SunMonth sunMonth : SunMonth.values()) {
			sumDays += sunMonth.getDaysInMonth();
			if (sumDays >= dayOfYear) {
				return sunMonth;
			}
		}
		return SunMonth.NADIR;
	}

	private static Map<SunMonth, Integer> getCorrectDaysOfMonth(final SunDate date) {
		final Map<SunMonth, Integer> map = new HashMap<SunMonth, Integer>();
		map.putAll(DAYS_OF_MONTH);
		if (isSchaltJahr(date)) {
			map.put(SunMonth.NADIR, map.get(SunMonth.NADIR) + 1);
		}
		return map;
	}

	public static int year(final SunDate date) {
		final LocalDate localDateValue = date.localDateValue();
		int year = isNewYear(localDateValue) ? 0 : 1;

		LocalDate cnt = ZERO_DAY;
		int i = 0;
		while (cnt.isBefore(localDateValue)) {
			i++;
			cnt = cnt.plusYears(1);
		}
		return i;
	}

	public static int dayOfYear(final SunDate date) {
		return currentDoY0(date);
	}

	private static int currentDoY0(final SunDate date) {
		final LocalDate lastNewYearsDay = getLastNewYearsDay(date);
		long minus = DateUtils.daysDifference(date.localDateValue(), lastNewYearsDay);
		if (minus < 0 || minus > Integer.MAX_VALUE) {
			throw new InternalLogicException(minus + "is not valid");
		}

		return (int) minus;
	}

	private static LocalDate getLastNewYearsDay(SunDate date) {
		final LocalDate localDate = date.localDateValue();

		if (isNewYear(localDate)) {
			return LocalDate.of(localDate.getYear(), 12, 21 - 15);
		}
		return LocalDate.of(localDate.getYear() - 1, 12, 21 - 15);

	}

	private static boolean isNewYear(final LocalDate localDate) {
		if (localDate.getMonth() != Month.DECEMBER) {
			return false;
		}
		if (localDate.getDayOfMonth() < 21 - 15) {
			return false;
		}
		return true;
	}

	public static boolean isSchaltJahr(final SunDate date) {
		final int year = date.year();
		if (year % 4 == 0) {
			if (year % 100 == 0 && year % 400 == 0) {
				return true;
			} else {
				if (year % 100 == 0 && year % 400 != 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
