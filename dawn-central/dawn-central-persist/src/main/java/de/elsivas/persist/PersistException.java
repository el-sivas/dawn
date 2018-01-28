package de.elsivas.persist;

public class PersistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersistException(final String s) {
		super(s);
	}

	public PersistException(final String s, final Throwable t) {
		super(s, t);
	}

}
