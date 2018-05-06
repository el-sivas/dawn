package de.elsivas.finance.logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import de.elsivas.basic.CliUtils;
import de.elsivas.finance.logic.portals.Portal;

public class FinPropertyUtils {

	public static FinProperties parseToProperties(final Options options, String... args) {
		final CommandLine cl = CliUtils.parseArgs(options, args);

		final String workdir = cl.getOptionValue(FinProperties.ARG_WORKDIR);
		final Portal portal = portal(cl);
		final Collection<String> downloads = downloads(cl);

		return new FinProperties() {

			public String getWorkdir() {
				return workdir;
			}

			public Portal getPortal() {
				return portal;
			}

			public Collection<String> getDownloads() {
				return downloads;
			}
		};
	}

	private static Portal portal(final CommandLine cl) {
		final String optionValue = cl.getOptionValue(FinProperties.ARG_PORTAL);
		if(optionValue == null) {
			return null;
		}
		return Portal.valueOf(optionValue.toUpperCase());
	}

	private static List<String> downloads(final CommandLine cl) {
		final String optionValue = cl.getOptionValue(FinProperties.ARG_DOWNLOADS);
		if (optionValue == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(optionValue.split(","));
	}

}
