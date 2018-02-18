package de.elsivas.odd.standalone.taskmanager;

import de.elsivas.basic.filedao.FileDao;

public class TaskFileDao extends FileDao<TMTask> {

	@Override
	protected Class<TMTask> getPersistClass() {
		return TMTask.class;
	}
}
