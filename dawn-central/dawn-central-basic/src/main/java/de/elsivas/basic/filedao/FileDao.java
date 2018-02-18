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

import de.elsivas.basic.SimpleLogFactory;

public abstract class FileDao<T> {

	private static final Log LOG = SimpleLogFactory.getLog(FileDao.class);

	protected abstract Class<T> getPersistClass();

	public void save(final T t, String filename) {
		LOG.info("save to file: '" + t + "'");
		final Marshaller marshaller = createMarshaller(getPersistClass().getClass());
		final File file = new File(filename);
//		if(!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
		try (OutputStream os = new FileOutputStream(file)) {
			try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
				marshaller.marshal(t, osw);
			} catch (JAXBException e) {
				throw new RuntimeException(e);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public T load(String filename) {
		LOG.info("load from file: '" + filename + "'");
		final Unmarshaller unmarshaller = createUnmarshaller(getPersistClass().getClass());
		final T t;
		try {
			t = (T) unmarshaller.unmarshal(new File(filename));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		return t;
	}

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
