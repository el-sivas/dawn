package de.elsivas.finance.logic.analyze.indicators;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.runner.Result;

import de.elsivas.basic.BaseCalcUtils;
import de.elsivas.finance.data.model.ShareValuePeriod;

public class FinValueIndicatorRsiUtils {

	/**
	 * Unpräzise da auf Tagesbasis, statt JEDE Kursänderung
	 */
	public static BigDecimal calcRsi(int days, Collection<ShareValuePeriod> all, ShareValuePeriod period) {
		final List<ShareValuePeriod> allForWertpapier = all.stream().filter(e -> e.getValue() == period.getValue())
				.collect(Collectors.toList());

		Collections.reverse(allForWertpapier);
		final List<ShareValuePeriod> relevantTime = allForWertpapier.subList(0, days);

		Collections.sort(relevantTime);

		final RsiResult result = new RsiResult();

		/*
		 * Beste Kombination bisher: startToEnd + dayGap
		 */
		for (int i = 0; i < relevantTime.size(); i++) {
			final ShareValuePeriod today = relevantTime.get(i);
			// result.add(method2(today));
			result.add(startToEnd(today));
			if (i == 0) {
				continue;
			}
			final ShareValuePeriod yesterday = relevantTime.get(i - 1);

			// geht erst bei 1 los, weil der erste Tag nicht zum Vortag verglichen werden
			// kann

			result.add(dayGap(today, yesterday));
			//result.add(compareLastWithLastDayBefore(today, yesterday));
		}
		final BigDecimal avgUp = BaseCalcUtils.div(result.sumUp, result.countUp);
		final BigDecimal avgDown = BaseCalcUtils.div(result.sumDown, result.countDown);

		final BigDecimal avgSum = BaseCalcUtils.add(avgUp, avgDown);

		final BigDecimal rsi = BaseCalcUtils.div(avgUp, avgSum);

		return BaseCalcUtils.mult(rsi, 100);
	}

	private static RsiResult dayGap(ShareValuePeriod today, ShareValuePeriod yesterday) {
		final RsiResult result = new RsiResult();

		final BigDecimal dayGap = BaseCalcUtils.sub(today.getFirst(), yesterday.getLast());
		final boolean up = BaseCalcUtils.isBigger(dayGap, BigDecimal.ZERO);
		if (up) {
			result.sumUp = dayGap;
			result.countUp++;
		} else {

			result.sumDown = dayGap.abs();
			result.countDown++;
		}

		return result;
	}
	
	

	private static RsiResult method2(final ShareValuePeriod today) {
		final RsiResult result = new RsiResult();
		final BigDecimal highest = today.getHighest();
		final BigDecimal first = today.getFirst();

		final BigDecimal last = today.getLast();
		final BigDecimal lowest = today.getLowest();

		final BigDecimal up1 = BaseCalcUtils.differenceAbs(first, highest);
		result.sumUp = BaseCalcUtils.add(result.sumUp, up1);
		result.countUp++;

		final BigDecimal up2 = BaseCalcUtils.differenceAbs(lowest, last);
		result.sumUp = BaseCalcUtils.add(result.sumUp, up2);
		result.countUp++;

		final BigDecimal down1 = BaseCalcUtils.differenceAbs(first, lowest);
		result.sumDown = BaseCalcUtils.add(result.sumDown, down1);
		result.countDown++;

		final BigDecimal down2 = BaseCalcUtils.differenceAbs(highest, last);
		result.sumDown = BaseCalcUtils.add(result.sumDown, down2);
		result.countDown++;

		return result;
	}

	/**
	 * beste Näherung bisher
	 */
	private static RsiResult compareLastWithLastDayBefore(ShareValuePeriod today, ShareValuePeriod yesterday) {
		final RsiResult result = new RsiResult();
		final BigDecimal lastToday = today.getLast();
		final BigDecimal lastYesterday = yesterday.getLast();
		final BigDecimal diff = BaseCalcUtils.sub(lastToday, lastYesterday);

		final boolean up = BaseCalcUtils.isBigger(diff, BigDecimal.ZERO);
		if (up) {
			result.sumUp = result.sumUp.add(diff.abs());
			result.countUp++;
		} else {
			result.sumDown = result.sumDown.add(diff.abs());
			result.countDown++;
		}
		return result;
	}
	
	private static RsiResult startToEnd(final ShareValuePeriod today) {
		final RsiResult result = new RsiResult();
		
		final BigDecimal first = today.getFirst();
		final BigDecimal last = today.getLast();

		final BigDecimal diff = BaseCalcUtils.sub(last, first);

		final boolean up = BaseCalcUtils.isBigger(diff, BigDecimal.ZERO);
		if (up) {
			result.sumUp = result.sumUp.add(diff.abs());
			result.countUp++;
		} else {
			result.sumDown = result.sumDown.add(diff.abs());
			result.countDown++;
		}
		return result;

	}


}
