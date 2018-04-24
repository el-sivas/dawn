package de.elsivas.finance.x;

import java.util.HashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.filedao.KeyValueDao;
import de.elsivas.finance.logic.FinConfig;

public class Finance {

	private static FinanceX financeX;

	public static void main(String[] args) {
		Configurator.setLevel("de.elsivas", Level.DEBUG);
		
		if (args.length != 2) {
			throw new IllegalArgumentException("args not valid: " + args);
		}
		final String mode = args[0];
		final String fileName = args[1];
		FinConfig.init(new HashMap<>(KeyValueDao.read(fileName)));

		switch (mode) {
		case "download":
			financeX = new FinanceXDownloader();
			break;
		case "write-config":
			financeX = new FinanceXConfigWriter();
			break;
		default:
			throw new EsRuntimeException("invalid mode: " + mode);
		}
		financeX.run();
		KeyValueDao.write(fileName, FinConfig.getValues());
		
	}

}
