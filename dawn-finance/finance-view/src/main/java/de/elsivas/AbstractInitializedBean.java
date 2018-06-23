package de.elsivas;

import de.elsivas.basic.EsRuntimeException;

public abstract class AbstractInitializedBean {

	public AbstractInitializedBean() {
		try {
			init();
		} catch (Exception e) {
			throw new EsRuntimeException("error post construct", e);
		}
	}

	protected abstract void init();

}
