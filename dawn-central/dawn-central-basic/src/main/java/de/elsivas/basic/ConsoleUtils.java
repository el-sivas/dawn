package de.elsivas.basic;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConsoleUtils {

	private static final Log LOG = LogFactory.getLog(ConsoleUtils.class);

	public static String determineUserHome() {
		return System.getProperty("user.home");
	}

	public static void runConsoleCommand(final String command) {
		try {
			runInternal(command);
		} catch (final IOException e) {
			throw new EsRuntimeException("error execute command: " + command, e);
		}
	}

	// TODO: error bzw. return handling. bisher geht alles auf error.
	private static void runInternal(final String command) throws IOException {
		final Process exec = Runtime.getRuntime().exec(command);
		while (exec.isAlive()) {
			LOG.debug("process alive, sleep.");
			SleepUtils.sleepFor(50);
		}

		final String error = readInput(exec.getErrorStream());
		final String input = readInput(exec.getInputStream());
		LOG.info(error);
		// if (!error.isEmpty()) {
		// throw new EsRuntimeException(error);
		// }
	}

	private static String readInput(final InputStream errorStream) throws IOException {
		final StringBuilder errorBuffer = new StringBuilder();
		while (true) {
			final int read = errorStream.read();
			if (read == -1) {
				break;
			}
			final char c = (char) read;
			errorBuffer.append(c);
		}
		return errorBuffer.toString();
	}

}
