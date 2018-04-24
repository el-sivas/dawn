package de.elsivas.basic.filedao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class FileDao<T> {

	private static final Log LOG = LogFactory.getLog(FileDao.class);

	public abstract void save(final T t, String filename);

	public abstract T load(String filename);

	public void save(final T config, String filename, Class c) {
		LOG.info("save config to file: '" + filename + "'");
		final Marshaller marshaller = createMarshaller(c);
		try (OutputStream os = new FileOutputStream(new File(filename))) {
			try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
				marshaller.marshal(config, osw);
			} catch (final JAXBException e) {
				throw new RuntimeException(e);
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public T load(String filename, Class c) {
		LOG.info("load config from file: '" + filename + "'");
		final Unmarshaller unmarshaller = createUnmarshaller(c);
		T config;
		try {
			config = (T) unmarshaller.unmarshal(new File(filename));

		} catch (final JAXBException e) {
			throw new RuntimeException(e);
		}
		return config;
	}

	protected Marshaller createMarshaller(Class c) {
		LOG.info("create marshaller for: " + c);
		try {
			return JAXBContext.newInstance(c).createMarshaller();
		} catch (final JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	protected Unmarshaller createUnmarshaller(Class c) {
		LOG.info("create unmarshaller for: " + c);
		try {
			return JAXBContext.newInstance(c).createUnmarshaller();
		} catch (final JAXBException e) {
			throw new RuntimeException(e);
		}
	}

}
