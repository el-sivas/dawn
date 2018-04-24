package de.elsivas.mail.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.mail.data.SimpleMailConfig;
import de.elsivas.mail.data.SimpleMailConfigDaoUtils;

public class SimpleMailConfigUtils {

	private static final Log LOG = LogFactory.getLog(SimpleMailConfigUtils.class);

	private static final String REPLACE_ME = "REPLACE_ME";

	public static void init(final String filename) {
		final SimpleMailConfig config = new SimpleMailConfig();
		config.addReciepient(REPLACE_ME);

		config.setImapHost(REPLACE_ME);
		config.setUser(REPLACE_ME);
		config.setPassword(REPLACE_ME);
		config.setSmtpHost(REPLACE_ME);

		SimpleMailConfigDaoUtils.save(config, filename);
	}

	public static void print(final SimpleMailConfig config) {
		LOG.info("Current config: \n" + config.toString().replace(",", "\n"));
	}

}
