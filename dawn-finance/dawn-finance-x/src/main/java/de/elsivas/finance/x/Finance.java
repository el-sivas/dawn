package de.elsivas.finance.x;

import java.util.HashMap;

import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.filedao.KeyValueDao;
import de.elsivas.finance.logic.ESFinConfig;

public class Finance {

	private static FinanceX financeX;

	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("args not valid: " + args);
		}
		final String mode = args[0];
		final String fileName = args[1];
		ESFinConfig.init(new HashMap<>(KeyValueDao.read(fileName)));

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
		KeyValueDao.write(fileName, ESFinConfig.getValues());
		
	}

}
