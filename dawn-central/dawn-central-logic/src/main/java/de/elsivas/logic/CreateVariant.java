package de.elsivas.logic;

import java.util.HashMap;
import java.util.Map;

public class CreateVariant {

	final Map<String, Object> variant = new HashMap<>();
	
	private CreateVariant() {
		
	}
	
	public static CreateVariant instance() {
		return new CreateVariant();
		
	}

	public void put(final String key, final Object o) {
		variant.put(key, o);
	}

	public <T> T get(final String key, Class<T> c) {
	    return c.cast(variant.get(key));
	}

}
