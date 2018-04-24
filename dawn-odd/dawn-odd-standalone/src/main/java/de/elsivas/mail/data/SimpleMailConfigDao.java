package de.elsivas.mail.data;

import de.elsivas.basic.filedao.FileDao;

public class SimpleMailConfigDao extends FileDao<SimpleMailConfig> {

	@Override
	public void save(final SimpleMailConfig t, final String filename) {
		super.save(t, filename, t.getClass());		
	}

	@Override
	public SimpleMailConfig load(final String filename) {
		return super.load(filename, SimpleMailConfig.class);
	}
}
