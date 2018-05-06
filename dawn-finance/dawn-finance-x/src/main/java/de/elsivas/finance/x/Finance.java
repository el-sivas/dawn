package de.elsivas.finance.x;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.filedao.KeyValueDao;
import de.elsivas.finance.FinConfig;

public class Finance {

	private static final String WRITE_CONFIG = "w";
	private static final String DOWNLOAD = "d";
	private static final String PARSE = "p";
	private static FinanceX financeX;

	public static void main(String[] args) {
		Configurator.setLevel("de.elsivas", Level.INFO);

		if (args == null || args.length == 0) {
			throw new IllegalArgumentException("args empty");
		}

		if (!args[0].equals(WRITE_CONFIG) && args.length < 2) {
			throw new IllegalArgumentException("args not valid: " + Arrays.asList(args));
		}
		final String mode = args[0];

		final String fileName;
		if (args.length > 1) {
			fileName = args[1];
			FinConfig.init(new HashMap<>(KeyValueDao.read(fileName)));
		} else {
			fileName = "/tmp/fin/config.txt";
		}

		switch (mode) {
		case DOWNLOAD:
			financeX = new FinanceXDownloader();
			break;
		case WRITE_CONFIG:
			financeX = new FinanceXConfigWriter();
			break;
		case PARSE:
			financeX = new FinanceXParser();
			break;
		default:
			throw new EsRuntimeException("invalid mode: " + mode);
		}
		financeX.run();
		KeyValueDao.write(fileName, FinConfig.getValues());

	}

	private static void options() {
		final Options options = new Options();
		options.addOption("m", true, "set Mode").a;
		CommandLineParser clp = new DefaultParser();
		options.
	}

}
