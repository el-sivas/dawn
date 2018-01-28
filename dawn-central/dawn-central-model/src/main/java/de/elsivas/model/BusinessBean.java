package de.elsivas.model;

/**
 * Extension of BusinessBean to use with user or external interaction to cover
 * the Implementation.
 */
public interface BusinessBean {

	boolean isValid();

	Long getId();
	
	String getSystemLabel();
}
