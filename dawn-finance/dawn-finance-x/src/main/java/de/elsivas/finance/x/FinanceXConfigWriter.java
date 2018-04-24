package de.elsivas.finance.x;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import de.elsivas.basic.ESClassesUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.filedao.KeyValueDao;
import de.elsivas.finance.logic.FinConfiguration;

public class FinanceXConfigWriter implements FinanceX {

	String s = "/tmp";

	@Override
	public void run() {

		try {
			runInternal();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new EsRuntimeException("error run internal", e);
		}

	}

	private void runInternal() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final Collection<Class> cs = ESClassesUtils.findSubclassesOf(FinConfiguration.class);
		for (Class c : cs) {
			final FinConfiguration config = FinConfiguration.class.cast(c.newInstance());
			final String simpleName = config.getClass().getSimpleName();
			final String fileName = s + "/" + simpleName + ".config.txt";
			
			final List<String> config2 = config.getConfig();
			Map<String, String> values = new HashMap<>();
			for (String string : config2) {
				values.put(string, "");
			}
			
			KeyValueDao.write(fileName, values);
			
		}
	}

}
