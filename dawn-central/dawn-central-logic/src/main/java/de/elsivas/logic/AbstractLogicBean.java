package de.elsivas.logic;

import de.elsivas.data.EntityBean;
import de.elsivas.model.BusinessBean;

public abstract class AbstractLogicBean<E extends EntityBean> implements BusinessBean {
	
	protected final E bean;

	public AbstractLogicBean(E bean) {
		super();
		this.bean = bean;
	}

	public E getEntityBean() {
		return bean;
	}
	
	public boolean isValid() {
		return getEntityBean().isValid();
	}


	public Long getId() {
		return getEntityBean().getId();
	}
	
	@Override
	public String getSystemLabel() {
		return bean.getSystemLabel();
	}
	
}
