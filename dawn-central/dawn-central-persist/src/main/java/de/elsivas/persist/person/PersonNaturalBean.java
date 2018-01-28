package de.elsivas.persist.person;

public class PersonNaturalBean extends PersonBean {

	@Override
	protected PersonType personType() {
		return PersonType.NATURAL;
	}

}
