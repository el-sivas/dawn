package de.elsivas.basic;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class ESOptions {

	public static Options defaultOpt() {
		final Options options = new Options();
		options.addOption("c", true, "config file location");
		options.addOption("h", "help", false, "prints this help");

		return options;
	}

	public static CommandLine defaultAndHandleHelp(String... args) {
		return defaultAndHandleHelp(args, StringUtils.SPACE);
	}

	public static CommandLine defaultAndHandleHelp(String[] args, String applicationName) {
		final Options o = defaultOpt();
		final CommandLineParser clp = new DefaultParser();

		final CommandLine cl;
		try {
			cl = clp.parse(o, args);
		} catch (final ParseException e) {
			throw new EsRuntimeException("error parsing args", e);
		}

		handleHelp(cl, applicationName, o);
		return cl;
	}

	public static void handleHelp(CommandLine cl, String applicationName, Options o) {
		final boolean hasOption = cl.hasOption("h");
		if (!hasOption) {
			return;
		}

		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(applicationName, o);
		System.exit(0);

	}

}
