package finance.deprecated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.elsivas.basic.EsIOUtils;

public class EsFinanceReaderUtils {

	private static final String CR = "\n";
	private static final String DEFAULT_SDF = "yyyy-MM-dd";

	public static Map<Date, Double> readHistoryFile(final String filename) {
		final String fileContent = EsIOUtils.readFile(preFormat(filename));
		final List<String> lines = Arrays.asList(preFormat(fileContent).split(CR));
		final Map<Date, Double> map = new HashMap<>();

		int i = 0;
		for (String line : lines) {
			i++;
			if(i == 1) {
				continue;
			}
			final Data data = toData(preFormat(line));
			map.put(data.getDate(), data.getValue());
		}

		return map;
	}

	private static Data toData(String line) {
		final String[] split = preFormat(line).split(";");
		Date parse;
		try {
			parse = new SimpleDateFormat(DEFAULT_SDF).parse(split[0]);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return Data.create(Double.valueOf(preFormat(split[1])), parse);
	}

	private static String preFormat(final String string) {
		return string.replace(",", ".");
	}
}
