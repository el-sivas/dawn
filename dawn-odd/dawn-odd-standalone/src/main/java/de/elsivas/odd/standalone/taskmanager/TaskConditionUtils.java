package de.elsivas.odd.standalone.taskmanager;

import java.time.LocalDateTime;
import java.util.Date;

import de.elsivas.basic.DateUtils;

public class TaskConditionUtils {

	private static String c = "conditioned";

	private static String ct = "condition-type";

	private static String cd = "contition-date";

	public static boolean hasCondition(final TMTask task) {
		return PropertyUtils.isTrue(task, c);
	}
	
	public static Date getDate(final TMTask task) {
		return PropertyUtils.getDate(task, cd);
	}

	public static void setDate(final TMTask task, final ConditionType type, final Date date) {
		PropertyUtils.setProperty(task, ct, type);
		PropertyUtils.setProperty(task, cd, date);
		PropertyUtils.setProperty(task, c, true);
	}

	public static boolean isExecutable(final TMTask task) {
		if (!hasCondition(task)) {
			return true;
		}
		final ConditionType conditionType = PropertyUtils.getConditionType(task, ct);
		switch (conditionType) {
		case NOT_BEFORE_DATE:
			final Date dateBefore = PropertyUtils.getDate(task, cd);
			return !LocalDateTime.now().isBefore(DateUtils.toLocalDateTime(dateBefore));
		case NOT_AFTER_DATE:
			final Date dateAfter = PropertyUtils.getDate(task, cd);
			return !LocalDateTime.now().isAfter(DateUtils.toLocalDateTime(dateAfter));
		default:
			break;
		}
		return false;
	}

	public static String toStringCondition(final TMTask task) {
		if (!hasCondition(task)) {
			return "no condition";
		}
		final ConditionType conditionType = PropertyUtils.getConditionType(task, ct);
		switch (conditionType) {
		case NOT_BEFORE_DATE:
			final Date date = PropertyUtils.getDate(task, cd);
			return ConditionType.NOT_BEFORE_DATE + ": " + date;
		case NOT_AFTER_DATE:
			final Date date2 = PropertyUtils.getDate(task, cd);
			return ConditionType.NOT_AFTER_DATE + ": " + date2;

		default:
			break;
		}
		return "";
	}
}
