package de.elsivas.odd.standalone.taskmanager;

import java.util.Collection;

import org.apache.commons.cli.ParseException;
import org.apache.commons.logging.Log;

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.basic.filedao.FileDao;
import de.elsivas.mail.data.SimpleMailConfig;
import de.elsivas.mail.data.SimpleMailConfigDaoUtils;

public class TaskManager {

	private static final Log LOG = SimpleLogFactory.getLog(TaskManager.class);	

	public static void main(String[] args) throws ParseException {

		TaskFileDao dao = new TaskFileDao();
		TMTask t = new TMTask();
		t.setTask("test");
		Collection<TMTask> subTasks = t.getSubTasks();
		TMTask tmTask2 = new TMTask();
		tmTask2.setTask("2");
		subTasks.add(tmTask2);
		TMTask tmTask3 = new TMTask();
		tmTask3.setTask("3");
		subTasks.add(tmTask3);
		dao.save(t, "/home/sebastian/test/tasks.xml");

	}

}
