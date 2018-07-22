package de.elsivas.webapp.base;

import java.util.Collection;
import java.util.Map;

public interface ParameterCache {

	void putValue(String key, String value);

	void putAll(Map<String, String> map);

	<T> T getValue(String key, Class<T> t);

	String getStringValue(String key);

	void clearValue(String key);

	String getStringValue(Parameter key);

	<T> T getValue(Parameter key, Class<T> t);

	void putValueForce(Parameter key, String value);

	void clearValue(Parameter key);

	void clear();
	
	public boolean isSet(Parameter key);
	
	public Collection<String> getAvailableKeys();

}
