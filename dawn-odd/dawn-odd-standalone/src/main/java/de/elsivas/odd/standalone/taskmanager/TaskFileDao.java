package de.elsivas.odd.standalone.taskmanager;

import de.elsivas.basic.filedao.FileDao;

public class TaskFileDao extends FileDao<TMTask> {

	@Override
	public void save(final TMTask t, final String filename) {
		super.save(t, filename, TMTask.class);
		
	}

	@Override
	public TMTask load(final String filename) {
		return super.load(filename, TMTask.class);
	}
}
