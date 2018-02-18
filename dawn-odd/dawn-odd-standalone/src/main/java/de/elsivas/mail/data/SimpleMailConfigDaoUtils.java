package de.elsivas.mail.data;

import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;

public class SimpleMailConfigDaoUtils {

	private static final Log LOG = SimpleLogFactory.getLog(SimpleMailConfigDaoUtils.class);

	private static SimpleMailConfigDaoUtils instance;
	
	private static SimpleMailConfigDao dao;

	private static SimpleMailConfigDao getInstance() {
		if (dao == null) {
			dao = new SimpleMailConfigDao();
		}
		return dao;
	}

	public static void save(final SimpleMailConfig config) {
		getInstance().save(config, config.getConfigFile());
	}

	public static SimpleMailConfig load(String pathname) {
		return getInstance().load(pathname);
	}	
}
