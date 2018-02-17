package de.elsivas.mail.logic;

public class SMRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;	
	
	public SMRuntimeException(SMLogicException e) {
		super(e);
	}
	

}
