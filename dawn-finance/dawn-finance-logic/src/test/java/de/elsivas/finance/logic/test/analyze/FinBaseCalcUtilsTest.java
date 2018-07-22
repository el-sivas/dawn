package de.elsivas.finance.logic.test.analyze;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import de.elsivas.basic.DateUtils;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.calc.FinBaseCalcUtils;

public class FinBaseCalcUtilsTest {

	@Test
	public void test13() {
		Collection<ShareValuePeriod> periods = new ArrayList<>();

		final ShareValuePeriod p = newPeriod(LocalDate.of(2018, 5, 18), 13077.72);
		periods.add(p);
		periods.add(newPeriod(LocalDate.of(2018, 5, 17), 13114.61));
		periods.add(newPeriod(LocalDate.of(2018, 5, 16), 12996.33));
		periods.add(newPeriod(LocalDate.of(2018, 5, 15), 12970.04));
		periods.add(newPeriod(LocalDate.of(2018, 5, 14), 12977.71));
		periods.add(newPeriod(LocalDate.of(2018, 5, 11), 13001.24));
		periods.add(newPeriod(LocalDate.of(2018, 5, 9), 13022.87));
		periods.add(newPeriod(LocalDate.of(2018, 5, 8), 12943.06));
		periods.add(newPeriod(LocalDate.of(2018, 5, 7), 12912.21));
		periods.add(newPeriod(LocalDate.of(2018, 5, 4), 12948.14));
		periods.add(newPeriod(LocalDate.of(2018, 5, 3), 12819.6));
		periods.add(newPeriod(LocalDate.of(2018, 5, 2), 12690.15));
		periods.add(newPeriod(LocalDate.of(2018, 4, 30), 12802.25));

		final BigDecimal sma = FinBaseCalcUtils.calcSMA(periods, p, 13);
		Assert.assertEquals(BigDecimal.valueOf(12944.31), sma);

	}

	@Test
	public void test4() {
		Collection<ShareValuePeriod> periods = new ArrayList<>();

		final ShareValuePeriod p = newPeriod(LocalDate.of(2018, 5, 18), 13077.72);
		periods.add(p);
		periods.add(newPeriod(LocalDate.of(2018, 5, 17), 13114.61));
		periods.add(newPeriod(LocalDate.of(2018, 5, 16), 12996.33));
		periods.add(newPeriod(LocalDate.of(2018, 5, 15), 12970.04));
		final BigDecimal sma = FinBaseCalcUtils.calcSMA(periods, p, 4);
		Assert.assertEquals(BigDecimal.valueOf(13039.68), sma);
	}

	@Test
	public void test3() {
		Collection<ShareValuePeriod> periods1 = new ArrayList<>();

		periods1.add(newPeriod(LocalDate.of(2018, 5, 18), 13077.72));
		periods1.add(newPeriod(LocalDate.of(2018, 5, 17), 13114.61));
		periods1.add(newPeriod(LocalDate.of(2018, 5, 16), 12996.33));
		periods1.add(newPeriod(LocalDate.of(2018, 5, 15), 12970.04));
		Collection<ShareValuePeriod> periods = periods1;

		final BigDecimal sma = FinBaseCalcUtils.calcSMA(periods, LocalDate.of(2018, 5, 15), LocalDate.of(2018, 5, 18));
		Assert.assertEquals(BigDecimal.valueOf(13039.68), sma);
	}
	
	@Test
	public void test1() {
		Collection<ShareValuePeriod> periods = new ArrayList<>();
		final ShareValuePeriod p = newPeriod(LocalDate.of(2018, 3, 5), 1000);
		periods.add(p);
		periods.add(newPeriod(LocalDate.of(2018, 3, 4), 1200));
		periods.add(newPeriod(LocalDate.of(2018, 3, 3), 1100));

		final BigDecimal sma = FinBaseCalcUtils.calcSMA(periods, p, 3);
		Assert.assertEquals(BigDecimal.valueOf(1100), sma);
	}

	@Test
	public void test() {
		Collection<ShareValuePeriod> periods = new ArrayList<>();
		periods.add(newPeriod(LocalDate.of(2018, 3, 3), 1000));
		periods.add(newPeriod(LocalDate.of(2018, 3, 4), 1200));
		periods.add(newPeriod(LocalDate.of(2018, 3, 5), 1100));

		final BigDecimal sma = FinBaseCalcUtils.calcSMA(periods, LocalDate.of(2018, 3, 3), LocalDate.of(2018, 3, 5));
		Assert.assertEquals(BigDecimal.valueOf(1100), sma);
	}

	private ShareValuePeriod newPeriod(LocalDate date, double last) {
		return new ShareValuePeriod() {

			@Override
			public Date getDate() {
				return DateUtils.toDate(date);
			}

			@Override
			public BigDecimal getHighest() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getLowest() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getFirst() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getLast() {
				return BigDecimal.valueOf(last);
			}

			@Override
			public BigDecimal getVolume() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Wertpapier getValue() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
