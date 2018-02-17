package de.elsivas.basic.filedao;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;

public abstract class FileDao {

	private static final Log LOG = SimpleLogFactory.getLog(FileDao.class);

	protected Marshaller createMarshaller(Class c) {
		LOG.info("create marshaller for: " + c);
		try {
			return JAXBContext.newInstance(c).createMarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	protected Unmarshaller createUnmarshaller(Class c) {
		LOG.info("create unmarshaller for: " + c);
		try {
			return JAXBContext.newInstance(c).createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

}
