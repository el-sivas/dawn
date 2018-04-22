package de.elsivas.basic;

import java.io.IOException;

public class ESConsoleUtils {

	public static void runConsoleCommand(final String command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new EsRuntimeException("error execute command: " + command, e);
		}
	}

}
