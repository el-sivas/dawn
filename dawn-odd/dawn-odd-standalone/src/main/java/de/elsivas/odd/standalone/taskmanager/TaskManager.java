package de.elsivas.odd.standalone.taskmanager;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import de.elsivas.basic.SimpleLogFactory;
import de.elsivas.basic.SleepUtils;
import de.elsivas.basic.gui.BasicGui;

public class TaskManager {

	private static final Log LOG = SimpleLogFactory.getLog(TaskManager.class);

	private TaskFileDao dao = new TaskFileDao();

	private Map<String, List<String>> resourceBuffers = new HashMap<>();

	public static void main(String[] args) throws ParseException {
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
		final int minutes = minutes(args);

		final TMTask rootTask = loadOrInit(filename);
		reset(rootTask);
		
		LOG.info("Minutes: " + minutes);

		addBuffers(rootTask);

		final TMGuiConfig config = TMGuiConfig.getConfig();
		BasicGui.create(config);

		final LocalDateTime end = LocalDateTime.now().plusMinutes(minutes);
		while (LocalDateTime.now().isBefore(end)) {
			executeTask(rootTask, config, level);
		}
		LOG.info("Value Root: " + value(rootTask));

		dao.save(rootTask, filename);

	}

	private int value(TMTask task) {
		int i = task.value();
		Collection<TMTask> subTasks = task.getSubTasks();
		for (TMTask subtask : subTasks) {
			i += subtask.value();
		}
		return i;
	}

	private int minutes(String[] args) {
		return Integer.valueOf(args[2]);
	}

	private double level(String[] args) {
		return Double.valueOf(args[1]);
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
		if (probability != null) {
			int maxOccurences = task.getMaxOccurences();
			final boolean haveToCheck = maxOccurences > 0;
			final boolean nochLuft = task.getOccurences() < maxOccurences;
			if (!haveToCheck || haveToCheck && nochLuft) {
				double d = Math.random() / level;

				BigDecimal setScale = BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP);
				BigDecimal p = BigDecimal.valueOf(probability);

				LOG.info(setScale + " : " + p);
				if (d < probability) {
					LOG.info("Task: " + task);
					final Double duration = task.getDuration();
					task.setLastOccurance(new Date());
					task.setOccurences(task.getOccurences() + 1);
					config.setImage(getRandom(task.getResource()));
					if (duration != null) {
						executeTaskInternal(task, config, duration);
					} else {
						executeDialog(task, config);
					}
				} else {
					LOG.info("Not Probable: " + task);
				}
			} else {
				LOG.info(task + " occurences max: " + task.getOccurences());
			}
		}
		boolean haveToCheck = !task.isSubtasksBeforeExecution();
		boolean occured = task.getLastOccurance() != null;
		if (haveToCheck && occured || !haveToCheck) {
			for (TMTask subtasks : task.getSubTasks()) {
				executeTask(subtasks, config, level);
			}
		}
	}

	private void executeDialog(TMTask task, TMGuiConfig config) {
		config.setText(StringUtils.EMPTY);
		JOptionPane.showMessageDialog(null, task.getTask(), "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
	}

	private void executeTaskInternal(TMTask task, TMGuiConfig config, final Double durationInMinutes) {
		int durationInSeconds = (int) (durationInMinutes * 60);
		LOG.info("...for (s): " + durationInSeconds);
		config.setText(task.getTask());
		SleepUtils.sleepFor((int) (durationInSeconds * 1000));
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
