package de.elsivas.odd.standalone.taskmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TMTask {

	private static final Log LOG = LogFactory.getLog(TMTask.class);

	private String task;

	private Double probability;

	private Date lastOccurance;

	private String resource;

	private Double duration;

	private Boolean subtasksBeforeExecution;

	private Boolean reset;

	private Integer maxOccurences;

	private Integer occurences;

	private String resourceAudio;

	private Collection<TMTask> subTasks = new ArrayList<>();

	private Set<TMTaskProperties> properties = new TreeSet<>(new Comparator<TMTaskProperties>() {

		@Override
		public int compare(final TMTaskProperties o1, final TMTaskProperties o2) {
			return o1.getKey().compareTo(o2.getKey());
		}
	});

	public static TMTask create(final String task) {
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
		return "'" + getTask().split("\n")[0] + "'";
	}

	public void setSubTasks(final Collection<TMTask> subTasks) {
		this.subTasks = subTasks;
	}

	public String getTask() {
		final String replaceAll2 = task.replaceAll("\n", " ");
		final String replaceAll = replaceAll2.replaceAll("<br>", "\n");
		final String[] split = replaceAll.split(";");
		final List<String> asList = Arrays.asList(split);
		return asList.get((int) (Math.random() * asList.size()));

	}

	public void setTask(final String task) {
		this.task = task;
	}

	public Collection<TMTask> getSubTasks() {
		return subTasks;
	}

	public double getProbability() {
		return probability != null ? probability : 0;
	}

	public void setProbability(final Double probability) {
		this.probability = probability;
	}

	public Date getLastOccurance() {
		return lastOccurance;
	}

	public void setLastOccurance(final Date lastOccurance) {
		this.lastOccurance = lastOccurance;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(final String resource) {
		this.resource = resource;
	}

	public double getDuration() {
		return duration != null ? duration : 0;
	}

	public void setDuration(final Double duration) {
		this.duration = duration;
	}

	public boolean isSubtaskBeforeExecutionAllowed() {
		return isSubtasksBeforeExecution();
	}

	public boolean isSubtasksBeforeExecution() {
		return BooleanUtils.isTrue(subtasksBeforeExecution);
	}

	public void setSubtasksBeforeExecution(final Boolean subtasksBeforeExecution) {
		this.subtasksBeforeExecution = subtasksBeforeExecution;
	}

	public boolean isReset() {
		return BooleanUtils.isNotFalse(reset);
	}

	public void setReset(final Boolean reset) {
		this.reset = reset;
	}

	public boolean isExecuted() {
		return lastOccurance != null;
	}

	public int getMaxOccurences() {
		return maxOccurences != null ? maxOccurences : 0;
	}

	public void setMaxOccurences(final Integer maxOccurences) {
		this.maxOccurences = maxOccurences;
	}

	public int getOccurences() {
		return occurences != null ? occurences : 0;
	}

	public void setOccurences(final Integer occurences) {
		this.occurences = occurences;
	}

	public Set<TMTaskProperties> getProperties() {
		return properties;
	}

	public void setProperties(final Set<TMTaskProperties> properties) {
		this.properties = properties;
	}

	public void setProperty(final String key, final String value) {
		properties.remove(TMTaskProperties.create(key, ""));
		properties.add(TMTaskProperties.create(key, value));
	}

	public String getPropertiy(final String key) {
		final Map<String, String> map = new HashMap<>();
		for (final TMTaskProperties p : properties) {
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

	public String getResourceAudio() {
		return resourceAudio;
	}

	public void setResourceAudio(final String resourceAudio) {
		this.resourceAudio = resourceAudio;
	}
}
