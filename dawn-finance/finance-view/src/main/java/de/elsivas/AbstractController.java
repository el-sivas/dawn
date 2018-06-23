package de.elsivas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.EsRuntimeException;


@Deprecated
public abstract class AbstractController extends AbstractInitializedBean {

	private static final Log LOG = LogFactory.getLog(AbstractController.class);

	@Override
	protected void init() {
		try {
			postConstruct();
		} catch (Exception e) {
			throw new EsRuntimeException("error init '" + getClass().getName() + "'", e);
		}
	}

	protected void postConstruct() throws Exception {
		// implement if needet
	}

	public void save() {
		// implement if needet
		LOG.warn("save not implemented.");
	}

}
