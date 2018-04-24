package de.elsivas.mail.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleMailConfigDaoUtils {

	private static final Log LOG = LogFactory.getLog(SimpleMailConfigDaoUtils.class);

	private static SimpleMailConfigDaoUtils instance;
	
	private static SimpleMailConfigDao dao;

	private static SimpleMailConfigDao getInstance() {
		if (dao == null) {
			dao = new SimpleMailConfigDao();
		}
		return dao;
	}

	public static void save(final SimpleMailConfig config, final String path) {
		getInstance().save(config, path);
	}

	public static SimpleMailConfig load(final String pathname) {
		return getInstance().load(pathname);
	}	
}
