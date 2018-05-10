package de.elsivas.finance.logic.analyze.indicators;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.elsivas.basic.DateUtils;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.analyze.TimePeriod;
import de.elsivas.finance.logic.calc.FinBaseCalcUtils;

public class FinValueIndicatorsUtils {

	private static final MathContext MC = new MathContext(7, RoundingMode.HALF_DOWN);

	public Collection<FinValueIndicators> determineAll(Collection<ShareValuePeriod> all) {
		final Collection<FinValueIndicators> list = new ArrayList<>();
		all.forEach(e -> list.add(determine(e, all)));
		return list;
	}

	private List<ShareValuePeriod> asReverseList(Collection<ShareValuePeriod> all) {
		final List<ShareValuePeriod> p = new ArrayList<>(all);
		Collections.reverse(p);
		return p;
	}

	public FinValueIndicators determine(ShareValuePeriod period, Collection<ShareValuePeriod> all) {
		return new FinValueIndicators() {

			@Override
			public Wertpapier getWertpapier() {
				return period.getValue();
			}

			@Override
			public BigDecimal getWMA38() {

				// FIXME die FinBaseCalcUtils arbeiten ungenau. Nicht die letzen 38 Kalender-
				// sondern Handelstage sind zu berücksichtigen.

				final LocalDate localDate = DateUtils.toLocalDate(period.getDate());
				return FinBaseCalcUtils.calcWMA(all, localDate.minusDays(38), localDate);
			}

			@Override
			public BigDecimal getWMA200() {
				// FIXME siehe wma38
				final LocalDate localDate = DateUtils.toLocalDate(period.getDate());
				return FinBaseCalcUtils.calcWMA(all, localDate.minusDays(38), localDate);
			}

			@Override
			public BigDecimal getSSTOCsignal() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getSSTOC() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getRSI() {
				return FinValueIndicatorRsiUtils.calcRsi(14, all, period);
			}

			@Override
			public BigDecimal getPrice() {
				return period.getLast();
			}

			@Override
			public Map<TimePeriod, BigDecimal> getMovement() {
				// FIXME nicht ganz präszse, z.B. 1 Wo != 5 Tage.

				final List<ShareValuePeriod> p = asReverseList(all);

				final Map<TimePeriod, BigDecimal> map = new HashMap<>();

				map.put(TimePeriod.WEEK, period.getLast().subtract(p.get(5).getLast(), MC));
				map.put(TimePeriod.MONTH, period.getLast().subtract(p.get(30).getLast(), MC));
				map.put(TimePeriod.QUARTAL_YEAR, period.getLast().subtract(p.get(90).getLast(), MC));
				map.put(TimePeriod.YEAR, period.getLast().subtract(p.get(360).getLast(), MC));

				return map;
			}

			@Override
			public BigDecimal getMACDsignal() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getMACD() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Date getDate() {
				return period.getDate();
			}

			@Override
			public BigDecimal getBollingerExit() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getBollingerEntry() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getBollingerAvg() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
