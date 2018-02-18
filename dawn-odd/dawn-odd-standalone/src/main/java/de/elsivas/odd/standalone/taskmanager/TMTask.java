package de.elsivas.odd.standalone.taskmanager;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TMTask implements Task {
	
	private String task;
	
	private Collection<Task> subTasks = new ArrayList<>();

	public void setSubTasks(Collection<Task> subTasks) {
		this.subTasks = subTasks;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Collection<Task> getSubTasks() {
		return subTasks;
	}
}
