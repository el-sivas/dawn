package de.elsivas.persist.person;

import de.elsivas.persist.EntityBean;

public abstract class PersonBean extends EntityBean {
	
	private String name;
	
	private PersonType personType;
	
	public PersonBean() {
		personType = personType();
	}

	abstract protected PersonType personType();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonType getPersonType() {
		return personType;
	}
}
