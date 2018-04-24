package de.elsivas.basic;

public class EsLogicException extends Exception {

	private static final long serialVersionUID = 1L;

	public EsLogicException(String s) {
		super(s);
	}

	public EsLogicException(String s, Throwable t) {
		super(s, t);
	}

}
