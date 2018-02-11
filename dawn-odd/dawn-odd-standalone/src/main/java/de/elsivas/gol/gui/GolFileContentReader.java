package de.elsivas.gol.gui;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GolFileContentReader {

	public static CellContent[][] content(String path) {
		return content(path, 1);
	}

	public static CellContent[][] content(String path, int chars) {
		File f = new File(path);
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".gol");
			}
		};
		File[] listFiles = f.listFiles(filter);
		List<File> asList = Arrays.asList(listFiles);
		Collections.sort(asList, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				return Long.valueOf(o1.lastModified()).compareTo(o2.lastModified());
			}
		});

		Collections.reverse(asList);

		try {
			return content(asList.get(0), chars);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String[] split(String line, int chars) {
		int length = line.length();
		if (length % chars != 0) {
			throw new RuntimeException("not conclude");
		}

		List<String> splitted = new ArrayList<>();
		int idx = 0;
		while (idx < length) {
			splitted.add(line.substring(idx, idx + chars));
			idx += chars;
		}
		return splitted.toArray(new String[0]);

	}

	private static CellContent[][] content(File file, int chars) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<List<CellContent>> l = new ArrayList<>();
		while (true) {
			String readLine = br.readLine();
			if (readLine == null) {
				break;
			}
			List<CellContent> cl = new ArrayList<>();
			String[] strings = split(readLine, chars);
			for (String c : strings) {
				cl.add(createContent(c));
			}
			l.add(cl);
		}

		int size = l.size();
		final CellContent[][] cc = new CellContent[size][size];
		int i = 0;
		for (List<CellContent> list : l) {
			CellContent[] c = new CellContent[size];
			int j = 0;
			for (CellContent cellContent : list) {
				c[j++] = cellContent;
			}
			cc[i++] = c;
		}

		return cc;
	}

	private static CellContent createContent(String s) {
		CellContent e = new CellContent() {

			@Override
			public boolean isContented() {
				return "1".equals(s);
			}

			@Override
			public int getContentSize() {
				return 1;
			}

			@Override
			public String toString() {
				return isContented() ? "1" : "0";
			}

			@Override
			public void format(Component c) {
				c.setBackground(isContented() ? Color.green : Color.WHITE);
			}
		};
		return e;
	}

}
