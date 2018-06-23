package de.elsivas.finance.logic.analyze.interpretation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;

import de.elsivas.basic.BaseCalcUtils;
import de.elsivas.basic.DateUtils;
import de.elsivas.basic.EsLogicException;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.analyze.Tendence;
import de.elsivas.finance.logic.analyze.TimePeriod;
import de.elsivas.finance.logic.analyze.indicators.FinValueIndicators;
import de.elsivas.finance.logic.analyze.simple.FinSimpleAnalyzeUtils;

public class FinValueInterpretationUtils {

	private static int xma(BigDecimal price, BigDecimal reference) {
		return price.compareTo(reference);
	}

	private static int trend(Tendence tendence) {
		switch (tendence) {
		case NEUTRAL:
			return 0;
		case UP:
		case HALF_UP:
			return 1;
		case DOWN:
		case HALF_DOWN:
			return -1;
		default:
			throw new EsRuntimeException("not supported: " + tendence);
		}
	}

	private static int trend2(Collection<ShareValuePeriod> all, final Date date, final TimePeriod quartalYear,
			final Wertpapier wertpapier) {
		try {
			return trend(FinSimpleAnalyzeUtils.calcTendence(all, quartalYear, wertpapier, DateUtils.toLocalDate(date)));
		} catch (EsLogicException e) {
			throw new EsRuntimeException("error get trend", e);
		}
	}

	public static FinValueInterpretation determine(FinValueIndicators indicators,
			
			// TODO das sollte hier nicht rein, für jeden Tag sollte es diese werte vorab geben
			Collection<ShareValuePeriod> all,
			
			
			Collection<FinValueIndicators> allIndicators) {
		return new FinValueInterpretation() {

			@Override
			public int getWMA38to200() {
				return xma(indicators.getWMA38(), indicators.getWMA200());
			}

			@Override
			public int getWMA38() {
				return xma(indicators.getPrice(), indicators.getWMA38());
			}

			@Override
			public int getWMA200() {
				return xma(indicators.getPrice(), indicators.getWMA200());
			}

			@Override
			public int getTrendMonth() {
				return trend2(all, indicators.getDate(), TimePeriod.MONTH, indicators.getWertpapier());
			}

			@Override
			public int getTrendYear() {
				return trend2(all, indicators.getDate(), TimePeriod.YEAR, indicators.getWertpapier());
			}

			@Override
			public int getTrendWeek() {
				return trend2(all, indicators.getDate(), TimePeriod.WEEK, indicators.getWertpapier());
			}

			@Override
			public int getTrend3Month() {
				return trend2(all, indicators.getDate(), TimePeriod.QUARTAL_YEAR, indicators.getWertpapier());
			}

			@Override
			public int getSSTOC() {
				// TODO beruecksichtigen SSTOCsignal

				BigDecimal sstoc = indicators.getSSTOC();
				final boolean ueberkauft = BaseCalcUtils.isBigger(sstoc, BigDecimal.valueOf(80));
				final boolean ueberverkauft = BaseCalcUtils.isBigger(BigDecimal.valueOf(20), sstoc);
				if (ueberkauft) {
					return -1;
				} else if (ueberverkauft) {
					return 1;
				}
				return 0;
			}

			@Override
			public int getRSI() {
				final BigDecimal rsi = indicators.getRSI();
				if (BaseCalcUtils.isBigger(rsi, BigDecimal.valueOf(70))) {
					return -1;
				}
				if (BaseCalcUtils.isBigger(BigDecimal.valueOf(30), rsi)) {
					return 1;
				}
				return 0;
			}

			@Override
			public int getMACD() {
				// TODO beruecksichtigen MACDsignal
				if (BaseCalcUtils.isBigger(indicators.getMACD(), BigDecimal.ZERO)) {
					return -1;
				}
				if (BaseCalcUtils.isBigger(BigDecimal.ZERO, indicators.getMACD())) {
					return 1;
				}
				return 0;
			}

			@Override
			public int getBollinger() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Date getDate() {
				return indicators.getDate();
			}

			@Override
			public Wertpapier getWertpapier() {
				return indicators.getWertpapier();
			}
		};
	}

}
