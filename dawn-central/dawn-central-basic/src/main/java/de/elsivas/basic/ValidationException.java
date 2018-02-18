package de.elsivas.basic;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ValidationException(final String s) {
		super(s);
	}
}
