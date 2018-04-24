package de.elsivas.finance.logic.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import de.elsivas.basic.DateUtils;
import de.elsivas.basic.file.csv.Csv;
import de.elsivas.basic.filedao.FileCsvDao;
import de.elsivas.finance.data.FinChartData;
import de.elsivas.finance.data.ChartDataType;

public class ChartDataTest {

	public static final String HOME_SIVAS_TEST_FINDATA_CSV = "/home/sivas/test/findata.csv";

	@Test
	public void generateRandomData() {
		final FinChartData cd = FinChartData.create();

		final int zeitraum = 365;
		final double maxSchwankungAmTag = 0.05;
		
		final LocalDate startDate = LocalDate.now().minusDays(zeitraum);
		
		final int start = 1000;
		double current = start;
		for (int i = 0; i < zeitraum; i++) {
			final Date date = DateUtils.toDate(startDate.plusDays(i));
			final BigDecimal price = BigDecimal.valueOf(current);
			cd.add(date, price);

			final double multiplicant = 1 + (Math.random() * maxSchwankungAmTag * (Math.random() > 0.5 ? 1 : -1));
			current = current * multiplicant;
		}
		FileCsvDao.write(HOME_SIVAS_TEST_FINDATA_CSV, cd.getRawData());
	}

	@Test
	public void test2() {
		final FinChartData cd = FinChartData.create(FileCsvDao.read(HOME_SIVAS_TEST_FINDATA_CSV));
		cd.add(new Date(), BigDecimal.valueOf(666));
		FileCsvDao.write(HOME_SIVAS_TEST_FINDATA_CSV, cd.getRawData());
	}

	@Test
	public void test() {
		FinChartData cd = FinChartData.create();

		final Date date = DateUtils.toDate(LocalDate.of(2018, 3, 15));

		cd.add(date, BigDecimal.valueOf(888.67));

		final Csv rawData = cd.getRawData();
		final String fileName = HOME_SIVAS_TEST_FINDATA_CSV;
		FileCsvDao.write(fileName, rawData);

		final Csv read = FileCsvDao.read(fileName);
		final FinChartData readCd = FinChartData.create(read);
		final BigDecimal data = readCd.getData(date);
		System.out.println(data);
	}

}
