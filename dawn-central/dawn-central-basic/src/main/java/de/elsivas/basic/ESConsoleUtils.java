package de.elsivas.basic;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @deprecated Use ConsoleUtils instead
 *
 */
@Deprecated
public class ESConsoleUtils {

	private static final Log LOG = LogFactory.getLog(ESConsoleUtils.class);

	public static void runConsoleCommand(final String command) {
		ConsoleUtils.runConsoleCommand(command);
		try {
			runInternal(command);
		} catch (final IOException e) {
			throw new EsRuntimeException("error execute command: " + command, e);
		}
	}

	private static void runInternal(final String command) throws IOException {
		final Process exec = Runtime.getRuntime().exec(command);

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
