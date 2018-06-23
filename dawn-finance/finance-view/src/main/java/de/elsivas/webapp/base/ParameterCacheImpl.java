package de.elsivas.webapp.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.elsivas.basic.EsRuntimeException;

public class ParameterCacheImpl implements ParameterCache {

	private static ParameterCacheImpl instance;

	private Map<String, String> map = new HashMap<>();

	public static ParameterCache getInstance() {
		synchronized (ParameterCacheImpl.class) {
			if (instance == null) {
				instance = new ParameterCacheImpl();
			}
		}
		return instance;
	}

	@Override
	public void putValue(final String key, final String value) {
		final String keyToSet = key.toUpperCase();
		if (isSet(keyToSet)) {
			throw new EsRuntimeException(
					"value is already set. key: '" + keyToSet + "', value: '" + map.get(keyToSet) + "'");
		}
		map.put(keyToSet, value);
	}

	@Override
	public void clearValue(final Parameter key) {
		clearValue(key.name());
	}

	@Override
	public void clearValue(final String key) {
		map.remove(key);
	}

	@Override
	public void putValueForce(final Parameter key, final String value) {
		putValue(key.name(), value);
	}

	public void putValueForce(final String key, final String value) {
		clearValue(key);
		putValue(key, value);
	}

	@Override
	public <T> T getValue(final Parameter key, final Class<T> t) {
		return getValue(key.name(), t);
	}

	@Override
	public <T> T getValue(final String key, final Class<T> t) {
		return t.cast(map.get(key));
	}

	@Override
	public String getStringValue(final Parameter key) {
		return getStringValue(key.name());
	}

	@Override
	public String getStringValue(final String key) {
		return getValue(key, String.class);
	}

	@Override
	public void putAll(Map<String, String> map) {
		map.keySet().forEach(e -> putValue(e, map.get(e)));
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean isSet(Parameter key) {
		return isSet(key.name());
	}

	public boolean isSet(String key) {
		return map.containsKey(key.toUpperCase());
	}

	@Override
	public Collection<String> getAvailableKeys() {
		return new ArrayList<>(map.keySet());
	}

}
