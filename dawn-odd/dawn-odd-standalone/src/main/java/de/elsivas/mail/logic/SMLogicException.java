package de.elsivas.mail.logic;

public class SMLogicException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SMLogicException(Exception e) {
		super(e);	
	}
	
	public SMLogicException(String s) {
		super(s);	
	}

}
