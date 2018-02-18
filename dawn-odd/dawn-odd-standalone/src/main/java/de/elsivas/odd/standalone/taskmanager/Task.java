package de.elsivas.odd.standalone.taskmanager;

import java.util.Collection;

public interface Task {
	
	String getTask();
	
	Collection<Task> getSubTasks();

}
