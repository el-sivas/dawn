package de.elsivas.mail.data;

import de.elsivas.basic.filedao.FileDao;

public class SimpleMailConfigDao extends FileDao<SimpleMailConfig> {

	@Override
	protected Class<SimpleMailConfig> getPersistClass() {
		return SimpleMailConfig.class;
	}

}
