package de.elsivas.odd.standalone.taskmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.logging.Log;

import com.mysql.cj.core.log.LogFactory;

import de.elsivas.basic.SimpleLogFactory;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TMTask {

	private static final Log LOG = SimpleLogFactory.getLog(TMTask.class);

	private String task;

	private Double probability;

	private Date lastOccurance;

	private String resource;

	private Double duration;

	private Boolean subtasksBeforeExecution;

	private Boolean reset;

	private Integer maxOccurences;

	private Integer occurences;

	private Collection<TMTask> subTasks = new ArrayList<>();

	private Set<TMTaskProperties> properties = new TreeSet<>(new Comparator<TMTaskProperties>() {

		@Override
		public int compare(TMTaskProperties o1, TMTaskProperties o2) {
			return o1.getKey().compareTo(o2.getKey());
		}
	});

	public static TMTask create(String task) {
		final TMTask tmTask = create();
		tmTask.setTask(task);
		return tmTask;
	}

	public static TMTask create() {
		final TMTask tmTask = new TMTask();
		tmTask.setProbability(1.0);
		return tmTask;
	}

	@Override
	public String toString() {
		return "'" + task + "'";
	}

	public void setSubTasks(Collection<TMTask> subTasks) {
		this.subTasks = subTasks;
	}

	public String getTask() {
		return task.replaceAll("<br>", "\n");
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Collection<TMTask> getSubTasks() {
		return subTasks;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	public Date getLastOccurance() {
		return lastOccurance;
	}

	public void setLastOccurance(Date lastOccurance) {
		this.lastOccurance = lastOccurance;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public boolean isSubtasksBeforeExecution() {
		return BooleanUtils.isTrue(subtasksBeforeExecution);
	}

	public void setSubtasksBeforeExecution(Boolean subtasksBeforeExecution) {
		this.subtasksBeforeExecution = subtasksBeforeExecution;
	}

	public boolean isReset() {
		return BooleanUtils.isNotFalse(reset);
	}

	public void setReset(Boolean reset) {
		this.reset = reset;
	}

	public boolean isExecuted() {
		return lastOccurance != null;
	}

	public int getMaxOccurences() {
		return maxOccurences != null ? maxOccurences : 0;
	}

	public void setMaxOccurences(Integer maxOccurences) {
		this.maxOccurences = maxOccurences;
	}

	public int getOccurences() {
		return occurences != null ? occurences : 0;
	}

	public void setOccurences(Integer occurences) {
		this.occurences = occurences;
	}

	public Set<TMTaskProperties> getProperties() {
		return properties;
	}

	public void setProperties(Set<TMTaskProperties> properties) {
		this.properties = properties;
	}

	public void setProperty(String key, String value) {
		properties.add(TMTaskProperties.create(key, value));
	}

	public String getPropertiy(String key) {
		final Map<String, String> map = new HashMap<>();
		for (TMTaskProperties p : properties) {
			map.put(p.getKey(), p.getValue());
		}
		return map.get(key);
	}

	public int value() {
		int value = 0;
		if (getMaxOccurences() > 0) {
			value += getOccurences();
		} else {
			value += getMaxOccurences() / 10;
		}
		LOG.info("value '" + toString() + "': " + value);
		return value;
	}
}
