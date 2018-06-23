package de.elsivas.basic;

import java.lang.reflect.Array;

public class Collections3 {

	@SuppressWarnings("unchecked")
	public static <T> T[] emptyArray(Class<T> type) {
		return (T[]) Array.newInstance(type, 0);
	}

}
