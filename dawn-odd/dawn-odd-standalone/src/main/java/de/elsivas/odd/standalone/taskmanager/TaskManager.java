package de.elsivas.odd.standalone.taskmanager;

import java.io.File;

import org.apache.commons.cli.ParseException;
import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.basic.filedao.FileDao;

public class TaskManager {
	
	private static final Log LOG = SimpleLogFactory.getLog(TaskManager.class);
	
	private FileDao<TMTask> dao;
	
	public static void main(String[] args) throws ParseException {
		TaskManager tm = new TaskManager();
		tm.doIt(args);
	}	
	

	private void doIt(String[] args) {
		init(filename(args));
		
	}



	private String filename(String[] args) {
		return args[0];
	}


	private void init(String filename) {
		dao = new TaskFileDao();
		if(!new File(filename).exists()) {
			final TMTask t = new TMTask();
			t.setTask("root");
			dao.save(t, filename);
		}
		final TMTask load = dao.load(filename);
		load.setTask("root2");
		dao.save(load, filename);
	}

}
