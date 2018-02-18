package de.elsivas.odd.standalone.taskmanager;

import javax.naming.OperationNotSupportedException;

public class PropertyUtils {

	public static void setProperty(TMTask task, String key, Object value) {
		if (value instanceof String) {
			task.setProperty(key, (String) value);
			return;
		}
		throw new RuntimeException(new OperationNotSupportedException(value.getClass().getName()));
	}

	public String getStringValue(TMTask task, String key) {
		return (String) getProperty(task, key, PropertyTypes.STRING);
	}

	private static Object getProperty(TMTask task, String key, PropertyTypes type) {
		if (type == PropertyTypes.STRING) {
			return task.getPropertiy(key);
		}
		throw new RuntimeException(new OperationNotSupportedException(type.name()));
	}

}
