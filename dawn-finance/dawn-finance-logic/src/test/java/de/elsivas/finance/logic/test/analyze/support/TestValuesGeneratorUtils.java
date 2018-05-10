package de.elsivas.finance.logic.test.analyze.support;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;

public class TestValuesGeneratorUtils {
	
	private static TestValuesGeneratorUtils instance() {
		return new TestValuesGeneratorUtils();
	}
	
	public static Collection<ShareValuePeriod> generate() {
		ClassLoader classLoader = instance().getClass().getClassLoader();
		File file = new File(classLoader.getResource("test-values.csv").getFile());
		
		final Set<ShareValuePeriod> all = ShareValuePeriodFileDao.instance().loadAll(file.getAbsolutePath());
		
		return all;
	}

}
