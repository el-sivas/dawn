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
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.DateUtils;
import de.elsivas.basic.SleepUtils;
import de.elsivas.basic.gui.BasicGui;

public class TaskManager {

	private static final Log LOG = LogFactory.getLog(TaskManager.class);

	private static final String VERSION = "0.4-SN";

	private final TaskFileDao dao = new TaskFileDao();

	private final Map<String, List<String>> resourceBuffers = new HashMap<>();

	private static int pauseSeconds = 0;

	public static void main(final String[] args) throws ParseException {
		LOG.info("Version: " + VERSION);
		final TaskManager tm = new TaskManager();
		try {
			tm.doIt(args);
		} catch (final Throwable t) {
			LOG.error(t.getMessage(), t);
			System.exit(1);
		}
		System.exit(0);
	}

	private void reset(final TMTask task) {
		if (task.isReset()) {
			task.setLastOccurance(null);
			task.setOccurences(0);
		}
		for (final TMTask tmTask : task.getSubTasks()) {
			reset(tmTask);
		}

	}

	private void doIt(final String[] args) {

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

		final double valuePerMinute = value / duration;

		int add;
		if (valuePerMinute > 1) {
			add = -value;
		} else {
			add = value;
		}

		final LocalDateTime plusMinutes = DateUtils.toLocalDateTime(refDate).plusHours((add));
		final Date date = DateUtils.toDate(plusMinutes);

		TaskConditionUtils.setDate(rootTask, ConditionType.NOT_BEFORE_DATE, date);
		showDialog(rootTask + "\n" + TaskConditionUtils.toStringCondition(rootTask));
	}

	private int value(final TMTask task) {
		int i = task.value();
		final Collection<TMTask> subTasks = task.getSubTasks();
		for (final TMTask subtask : subTasks) {
			i += value(subtask);
		}
		return i;
	}

	private double minutes(final String[] args) {
		try {
			return Double.valueOf(args[2]);
		} catch (final Throwable t) {
			LOG.error("error getting minutes", t);
		}
		return 15;
	}

	private boolean fullscreen(final String[] args) {
		try {
			return Boolean.valueOf(args[3]);
		} catch (final Throwable t) {
			LOG.error("error getting fullscreen", t);
		}
		return true;
	}

	private double level(final String[] args) {
		try {
			return Double.valueOf(args[1]);
		} catch (final Throwable t) {
			LOG.error("error getting minutes", t);
		}
		return 1.5;
	}

	private void addBuffers(final TMTask task) {
		final String resource = task.getResource();
		if (!StringUtils.isEmpty(resource)) {
			if (!resourceBuffers.containsKey(resource)) {
				resourceBuffers.put(resource, buffer(resource));
			}
		}
		final Collection<TMTask> subTasks = task.getSubTasks();
		for (final TMTask tmTask : subTasks) {
			addBuffers(tmTask);
		}
	}

	private void executeTask(final TMTask task, final TMGuiConfig config, final double level) {
		final double probability = task.getProbability();
		if (!TaskConditionUtils.isExecutable(task)) {
			LOG.info("not executalbe: " + task);
		} else {
			final int maxOccurences = task.getMaxOccurences();
			final boolean haveToCheck = maxOccurences > 0;
			final boolean nochLuft = task.getOccurences() < maxOccurences;
			if (!haveToCheck || nochLuft) {
				executePropably(task, config, level, probability);
			} else {
				LOG.info(task + " occurences max: " + task.getOccurences());
			}
		}
		final boolean haveToCheck = !task.isSubtaskBeforeExecutionAllowed();
		final boolean occured = task.getLastOccurance() != null;
		if (!haveToCheck || occured) {
			task.getSubTasks().forEach(e -> executeTask(e, config, level));
		}
	}

	private void executePropably(final TMTask task, final TMGuiConfig config, final double level, final Double probability) {
		final double d = Math.random() / level;

		final BigDecimal setScale = BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP);
		final BigDecimal p = BigDecimal.valueOf(probability);

		if (d < probability) {
			execute(task, config);
		} else {
			LOG.info("Not Probable: " + task + "; " + setScale + " : " + p);
		}
	}

	private void execute(final TMTask task, final TMGuiConfig config) {
		final double duration = task.getDuration();
		task.setLastOccurance(new Date());
		task.setOccurences(task.getOccurences() + 1);
		config.setImage(getRandom(task.getResource()));

		if (duration != 0) {
			LOG.info("Task: " + task + " for (s): " + (int) (duration * 60));
			executeTaskInternal(task, config, duration);
		} else {
			LOG.info("Task: " + task + " dialog");
			executeDialog(task, config);
		}
	}

	private void executeDialog(final TMTask task, final TMGuiConfig config) {
		SleepUtils.sleepFor(500);

		final long start = System.currentTimeMillis();
		final String task2 = task.getTask();
		if (task2.contains("\n")) {
			config.setText("");
			showDialog(task2);
		} else {
			config.setText(task2);
			showDialog("Fertig?");
		}
		final long durationInMs = System.currentTimeMillis() - start;
		final int pauseVorher = pauseSeconds;
		pauseSeconds += durationInMs * 2 / 1000;
		LOG.info("pause (seconds): " + pauseVorher + " -> " + pauseSeconds);

	}

	private void showDialog(final String message) {
		LOG.info("Dialog: " + message);
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE);
	}

	private void executeTaskInternal(final TMTask task, final TMGuiConfig config, final Double durationInMinutes) {
		config.setText(task.getTask());
		SleepUtils.sleepFor((int) (durationInMinutes * 60 * 1000));
	}

	private String getRandom(final String bufferKey) {
		if (StringUtils.isEmpty(bufferKey)) {
			return null;
		}
		final List<String> bufferList = resourceBuffers.get(bufferKey);
		final String resource = bufferList.get((int) (Math.random() * bufferList.size()));
		LOG.debug("buffer: " + bufferKey + ", resource: " + resource);
		return resource;
	}

	private String filename(final String[] args) {
		return args[0];
	}

	private TMTask loadOrInit(final String filename) {
		if (!new File(filename).exists()) {
			final TMTask t = TMTask.create();
			dao.save(t, filename);
		}
		return dao.load(filename);
	}

	private List<String> buffer(final String pathname) {
		final File file = new File(pathname);
		final String[] list = file.list();
		final List<String> buffer = new ArrayList<>();
		for (final String filename : list) {
			if (!filename.endsWith(".jpg")) {
				continue;
			}
			buffer.add(pathname + (pathname.endsWith("/") ? "" : "/") + filename);
		}
		return buffer;
	}
}
