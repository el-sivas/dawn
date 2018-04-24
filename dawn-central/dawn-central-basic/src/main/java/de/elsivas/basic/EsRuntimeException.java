package de.elsivas.basic;

public class EsRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EsRuntimeException(String s) {
		super(s);
	}

	public EsRuntimeException(String s, Throwable t) {
		super(s, t);
	}
}
