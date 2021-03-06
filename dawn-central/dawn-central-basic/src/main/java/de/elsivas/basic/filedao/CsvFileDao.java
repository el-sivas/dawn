package de.elsivas.basic.filedao;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.EsIOUtils;
import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.file.csv.CsvLine;

public class CsvFileDao {

	private static final Log LOG = LogFactory.getLog(CsvFileDao.class);

	private static final String SEMICOLON = ";";

	public static Csv read(final String fileName) {
		return read(new File(fileName));
	}

	public static Csv read(final File file) {
		final List<List<String>> allData = new ArrayList<>();
		final List<String> lines = EsIOUtils.readFileToLines(file);
		lines.forEach(e -> allData.add(Arrays.asList(e.split(SEMICOLON))));

		return Csv.create(allData);
	}

	public static void write(final String fileName, final Csv csv) {
		csv.resetIterator();
		final List<String> lines = new ArrayList<>();

		final List<String> titles = csv.getTitle();
		final StringBuilder sb = new StringBuilder();
		titles.forEach(e -> sb.append(e + SEMICOLON));
		lines.add(sb.toString());

		while (csv.hasNext()) {
			final CsvLine csvLine = csv.next();
			final StringBuilder line = new StringBuilder();
			final List<String> title = csvLine.getTitle();
			for (final String colTitle : title) {
				line.append(csvLine.getValue(colTitle, String.class) + SEMICOLON);
			}
			lines.add(line.toString());
		}
		EsIOUtils.write(fileName, lines);
		LOG.error("lines successful written: " + lines.size());
	}

}
