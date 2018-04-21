package de.elsivas.odd;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import de.elsivas.basic.DateUtils;
import finance.calc.EsFinanceCalcService;
import finance.calc.EsFinanceCalcServiceImpl;
import finance.io.EsFinanceReaderUtils;

public class EsFinanceCalcServiceImplTest {

	private static final Log LOG = LogFactory.getLog(EsFinanceCalcServiceImplTest.class);

	private EsFinanceCalcService service;
	private final Map<Date, Double> history = new HashMap<>();

	@Test
	public void testSMA() {
		LOG.info(service.calcSMA(history, 38));
	}

	@Test
	public void testWMA() {
		LOG.info(service.calcWMA(history, 38));
	}
	
	@Test
	public void testEMA() {
		LOG.info(service.calcEMA(history, 38));
	}

	@Before
	public void init() {
		service = new EsFinanceCalcServiceImpl();
		
		final Map<Date, Double> readHistoryFile = EsFinanceReaderUtils.readHistoryFile("/tmp/wkn_846900_historic.csv");
		history.putAll(readHistoryFile);
		
//		history.put(DateUtils.toDate(LocalDate.of(2018, 1, 10)), Double.valueOf(1000));
//		history.put(DateUtils.toDate(LocalDate.of(2018, 1, 11)), Double.valueOf(1100));
//		history.put(DateUtils.toDate(LocalDate.of(2018, 1, 12)), Double.valueOf(1200));
//		history.put(DateUtils.toDate(LocalDate.of(2018, 1, 13)), Double.valueOf(1300));
//		history.put(DateUtils.toDate(LocalDate.of(2018, 1, 14)), Double.valueOf(1400));
		
	}

}
