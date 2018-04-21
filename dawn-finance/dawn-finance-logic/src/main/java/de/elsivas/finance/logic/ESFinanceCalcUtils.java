package de.elsivas.finance.logic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;

import de.elsivas.basic.DateUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.data.Chart;

public class ESFinanceCalcUtils {

	private static final MathContext DEFAULT_MC = new MathContext(7, RoundingMode.HALF_UP);

	public static BigDecimal calcSMA(Chart cd, int days) {
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < days; i++) {
			final Date date = DateUtils.toDate(LocalDate.now().minusDays(i));
			final BigDecimal data = cd.getData(date);
			if (data == null) {
				throw new EsRuntimeException("no value: " + date);
			}
			sum = sum.add(data);
		}
		return sum.divide(BigDecimal.valueOf(days), DEFAULT_MC);
	}

	public static BigDecimal calcWMA(final Chart cd, int days) {
		final BigDecimal wmaMin = ESFinConfig.get(ESFinConfig.WMA_MIN, BigDecimal.class);
		final BigDecimal diff = BigDecimal.ONE.subtract(wmaMin);
		final BigDecimal weightLossPerDay = diff.divide(BigDecimal.valueOf(days), DEFAULT_MC);

		BigDecimal weight = BigDecimal.ONE;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal weightSum = BigDecimal.ZERO;
		for (int i = 0; i < days; i++) {
			final Date date = DateUtils.toDate(LocalDate.now().minusDays(i));
			final BigDecimal data = cd.getData(date);
			sum = sum.add(data.multiply(weight));
			weightSum = weightSum.add(weight);
			weight = weight.subtract(weightLossPerDay);

		}
		return sum.divide(weightSum, DEFAULT_MC);

	}

}
