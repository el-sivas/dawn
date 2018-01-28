package de.elsivas.persist.person;

import de.elsivas.persist.AbstractDaoImpl;
import de.elsivas.persist.Dao;

public class PersonDaoImpl extends AbstractDaoImpl<PersonBean> implements Dao<PersonBean> {

	@Override
	protected Class<PersonBean> beanClass() {
		return PersonBean.class;
	}

}
