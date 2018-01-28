package de.elsivas.logic.person;

import de.elsivas.logic.AbstractBusinessServiceImpl;
import de.elsivas.logic.AbstractLogicBean;
import de.elsivas.logic.CreateVariant;
import de.elsivas.logic.InternalLogicException;
import de.elsivas.model.BusinessService;
import de.elsivas.model.person.Person;
import de.elsivas.persist.Dao;
import de.elsivas.persist.person.PersonBean;
import de.elsivas.persist.person.PersonNaturalBean;
import de.elsivas.persist.person.PersonType;

public class PersonBusinessServiceImpl extends AbstractBusinessServiceImpl<Person, PersonBean>
		implements BusinessService<Person> {

	private Dao<PersonBean> dao;

	@Override
	protected Dao<PersonBean> getDao() {
		return dao;
	}

	@Override
	protected PersonBean createEntityBean(final CreateVariant c) {
		final PersonType personType = c.get(Person.TYPE, PersonType.class);
		switch (personType) {
		case NATURAL:
			return new PersonNaturalBean();
		default:
			throw new InternalLogicException("type not valid: " + personType);
		}
	}

	@Override
	protected Person createBusinessBean(final PersonBean bean) {
		return new PersonLogicBean(bean);
	}

	@Override
	protected Class<? extends AbstractLogicBean<PersonBean>> getLogicBeanClass() {
		return PersonLogicBean.class;
	}

	public String createSystemLabel(final Person bean) {
		return bean.getName();
	}

}
