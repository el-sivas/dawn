package de.elsivas.finance.x;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.elsivas.basic.ESClassesUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.filedao.KeyValueDao;
import de.elsivas.finance.logic.config.FinConfig;
import de.elsivas.finance.logic.config.FinConfigurable;

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

	@SuppressWarnings("rawtypes")
	private void runInternal() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final File file = new File(OUTPUT_DIR);
		if (!file.isDirectory()) {
			throw new EsRuntimeException("no dir: " + OUTPUT_DIR);
		}

		final Collection<Class> cs = ESClassesUtils.findSubclassesOf(FinConfigurable.class);
		for (Class c : cs) {
			final FinConfigurable config = FinConfigurable.class.cast(c.newInstance());

			for (String string : config.getConfig()) {
				FinConfig.set(string, "novalue");
			}
		}
	}

}
