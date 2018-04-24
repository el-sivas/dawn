package de.elsivas.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EsIOUtils {

	private static final String CR = "\n";

	public static String readFile(String filename) {
		final StringBuilder sb = new StringBuilder();
		readFileToLines(filename).forEach(e -> sb.append(e + CR));
		return sb.toString();
	}

	public static List<String> readFileToLines(String filename) {
		final File file = new File(filename);
		final List<String> lines = new ArrayList<>();
		try (FileReader fr = new FileReader(file)) {
			final BufferedReader br = new BufferedReader(fr);
			String line = "";
			while (line != null) {
				line = br.readLine();
				if (line == null) {
					continue;
				}
				lines.add(line);

			}
		} catch (IOException e) {
			throw new EsRuntimeException("error on read file", e);
		}
		return lines;
	}

	public static void write(String fileName, List<String> lines) {
		final File file = new File(fileName);
		try (FileWriter fw = new FileWriter(file)) {
			try (final BufferedWriter bw = new BufferedWriter(fw)) {
				for (String string : lines) {
					bw.write(string);
					bw.newLine();
				}
			}
		} catch (IOException e) {
			throw new EsRuntimeException("error on write file", e);
		}
	}

}
