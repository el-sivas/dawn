package de.elsivas.basic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {

	public static long minus(final LocalDate a, final LocalDate b) {
		return Math.abs(a.toEpochDay() - b.toEpochDay());
	}
	
	public static long minus(final LocalDateTime a, final LocalDateTime b) {
		ZoneOffset o = ZoneOffset.UTC;
		return Math.abs(a.toEpochSecond(o) - b.toEpochSecond(o));
	}

	public static Date toDate(final LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date toDate(final LocalDate localDate) {
		return toDate(LocalDateTime.of(localDate, LocalTime.MIDNIGHT));
	}

	public static LocalDateTime toLocalDateTime(final Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

}
