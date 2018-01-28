package de.elsivas.data.info;

import org.springframework.stereotype.Repository;

import de.elsivas.data.AbstractDaoImpl;
import de.elsivas.data.Dao;

@Repository
public class InfoDaoImpl extends AbstractDaoImpl<InfoBean> implements Dao<InfoBean> {

	@Override
	protected Class<InfoBean> beanClass() {
		return InfoBean.class;
	}

}
