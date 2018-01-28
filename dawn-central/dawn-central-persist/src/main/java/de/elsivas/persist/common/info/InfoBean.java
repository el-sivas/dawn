package de.elsivas.persist.common.info;

import de.elsivas.persist.EntityBean;

public class InfoBean extends EntityBean{

	private static final long serialVersionUID = 1l;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
