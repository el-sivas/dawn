package de.elsivas.mail.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.basic.filedao.FileDao;
import de.elsivas.mail.logic.SimpleMailConfigUtils;

public class SimpleMailConfigDaoUtils extends FileDao {

	private static final Log LOG = SimpleLogFactory.getLog(SimpleMailConfigDaoUtils.class);

	private static SimpleMailConfigDaoUtils instance;

	private static SimpleMailConfigDaoUtils getInstance() {
		if (instance == null) {
			instance = new SimpleMailConfigDaoUtils();
		}
		return instance;
	}

	public static void save(final SimpleMailConfig config) {
		getInstance().saveInternal(config);
	}

	public static SimpleMailConfig load(String pathname) {
		return getInstance().loadInternal(pathname);
	}

	private void saveInternal(final SimpleMailConfig config) {
		final String configFile = config.getConfigFile();
		LOG.info("save config to file: '" + configFile + "'");
		SimpleMailConfigUtils.print(config);
		Marshaller marshaller = createMarshaller(SimpleMailConfig.class);
		try (OutputStream os = new FileOutputStream(new File(configFile))) {
			try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
				marshaller.marshal(config, osw);
			} catch (JAXBException e) {
				throw new RuntimeException(e);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private SimpleMailConfig loadInternal(String pathname) {
		LOG.info("load config from file: '" + pathname + "'");
		final Unmarshaller unmarshaller = createUnmarshaller(SimpleMailConfig.class);
		SimpleMailConfig config;
		try {
			config = (SimpleMailConfig) unmarshaller.unmarshal(new File(pathname));

		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		config.setConfigFile(pathname);
		SimpleMailConfigUtils.print(config);
		return config;
	}
}
