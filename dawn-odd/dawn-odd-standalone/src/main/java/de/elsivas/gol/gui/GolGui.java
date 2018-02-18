package de.elsivas.gol.gui;

import java.io.File;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GolGui extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(GolGui.class);

	private static GolGuiFrame gui;

	private static Thread thread;

	public static void main(String[] args) {
		if (args.length == 0) {
			throw new RuntimeException("No args");
		}
		doStart(args[0], 1);
	}

	public static void doStart(String p, int chars) {
		doStart(p, chars, new DefaultCellContentFactory());

	}

	public static void doStart(String p, int chars, CellContentFactory cellContentFactory) {
		final boolean random;
		if ("-r".equals(p)) {
			random = true;
		} else {
			random = false;
			if (!isPath(p)) {
				throw new RuntimeException("No path");
			}
		}

		final String path = p;

		final GolParameter g = new GolParameter() {

			@Override
			public boolean isRandom() {
				return random;
			}

			@Override
			public String getWorkdir() {
				return path;
			}

			@Override
			public int chars() {
				return chars;
			}

			@Override
			public CellContentFactory getCellContentFactory() {
				return cellContentFactory;
			}

		};

		start0(g);
	}

	private static boolean isPath(String s) {
		return getPath(s) != null;
	}

	private static File getPath(String s) {
		final File potentialPath = new File(s);
		if (!potentialPath.exists()) {
			throw new RuntimeException("Path not exists: " + s);
		}
		if (!potentialPath.isDirectory()) {
			throw new RuntimeException("No Path:" + s);
		}
		return potentialPath;
	}

	private static void start0(GolParameter g) {
		gui = new GolGuiFrame();
		try {
			gui.init(g);
		} catch (Throwable t) {
			LOG.error("Error while programm execution", t);
		}
		thread = new Thread(gui);
		thread.start();
	}

	public interface GolParameter {

		boolean isRandom();

		String getWorkdir();

		int chars();

		CellContentFactory getCellContentFactory();

	}

}
