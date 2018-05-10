package de.elsivas.finance.logic.analyze.simple;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.elsivas.basic.BaseCalcUtils;
import de.elsivas.basic.DateUtils;
import de.elsivas.basic.EsLogicException;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.analyze.Tendence;
import de.elsivas.finance.logic.analyze.TimePeriod;

public class FinSimpleAnalyzeUtils {

	private static final MathContext MC = new MathContext(7, RoundingMode.HALF_UP);

	private static final BigDecimal STRONG_TENDENCE_SIGNIFICANCE = BigDecimal.valueOf(0.05);
	private static final BigDecimal LIGHT_TENDENCE_SIGNIFICANCE = BigDecimal.valueOf(0.025);

	private static final int MAX_SEARCH_DAYS = 10;

	public static Tendence calcTendence(final Collection<ShareValuePeriod> all, final TimePeriod timePeriod,
			final Wertpapier wertpapier, LocalDate end) throws EsLogicException {
		return calcTendence(all, timePeriod, wertpapier, Protocolant.instance(), end);
	}

	public static Tendence calcTendence(final Collection<ShareValuePeriod> all, final TimePeriod timePeriod,
			final Wertpapier wertpapier, Protocolant protocolant, LocalDate end) throws EsLogicException {

		final List<ShareValuePeriod> allFromValue = all.stream().filter(e -> e.getValue() == wertpapier)
				.collect(Collectors.toList());

		final Map<LocalDate, ShareValuePeriod> map = map2(allFromValue);

		final LocalDate start = end.minusDays(timePeriod.getDays());
		final ShareValuePeriod startDay = getMapValue(map, start);
		final ShareValuePeriod endDay = getMapValue(map, end);
		if (startDay == null || endDay == null) {
			throw new EsLogicException("start or today null");
		}
		protocolant.append("Start:" + startDay.getDate() + ": " + startDay.getLast());
		protocolant.append("End:" + endDay.getDate() + ": " + endDay.getLast());

		final BigDecimal startDayPrice = startDay.getLast();
		final BigDecimal endDayPrice = endDay.getLast();

		final BigDecimal difference = endDayPrice.subtract(startDayPrice);
		boolean up = BaseCalcUtils.isBigger(difference, BigDecimal.ZERO);

		final BigDecimal strongSignificance = startDayPrice.multiply(STRONG_TENDENCE_SIGNIFICANCE, MC);
		final BigDecimal lightSignificance = startDayPrice.multiply(LIGHT_TENDENCE_SIGNIFICANCE, MC);

		if (up) {
			if (BaseCalcUtils.isBigger(difference.abs(), strongSignificance)) {
				return Tendence.UP;
			}
			if (BaseCalcUtils.isBigger(difference.abs(), lightSignificance)) {
				return Tendence.HALF_UP;
			}
		} else if (!up) {
			if (BaseCalcUtils.isBigger(difference.abs(), strongSignificance)) {
				return Tendence.DOWN;
			}
			if (BaseCalcUtils.isBigger(difference.abs(), lightSignificance)) {
				return Tendence.HALF_DOWN;
			}
		}
		return Tendence.NEUTRAL;
	}

	private static Map<LocalDate, ShareValuePeriod> map2(Collection<ShareValuePeriod> all) {
		final Map<LocalDate, ShareValuePeriod> map = new HashMap<>();
		all.forEach(e -> map.put(DateUtils.toLocalDate(e.getDate()), e));

		return map;
	}

	private static ShareValuePeriod getMapValue(final Map<LocalDate, ShareValuePeriod> map, LocalDate localDate) {
		final ShareValuePeriod mapValue = getMapValue(map, localDate, 1);
		if (mapValue == null) {
			throw new EsRuntimeException("no value found for:" + localDate);
		}
		return mapValue;
	}

	private static ShareValuePeriod getMapValue(final Map<LocalDate, ShareValuePeriod> map, LocalDate localDate,
			int recursiveCount) {
		if (recursiveCount > MAX_SEARCH_DAYS) {
			return null;
		}
		final ShareValuePeriod shareValuePeriod = map.get(localDate);
		if (shareValuePeriod != null) {
			return shareValuePeriod;
		}
		return getMapValue(map, localDate.minusDays(1), recursiveCount++);
	}

}
