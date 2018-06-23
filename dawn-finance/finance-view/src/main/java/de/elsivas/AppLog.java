package de.elsivas;

import java.util.Date;

public class AppLog {

	private static final StringBuffer sb = new StringBuffer();

	public static void append(String s) {
		sb.append(new Date() + ": " + s + "\n");
	}

	public static String getLog() {
		return sb.toString();
	}

}
