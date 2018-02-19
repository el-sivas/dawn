package de.elsivas.odd.standalone.taskmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang3.BooleanUtils;

public class PropertyUtils {

	public static String sdfPattern = "yyyy-MM-dd_HH:mm:ss";

	public static void setProperty(final TMTask task, final String key, final Object value) {
		if (value instanceof String) {
			task.setProperty(key, (String) value);
			return;
		}
		if (value instanceof Boolean) {
			setProperty(task, key, String.valueOf((boolean) value));
			return;
		}

		if (value instanceof Date) {
			setProperty(task, key, new SimpleDateFormat(sdfPattern).format((Date) value));
			return;
		}

		if (value instanceof Enum) {
			setProperty(task, key, ((Enum) value).name());
			return;
		}
		throw new RuntimeException(new OperationNotSupportedException(value.getClass().getName()));
	}

	public static ConditionType getConditionType(final TMTask task, final String key) {
		return ConditionType.valueOf(task.getPropertiy(key));
	}

	public static Date getDate(final TMTask task, final String key) {
		try {
			return new SimpleDateFormat(sdfPattern).parse(task.getPropertiy(key));
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isTrue(final TMTask task, final String key) {
		return BooleanUtils.isTrue(Boolean.valueOf(task.getPropertiy(key)));
	}

	public static String getString(final TMTask task, final String key) {
		return task.getPropertiy(key);
	}

}
