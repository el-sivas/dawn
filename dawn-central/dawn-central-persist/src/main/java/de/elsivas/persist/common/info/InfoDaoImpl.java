package de.elsivas.persist.common.info;

import org.springframework.stereotype.Repository;

import de.elsivas.persist.AbstractDaoImpl;
import de.elsivas.persist.Dao;

@Repository
public class InfoDaoImpl extends AbstractDaoImpl<InfoBean> implements Dao<InfoBean> {

	@Override
	protected Class<InfoBean> beanClass() {
		return InfoBean.class;
	}

}
