package de.elsivas.basic;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CliUtils {

	public static CommandLine parseArgs(final Options options, final String... args) {
		final CommandLineParser parser = new DefaultParser();
		try {
			return parser.parse(options, args);
		} catch (final ParseException e) {
			throw new EsRuntimeException("error parsing args", e);
		}
	}

}
