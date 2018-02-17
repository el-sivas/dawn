package de.elsivas.mail.logic;

import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.mail.data.SimpleMailConfig;
import de.elsivas.mail.data.SimpleMailConfigDaoUtils;

public class SimpleMailConfigUtils {

	private static final Log LOG = SimpleLogFactory.getLog(SimpleMailConfigUtils.class);

	private static final String REPLACE_ME = "REPLACE_ME";

	public static void init(String filename) {
		final SimpleMailConfig config = new SimpleMailConfig();
		config.setConfigFile(filename);
		setDefaults(config);

		config.setImapHost(REPLACE_ME);
		config.setUser(REPLACE_ME);
		config.setPassword(REPLACE_ME);
		config.setSmtpHost(REPLACE_ME);

		SimpleMailConfigDaoUtils.save(config);
	}

	public static void setDefaults(SimpleMailConfig config) {
		config.addReciepient("mail@sebastianlipp.de");
		config.addReciepient("wehrfuehrer-oberweidbach@bischoffen.de");
	}

	public static void print(SimpleMailConfig config) {
		LOG.info("Current config: \n" + config.toString().replace(",", "\n"));
	}

}
