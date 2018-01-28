package de.elsivas.basic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

	public static long minus(final LocalDate a, final LocalDate b) {
		return Math.abs(a.toEpochDay() - b.toEpochDay());
	}

	public static Date toDate(final LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDateTime toLocalDateTime(final Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

}
