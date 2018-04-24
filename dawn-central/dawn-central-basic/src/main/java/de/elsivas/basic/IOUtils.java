package de.elsivas.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class IOUtils implements BasicFunction {

	public static String read(File file) throws IOException {
		try (FileReader r = new FileReader(file)) {
			return read(r);
		}
	}

	public static String read(final Reader r) throws IOException {
		final StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(r);) {
			String line = "";
			while (line != null) {
				line = br.readLine();
				if (line == null) {
					continue;
				}
				sb.append(line + "\n");
			}
		}
		return sb.toString();

	}
}
