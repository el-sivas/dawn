package de.elsivas.odd.standalone.taskmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TMTask {
	
	private String task;
	
	private Collection<TMTask> subTasks = new ArrayList<>();

	public void setSubTasks(Collection<TMTask> subTasks) {
		this.subTasks = subTasks;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Collection<TMTask> getSubTasks() {
		return subTasks;
	}
}
