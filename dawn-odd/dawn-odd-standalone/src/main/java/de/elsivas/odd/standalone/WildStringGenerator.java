package de.elsivas.odd.standalone;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WildStringGenerator {

	final static char[][] c = { { 'a', 'z' }, { 'A', 'Z' } };

	final static List<char[]> c2 = Arrays.asList(c);

	static {
		Collections.sort(c2, new Comparator<char[]>() {

			@Override
			public int compare(char[] o1, char[] o2) {
				return Character.valueOf(o1[0]).compareTo(Character.valueOf(o2[0]));
			}
		});
	}

	public static String plus(String s) {
		s.toCharArray();
		return null;
	}

	/**
	 * returns if char[] could count up
	 */
	private static boolean countUp(char[] c) {
		int length = c.length;
		for (char[] d : c2) {
			if(c[length] < d[1]) {
				char cx = c[length];
				c[length] = (char) (cx+1);
				return true;
			}
		}
//		Arrays.asList(c).subList(0, length-1).toArray(new char[0]);
		return false;
	}

}
