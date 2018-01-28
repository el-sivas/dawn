package de.elsivas.model.person;

import de.elsivas.model.BusinessBean;

public interface Person extends BusinessBean {
	
	String TYPE = "Person.TYPE";
	
	String getName();
	
	void setName(final String name);

}
