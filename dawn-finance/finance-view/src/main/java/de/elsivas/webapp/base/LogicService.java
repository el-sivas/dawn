package de.elsivas.webapp.base;

import java.util.Collection;

import de.elsivas.finance.data.model.ShareValuePeriod;

public interface LogicService<T> {
	
	Collection<T> findAll();
	
	void save(T t);

	Collection<ShareValuePeriod> findAll(String workdir);
}
