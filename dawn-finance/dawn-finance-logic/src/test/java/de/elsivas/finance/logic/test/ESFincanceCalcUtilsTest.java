package de.elsivas.finance.logic.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.elsivas.basic.DateUtils;
import de.elsivas.finance.logic.FinConfig;
import de.elsivas.finance.logic.FinCalcUtils;
import de.elsivas.finance.model.FinChart;

public class ESFincanceCalcUtilsTest {

	private Map<Date, BigDecimal> map = new HashMap<>();

	@Test
	public void test2() {
		final Map<Date, BigDecimal> map2 = new HashMap<>();
		map2.put(DateUtils.toDate(LocalDate.now().minusDays(0)), BigDecimal.valueOf(1000));
		map2.put(DateUtils.toDate(LocalDate.now().minusDays(1)), BigDecimal.valueOf(900));
		map2.put(DateUtils.toDate(LocalDate.now().minusDays(2)), BigDecimal.valueOf(800));

		Map<String, String> config = new HashMap<>();
		config.put(FinConfig.WMA_MIN, String.valueOf(0.5));
		FinConfig.init(config);
		final FinChart dataset = dataset(map2);

		final BigDecimal sma = FinCalcUtils.calcSMA(dataset, 3);
		Assert.assertTrue(BigDecimal.valueOf(900).equals(sma));

		final BigDecimal wma = FinCalcUtils.calcWMA(dataset, 3);
		Assert.assertTrue(BigDecimal.valueOf(913.3333).equals(wma));

	}

	@Test
	public void test() {
		Map<String, String> config = new HashMap<>();
		config.put(FinConfig.WMA_MIN, String.valueOf(0.5));
		FinConfig.init(config);
		final FinChart cd = dataset(map);

		final BigDecimal sma = FinCalcUtils.calcSMA(cd, 38);
		Assert.assertTrue(BigDecimal.valueOf(1008.315).equals(sma));

		final BigDecimal wma = FinCalcUtils.calcWMA(cd, 38);
		Assert.assertTrue(BigDecimal.valueOf(1006.629).equals(wma));
	}

	private FinChart dataset(Map<Date, BigDecimal> cdMap) {
		return new FinChart() {

			@Override
			public BigDecimal getData(Date date) {
				return cdMap.get(date);
			}
		};
	}

	@Before
	public void init() {
		add(1000, 0);
		add(976.9810533121, 1);
		add(1017.6831263839, 2);
		add(914.5263905467, 3);
		add(1080.5743800775, 4);
		add(985.2788982268, 5);
		add(1055.424141331, 6);
		add(987.6652833408, 7);
		add(951.2535289876, 8);
		add(1077.1457090912, 9);
		add(946.3185679444, 10);
		add(1064.2261571016, 11);
		add(1016.2938244411, 12);
		add(927.6976652256, 13);
		add(1033.8425313109, 14);
		add(1088.4401816353, 15);
		add(908.8146676171, 16);
		add(977.284844073, 17);
		add(1000.2361102786, 18);
		add(922.7778684388, 19);
		add(987.8611382219, 20);
		add(983.7072645278, 21);
		add(1015.4316255419, 22);
		add(1090.8997991539, 23);
		add(903.9420910477, 24);
		add(1023.110970257, 25);
		add(1093.0847728609, 26);
		add(990.6828339796, 27);
		add(1093.7889348755, 28);
		add(1051.9941207016, 29);
		add(1066.6883971899, 30);
		add(1008.0379206314, 31);
		add(1061.0344853342, 32);
		add(1013.0017337496, 33);
		add(944.5633878327, 34);
		add(1076.567707395, 35);
		add(1017.4726441842, 36);
		add(961.6202497338, 37);
		add(954.7888376465, 38);
		add(1004.2844518553, 39);
		add(1012.1664806063, 40);
		add(934.695603485, 41);
		add(1090.1193569627, 42);
		add(985.0303397041, 43);
		add(937.0323968426, 44);
		add(992.1535505962, 45);
		add(1036.6513211356, 46);
		add(935.2622362257, 47);
		add(1013.5171935731, 48);
		add(1014.68525596, 49);
		add(923.6471103523, 50);

		/*
		 * 
		 * final Set<Date> keySet = map.keySet(); List<Date> dates = new
		 * ArrayList<>(keySet); Collections.sort(dates); dates.forEach(e ->
		 * System.out.println(e + ":" + map.get(e)));
		 * 
		 */

	}

	private void add(double value, long days) {
		map.put(DateUtils.toDate(LocalDate.now().minusDays(days)), BigDecimal.valueOf(value));
	}
}
