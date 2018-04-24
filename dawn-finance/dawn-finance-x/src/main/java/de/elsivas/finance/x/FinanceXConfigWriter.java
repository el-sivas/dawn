package de.elsivas.finance.x;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.elsivas.basic.ESClassesUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.filedao.KeyValueDao;
import de.elsivas.finance.logic.FinConfiguration;

/**
 * Writes emtpy config file per
 *
 */
public class FinanceXConfigWriter implements FinanceX {

	final private static String OUTPUT_DIR = "/tmp/fin";

	@Override
	public void run() {

		try {
			runInternal();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new EsRuntimeException("error run internal", e);
		}

	}

	private void runInternal() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final File file = new File(OUTPUT_DIR);
		if (!file.isDirectory()) {
			throw new EsRuntimeException("no dir: " + OUTPUT_DIR);
		}

		final Collection<Class> cs = ESClassesUtils.findSubclassesOf(FinConfiguration.class);
		for (Class c : cs) {
			final FinConfiguration config = FinConfiguration.class.cast(c.newInstance());

			String configFileName = config.getConfigFileName();
			if (configFileName == null) {
				configFileName = config.getClass().getSimpleName();
			}

			final String fileName = OUTPUT_DIR + "/" + configFileName + ".config.txt";

			final List<String> config2 = config.getConfig();
			Map<String, String> values = new HashMap<>();
			for (String string : config2) {
				values.put(string, "");
			}

			KeyValueDao.write(fileName, values);

		}
	}

}
