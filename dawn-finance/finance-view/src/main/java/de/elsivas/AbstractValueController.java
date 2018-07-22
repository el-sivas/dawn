package de.elsivas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractValueController<T> extends AbstractController {

	private static final Log LOG = LogFactory.getLog(AbstractValueController.class);

	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public void open(T value) {
		LOG.info("open: " + value);
		setValue(value);
	}
	
	public void close() {
		LOG.info("open: " + value);
		setValue(null);
	}

}
