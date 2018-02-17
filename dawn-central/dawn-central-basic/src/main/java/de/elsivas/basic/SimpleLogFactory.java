package de.elsivas.basic;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.apache.logging.log4j.CloseableThreadContext.Instance;

public class SimpleLogFactory {

	private static Log LOG = getLog(SimpleLogFactory.class);

	private static SimpleLogFactory instance;
	
	private SimpleLogFactory() {
		LOG = new SimpleLog(SimpleLogFactory.class.getName());
	}

	public static Log getLog(final Class c) {
		init();
		if (BooleanUtils
				.isFalse(Boolean.valueOf(System.getProperty("org.apache.commons.logging.simplelog.showdatetime")))) {
			LOG.warn("Set system property:" + "org.apache.commons.logging.simplelog.showdatetime" + "=true");
		}
		return new SimpleLog(c.getName());
	}

	private static void init() {
		if (instance == null) {
			instance = new SimpleLogFactory();
		}

	}

}
