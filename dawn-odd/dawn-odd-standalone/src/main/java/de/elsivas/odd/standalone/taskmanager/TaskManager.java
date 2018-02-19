package de.elsivas.odd.standalone.taskmanager;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import de.elsivas.basic.DateUtils;
import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.basic.SleepUtils;
import de.elsivas.basic.gui.BasicGui;

public class TaskManager {

	private static final Log LOG = SimpleLogFactory.getLog(TaskManager.class);
	
	private static final String VERSION = "0.3-SN";

	private TaskFileDao dao = new TaskFileDao();

	private Map<String, List<String>> resourceBuffers = new HashMap<>();

	private static int pauseSeconds = 0;

	public static void main(String[] args) throws ParseException {
		LOG.info("Version: " + VERSION);
		TaskManager tm = new TaskManager();
		try {
			tm.doIt(args);
		} catch (Throwable t) {
			LOG.error(t.getMessage(), t);
			System.exit(1);
		}
		System.exit(0);
	}

	private void reset(TMTask task) {
		if (task.isReset()) {
			task.setLastOccurance(null);
			task.setOccurences(0);
		}
		for (TMTask tmTask : task.getSubTasks()) {
			reset(tmTask);
		}

	}

	private void doIt(String[] args) {

		final String filename = filename(args);
		final double level = level(args);
		final double minutes = minutes(args);

		final TMTask rootTask = loadOrInit(filename);
		reset(rootTask);

		LOG.info("Minutes: " + minutes);
		final boolean fullscreen = fullscreen(args);
		addBuffers(rootTask);
		showDialog(rootTask + "\n" + TaskConditionUtils.toStringCondition(rootTask));
		final TMGuiConfig config = TMGuiConfig.getConfig(fullscreen);
		BasicGui.create(config);

		final LocalDateTime start = LocalDateTime.now();
		final LocalDateTime end = LocalDateTime.now().plusSeconds((long) (minutes * 60));
		while (LocalDateTime.now().minusSeconds(pauseSeconds).isBefore(end)) {
			executeTask(rootTask, config, level);
		}
		final int value = value(rootTask);
		LOG.info("Value (root): " + value);

		final double durationInMinutes = DateUtils.minus(LocalDateTime.now(), start) / 60.0;
		if (TaskConditionUtils.hasCondition(rootTask)) {
			setNewCondition(rootTask, value, durationInMinutes);
		}
		showDialog("Took (min): " + durationInMinutes);
		dao.save(rootTask, filename);

	}

	private void setNewCondition(final TMTask rootTask, final int value, final double duration) {
		final Date to = TaskConditionUtils.getDate(rootTask);
		final Date refDate;
		if (LocalDateTime.now().isAfter(DateUtils.toLocalDateTime(to))) {
			refDate = new Date();
		} else {
			refDate = to;
		}
		
		double valuePerMinute = value / duration;
		
		int add;
		if(valuePerMinute > 1) {
			add = -value;
		} else {
			add = value;
		}

		final LocalDateTime plusMinutes = DateUtils.toLocalDateTime(refDate).plusHours((long) (add ));
		final Date date = DateUtils.toDate(plusMinutes);

		TaskConditionUtils.setDate(rootTask, ConditionType.NOT_BEFORE_DATE, date);
		showDialog(rootTask + "\n" + TaskConditionUtils.toStringCondition(rootTask));
	}

	private int value(TMTask task) {
		int i = task.value();
		Collection<TMTask> subTasks = task.getSubTasks();
		for (TMTask subtask : subTasks) {
			i += value(subtask);
		}
		return i;
	}

	private double minutes(String[] args) {
		try {
			return Double.valueOf(args[2]);
		} catch (Throwable t) {
			LOG.error("error getting minutes", t);
		}
		return 15;
	}
	
	private boolean fullscreen(String[] args) {
		try {
			return Boolean.valueOf(args[3]);			
		} catch (Throwable t) {
			LOG.error("error getting fullscreen", t);
		}
		return true;
	}

	private double level(String[] args) {
		try {
			return Double.valueOf(args[1]);
		} catch (Throwable t) {
			LOG.error("error getting minutes", t);
		}
		return 1.5;
	}

	private void addBuffers(TMTask task) {
		final String resource = task.getResource();
		if (!StringUtils.isEmpty(resource)) {
			if (!resourceBuffers.containsKey(resource)) {
				resourceBuffers.put(resource, buffer(resource));
			}
		}
		final Collection<TMTask> subTasks = task.getSubTasks();
		for (TMTask tmTask : subTasks) {
			addBuffers(tmTask);
		}
	}

	private void executeTask(TMTask task, TMGuiConfig config, double level) {
		final Double probability = task.getProbability();
		if (!TaskConditionUtils.isExecutable(task)) {
			LOG.info("not executalbe: " + task);
		} else {

			if (probability != null) {
				int maxOccurences = task.getMaxOccurences();
				final boolean haveToCheck = maxOccurences > 0;
				final boolean nochLuft = task.getOccurences() < maxOccurences;
				if (!haveToCheck || haveToCheck && nochLuft) {
					double d = Math.random() / level;

					final BigDecimal setScale = BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP);
					final BigDecimal p = BigDecimal.valueOf(probability);

					if (d < probability) {
						final Double duration = task.getDuration();
						task.setLastOccurance(new Date());
						task.setOccurences(task.getOccurences() + 1);
						config.setImage(getRandom(task.getResource()));

						if (duration != null) {
							LOG.info("Task: " + task + " for (s): " + (int) (duration * 60));
							executeTaskInternal(task, config, duration);
						} else {
							LOG.info("Task: " + task + " dialog");
							executeDialog(task, config);
						}
					} else {
						LOG.info("Not Probable: " + task + "; " + setScale + " : " + p);
					}
				} else {
					LOG.info(task + " occurences max: " + task.getOccurences());
				}
			}
		}
		boolean haveToCheck = !task.isSubtaskBeforeExecutionAllowed();
		boolean occured = task.getLastOccurance() != null;
		if (haveToCheck && occured || !haveToCheck) {
			for (TMTask subtasks : task.getSubTasks()) {
				executeTask(subtasks, config, level);
			}
		}
	}

	private void executeDialog(TMTask task, TMGuiConfig config) {
		SleepUtils.sleepFor(500);

		long start = System.currentTimeMillis();
		final String task2 = task.getTask();
		if (task2.contains("\n")) {
			config.setText("");
			showDialog(task2);
		} else {
			config.setText(task2);
			showDialog("Fertig?");
		}
		long durationInMs = System.currentTimeMillis() - start;
		int pauseVorher = pauseSeconds;
		pauseSeconds += durationInMs * 2 / 1000;
		LOG.info("pause (seconds): " + pauseVorher + " -> " + pauseSeconds);

	}

	private void showDialog(String message) {
		LOG.info("Dialog: " + message);
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE);
	}

	private void executeTaskInternal(TMTask task, TMGuiConfig config, final Double durationInMinutes) {
		config.setText(task.getTask());
		SleepUtils.sleepFor((int) (durationInMinutes * 60 * 1000));
	}

	private String getRandom(String bufferKey) {
		if (StringUtils.isEmpty(bufferKey)) {
			return null;
		}
		final List<String> bufferList = resourceBuffers.get(bufferKey);
		final String resource = bufferList.get((int) (Math.random() * bufferList.size()));
		LOG.debug("buffer: " + bufferKey + ", resource: " + resource);
		return resource;
	}

	private String filename(String[] args) {
		return args[0];
	}

	private TMTask loadOrInit(String filename) {
		if (!new File(filename).exists()) {
			TMTask t = TMTask.create();
			dao.save(t, filename);
		}
		return dao.load(filename);
	}

	private List<String> buffer(String pathname) {
		final File file = new File(pathname);
		String[] list = file.list();
		final List<String> buffer = new ArrayList<>();
		for (String filename : list) {
			if (!filename.endsWith(".jpg")) {
				continue;
			}
			buffer.add(pathname + (pathname.endsWith("/") ? "" : "/") + filename);
		}
		return buffer;
	}
}
