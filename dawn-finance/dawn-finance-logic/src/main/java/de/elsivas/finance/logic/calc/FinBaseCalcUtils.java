package de.elsivas.finance.logic.calc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.elsivas.basic.BaseCalcUtils;
import de.elsivas.basic.DateUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.ShareValuePeriodNewToOldComparator;

public class FinBaseCalcUtils {

	private static final MathContext MATH_CONTEXT = new MathContext(15, RoundingMode.HALF_UP);

	public static LocalDate determineStart(Collection<ShareValuePeriod> periods, LocalDate end, int days) {
		Set<ShareValuePeriod> set = new TreeSet<>(new ShareValuePeriodNewToOldComparator());
		set.addAll(periods);
		final List<LocalDate> collect = set.stream().map(e -> DateUtils.toLocalDate(e.getDate()))
				.collect(Collectors.toList());
		final int indexOf = collect.indexOf(end);
		return DateUtils.toLocalDate(new ArrayList<>(set).get(indexOf + days).getDate());
	}

	public static BigDecimal calcSMA(Collection<ShareValuePeriod> periods, ShareValuePeriod p, int amount) {
		if (!periods.contains(p)) {
			throw new EsRuntimeException("p not in periods");
		}
		final ArrayList<ShareValuePeriod> arrayList = new ArrayList<>(periods);
		final ShareValuePeriodNewToOldComparator comp = new ShareValuePeriodNewToOldComparator();
		Collections.sort(arrayList, comp);
		final int startIndex = arrayList.indexOf(p) + amount - 1;
		if (startIndex > (periods.size() - 1)) {
			throw new EsRuntimeException("error calc SMA" + amount + " for " + p + ". Not enough values.");
		}
		final ShareValuePeriod shareValuePeriod = arrayList.get(startIndex);
		if (shareValuePeriod == null) {
			throw new EsRuntimeException("no value for index: " + startIndex);
		}
		return calcSMA(periods, DateUtils.toLocalDate(shareValuePeriod.getDate()), DateUtils.toLocalDate(p.getDate()));

	}

	public static BigDecimal calcSMA(Collection<ShareValuePeriod> periods, LocalDate start, LocalDate end) {
		if (start.isAfter(end)) {
			throw new EsRuntimeException("end (" + end + ") after start (" + start + ")");
		}
		final Collection<ShareValuePeriod> periods2 = new TreeSet<>(new ShareValuePeriodNewToOldComparator());
		periods2.addAll(filterRelevantPeriods(periods, start, end));

		final Collection<BigDecimal> sum = new ArrayList<>();

		periods2.forEach(e -> sum.add(e.getLast()));

		return BaseCalcUtils.div(BaseCalcUtils.addAll(sum), sum.size());
	}

	/**
	 * 
	 * @param periods
	 *            All unsorted, relevant periods between <b>start</b> and <b>end</b>
	 * @param start
	 *            Date from when to the future the calculation should be done, i.e.
	 *            21.02.2018
	 * @param end
	 *            Date from when from the past the calculation should be done, i.e.
	 *            15.03.2018
	 * @return
	 */
	public static BigDecimal calcWMA(Collection<ShareValuePeriod> periods, LocalDate start, LocalDate end) {
		return calcXMA2(periods, start, end, true);
	}

	private static BigDecimal calcXMA(Collection<ShareValuePeriod> periods, LocalDate start, LocalDate end,
			boolean loss) {
		if (start.isAfter(end)) {
			throw new EsRuntimeException("end (" + end + ") after start (" + start + ")");
		}
		final List<ShareValuePeriod> relevantPeriods = filterRelevantPeriods(periods, start, end);

		Collections.reverse(relevantPeriods);

		double lossPerDay = loss ? 1.0 / DateUtils.daysDifference(start, end) : 0;
		double cnt = 0;
		BigDecimal sum = BigDecimal.ZERO;
		double currentLoss = 0;
		for (ShareValuePeriod shareValuePeriod : relevantPeriods) {
			cnt = cnt + 1 * (1 - currentLoss);
			final BigDecimal last = shareValuePeriod.getLast();
			final BigDecimal multiply = last.multiply(BigDecimal.valueOf(1 - currentLoss));
			sum = sum.add(multiply);
			currentLoss = currentLoss - lossPerDay;
		}
		return sum.divide(BigDecimal.valueOf(cnt), MATH_CONTEXT);
	}

	private static List<ShareValuePeriod> filterRelevantPeriods(Collection<ShareValuePeriod> periods, LocalDate start,
			LocalDate end) {
		final List<ShareValuePeriod> relevantPeriods = periods.stream()
				.filter(e -> DateUtils.isWithin(start, end, DateUtils.toLocalDate(e.getDate())))
				.collect(Collectors.toList());
		return relevantPeriods;
	}

	private static BigDecimal calcXMA2(Collection<ShareValuePeriod> periods, LocalDate start, LocalDate end,
			boolean loss) {
		final List<ShareValuePeriod> relevantPeriods = filterRelevantPeriods(periods, start, end);
		Collections.reverse(relevantPeriods);
		BigDecimal sum = BigDecimal.ZERO;
		double weights = 0;
		double currentWeight = 1;
		for (ShareValuePeriod shareValuePeriod : relevantPeriods) {
			final BigDecimal last = shareValuePeriod.getLast();
			final BigDecimal multiply = last.multiply(BigDecimal.valueOf(currentWeight));
			sum = sum.add(multiply);
			weights += currentWeight;
			currentWeight *= 0.9;
		}
		return BaseCalcUtils.div(sum, weights);
	}

}
