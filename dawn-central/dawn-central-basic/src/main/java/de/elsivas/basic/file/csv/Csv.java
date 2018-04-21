package de.elsivas.basic.file.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.elsivas.basic.EsRuntimeException;

public class Csv {

	private List<String> title = new ArrayList<String>();

	private List<List<String>> data = new ArrayList<>();

	private Iterator<CsvLine> iterator;

	private Csv(final List<String> title) {
		this(title, new ArrayList<>());
	}

	private Csv(final List<String> title, final List<List<String>> data) {
		this.title.addAll(title);
		this.data.addAll(data);
	}

	public static Csv createEmpty(final List<String> title) {
		return new Csv(title);
	}

	public static Csv create(final List<List<String>> source) {
		final List<String> asList = source.get(0);
		final Set<String> set = new TreeSet<>();
		set.addAll(asList);
		if (asList.size() != set.size()) {
			throw new EsRuntimeException("title not unique: " + asList);
		}

		final List<List<String>> data2 = new ArrayList<>();
		for (int i = 1; i < source.size(); i++) {
			data2.add(new ArrayList<>(source.get(i)));
		}

		return new Csv(asList, data2);
	}

	public static Csv create(final String[][] source) {
		final List<List<String>> all = new ArrayList<>();
		for (int y = 0; y < source.length; y++) {
			all.add(Arrays.asList(source[y]));
		}
		return create(all);
	}

	public List<String> getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return title + " " + data;
	}

	public void resetIterator() {
		final List<CsvLine> lines = new ArrayList<>();
		data.forEach(e -> lines.add(CsvLine.create(title, e)));
		this.iterator = lines.iterator();
	}

	public CsvLine next() {
		if (iterator == null || !iterator.hasNext()) {
			return null;
		}
		return iterator.next();
	}

	public boolean hasNext() {
		if (iterator == null) {
			resetIterator();
		}
		return iterator.hasNext();
	}

	public void add(CsvLine line) {
		final List<String> lineTitle = line.getTitle();
		for (String colTitle : lineTitle) {
			if (!this.title.contains(colTitle)) {
				throw new EsRuntimeException("line title not in csv: " + colTitle);
			}
		}
		final List<String> newLine = new ArrayList<>(title.size());
		lineTitle.forEach(e -> newLine.add(title.indexOf(e), line.getValue(e)));
		data.add(newLine);
	}
}
