package de.elsivas.logic;

import de.elsivas.model.BusinessBean;
import de.elsivas.persist.Dao;
import de.elsivas.persist.EntityBean;

public abstract class AbstractBusinessServiceImpl<B extends BusinessBean, E extends EntityBean> {

	abstract protected E createEntityBean(final CreateVariant c);

	abstract protected B createBusinessBean(final E bean);

	abstract protected Dao<E> getDao();

	abstract protected Class<? extends AbstractLogicBean<E>> getLogicBeanClass();
	
	abstract protected String createSystemLabel(B businessBean);

	public B create(final CreateVariant c) {
		if (c == null) {
			return create();
		}
		final E bean = createEntityBean(c);
		getDao().save(bean);
		return createBusinessBean(bean);
	}

	public B create() {
		return create(CreateVariant.instance());
	}

	protected AbstractLogicBean<E> logicBean(final B businessBean) {
		return getLogicBeanClass().cast(businessBean);
	}

	protected E entityBean(final B businessBean) {
		return logicBean(businessBean).getEntityBean();
	}

	public void save(final B businessBean) {
		final E entityBean = entityBean(businessBean);
		entityBean.setSystemLabel(createSystemLabel(businessBean));
		getDao().save(entityBean);
	}

	public void delete(final B businessBean) {
		getDao().delete(entityBean(businessBean));
	}

}
