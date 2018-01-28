package de.elsivas.logic.person;

import de.elsivas.logic.AbstractLogicBean;
import de.elsivas.model.person.Person;
import de.elsivas.persist.person.PersonBean;

public class PersonLogicBean extends AbstractLogicBean<PersonBean> implements Person {

	public PersonLogicBean(final PersonBean bean) {
		super(bean);
	}

	public String getName() {
		return bean.getName();
	}

	public void setName(String name) {
		bean.setName(name);
	}

}
