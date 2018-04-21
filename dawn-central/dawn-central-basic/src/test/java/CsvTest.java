import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.file.csv.CsvLine;
import de.elsivas.basic.filedao.FileCsvDao;

public class CsvTest {
	
	private static final String BASE_FILE_NAME = "/home/sivas/test/csv-test";
	
	private static final String CSV = ".csv";
	
	@Before
	public void init() {
		//System.getProperties().setProperty("log4j2.debug", "true");
	}

	@Test
	public void test2() {
		final String testFilename = BASE_FILE_NAME + 2 + CSV;

		final String[] title = { "F", "G" };
		final Csv csv = Csv.createEmpty(Arrays.asList(title));
		final String[] lineData = { "1", "2" };
		List<String> data = Arrays.asList(lineData);
		final CsvLine csvLine = CsvLine.create(csv.getTitle(), data);
		csv.add(csvLine);
		FileCsvDao.write(testFilename, csv);

	}

	@Test
	public void test() {
		String[][] source = { { "A", "B" }, { "1.2", "2.3" }, { "1", "3" } };
		final Csv csv = Csv.create(source);
		testCsv(csv);

		final String fileName = BASE_FILE_NAME + CSV;
		FileCsvDao.write(fileName, csv);

		final Csv readCsv = FileCsvDao.read(fileName);
		testCsv(readCsv);
	}

	private void testCsv(final Csv csv) {
		System.out.println(csv);
		csv.resetIterator();
		System.out.println(csv.next().getValue(csv.getTitle().get(1), Double.class));
		System.out.println(csv.next().getValue(csv.getTitle().get(0), BigDecimal.class));
	}

}
