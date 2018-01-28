package de.elsivas.data;

public interface Dao<E extends EntityBean> {

	void save(final E bean);

	void delete(final E bean);

	E loadById(final long id);
}
