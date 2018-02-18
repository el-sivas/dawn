package de.elsivas.mail.data;

import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.basic.filedao.FileDao;

public class SimpleMailConfigDao extends FileDao<SimpleMailConfig> {

	private static final Log LOG = SimpleLogFactory.getLog(SimpleMailConfigDaoUtils.class);

	public void save(final SimpleMailConfig config, String filename) {
		super.save(config, filename, SimpleMailConfig.class);
	}

		// LOG.info("save config to file: '" + filename + "'");
		// SimpleMailConfigUtils.print(config);
		// Marshaller marshaller = createMarshaller(SimpleMailConfig.class);
		// try (OutputStream os = new FileOutputStream(new File(filename))) {
		// try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
		// marshaller.marshal(config, osw);
		// } catch (JAXBException e) {
		// throw new RuntimeException(e);
		// }
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }

	public SimpleMailConfig load(String pathname) {
		return super.load(pathname, SimpleMailConfig.class);
	}
	// LOG.info("load config from file: '" + pathname + "'");
	// final Unmarshaller unmarshaller = createUnmarshaller(SimpleMailConfig.class);
	// SimpleMailConfig config;
	// try {
	// config = (SimpleMailConfig) unmarshaller.unmarshal(new File(pathname));
	//
	// } catch (JAXBException e) {
	// throw new RuntimeException(e);
	// }
	// config.setConfigFile(pathname);
	// SimpleMailConfigUtils.print(config);
	// return config;

}
