package de.elsivas.basic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {

	public static int daysDifference(final LocalDate a, final LocalDate b) {
		return (int) Math.abs(a.toEpochDay() - b.toEpochDay());
	}

	public static long secondsDifference(final LocalDateTime a, final LocalDateTime b) {
		final ZoneOffset o = ZoneOffset.UTC;
		return Math.abs(a.toEpochSecond(o) - b.toEpochSecond(o));
	}

	public static Date toDate(final LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(final LocalDate localDate) {
		return toDate(LocalDateTime.of(localDate, LocalTime.MIDNIGHT));
	}

	public static LocalDate toLocalDate(final Date date) {
		return toLocalDateTime(date).toLocalDate();
	}

	public static LocalDateTime toLocalDateTime(final Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Checks if 'test' is within 'early' and 'late' included the boarders.
	 */
	public static boolean isWithin(LocalDate early, LocalDate late, LocalDate test) {
		if (early.isAfter(late)) {
			throw new EsRuntimeException("early after late");
		}
		if (test.isBefore(early)) {
			return false;
		}
		if (test.isAfter(late)) {
			return false;
		}
		return true;
	}

}
