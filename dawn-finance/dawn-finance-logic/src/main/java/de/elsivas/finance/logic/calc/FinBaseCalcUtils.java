package de.elsivas.finance.logic.calc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.elsivas.basic.DateUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.data.model.ShareValuePeriod;

public class FinBaseCalcUtils {

	private static final MathContext MATH_CONTEXT = new MathContext(15, RoundingMode.HALF_UP);

	public static BigDecimal calcSMA(Collection<ShareValuePeriod> periods, LocalDate start, LocalDate end) {
		return calcXMA(periods, start, end, false);
	}

	public static BigDecimal calcWMA(Collection<ShareValuePeriod> periods, LocalDate start, LocalDate end) {
		return calcXMA(periods, start, end, true);
	}

	private static BigDecimal calcXMA(Collection<ShareValuePeriod> periods, LocalDate start, LocalDate end,
			boolean loss) {
		if (start.isAfter(end)) {
			throw new EsRuntimeException("end after start");
		}
		final List<ShareValuePeriod> relevantPeriods = periods.stream()
				.filter(e -> DateUtils.isWithin(start, end, DateUtils.toLocalDate(e.getDate())))
				.collect(Collectors.toList());

		Collections.reverse(relevantPeriods);

		double lossPerDay = loss ? 1.0 / DateUtils.daysDifference(start, end) : 0;
		double cnt = 0;
		BigDecimal sum = BigDecimal.ZERO;
		double currentLoss = 0;
		for (ShareValuePeriod shareValuePeriod : relevantPeriods) {
			cnt = cnt + 1 * (1 - currentLoss);
			sum = sum.add(shareValuePeriod.getLast().multiply(BigDecimal.valueOf(1 - currentLoss)));
			currentLoss = currentLoss - lossPerDay;
		}
		return sum.divide(BigDecimal.valueOf(cnt), MATH_CONTEXT);
	}

}
