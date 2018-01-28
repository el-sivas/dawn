package de.elsivas.model;

/**
 * The extenxion of {@link BusinessService} to cover logic for related
 * BusinessBean.
 */
public interface BusinessService<B extends BusinessBean> {

	void save(final B bean);

	B create();
	
	void delete(final B bean);
	
	String createSystemLabel(final B bean);

}
