package finance.deprecated;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EsFinanceCalcServiceImpl implements EsFinanceCalcService {
	
	private static final Log LOG = LogFactory.getLog(EsFinanceCalcServiceImpl.class);

	private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(7, RoundingMode.HALF_UP);

	@Override
	public BigDecimal calcSMA(final Map<Date, Double> history, int days) {
		if (history.isEmpty()) {
			throw new EsFinanceRuntimeException("history empty");
		}

		if (days < 1) {
			throw new EsFinanceRuntimeException("days < 1");
		}

		final Set<Data> reverseDataset = createReverseDataset(history);
		final ArrayList<Data> reverseList = new ArrayList<>(reverseDataset);

		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < days; i++) {

			sum = sum.add(BigDecimal.valueOf(reverseList.get(i).getValue()));
		}
		return sum.divide(BigDecimal.valueOf(days), DEFAULT_MATH_CONTEXT);
	}

	private Set<Data> createReverseDataset(final Map<Date, Double> history) {
		return createDataset(history, new DataReverseComparator());
	}

	private Set<Data> createDataset(final Map<Date, Double> history, final Comparator<Data> comparator) {
		final Set<Data> set = new TreeSet<>(comparator);
		final Set<Date> keySet = history.keySet();
		keySet.forEach(e -> set.add(Data.create(history.get(e), e)));
		return set;
	}
	


	@Override
	public BigDecimal calcWMA(Map<Date, Double> history, int days) {
		if (history.isEmpty()) {
			throw new EsFinanceRuntimeException("history empty");
		}

		if (days < 1) {
			throw new EsFinanceRuntimeException("days < 1");
		}

		final Set<Data> reverseDataset = createReverseDataset(history);
		final ArrayList<Data> reverseList = new ArrayList<>(reverseDataset);

		BigDecimal sum = BigDecimal.ZERO;
		int sumFactor = 0;
		for (int i = 0; i < days; i++) {
			int factor = days - i;
			sumFactor += factor;
			final BigDecimal valueOfDay = BigDecimal.valueOf(reverseList.get(i).getValue());
			LOG.debug("EMA: " + valueOfDay + ", f: " + factor);

			sum = sum.add(valueOfDay.multiply(BigDecimal.valueOf(factor)));
		}
		return sum.divide(BigDecimal.valueOf(sumFactor), DEFAULT_MATH_CONTEXT);
	}

	@Override
	public BigDecimal calcEMA(Map<Date, Double> history, int days) {
		
		final BigDecimal multiplicant = days <= 1 ? BigDecimal.ONE : BigDecimal.valueOf(1 -(1.0 / days));
		
		if (history.isEmpty()) {
			throw new EsFinanceRuntimeException("history empty");
		}

		if (days < 1) {
			throw new EsFinanceRuntimeException("days < 1");
		}

		final Set<Data> reverseDataset = createReverseDataset(history);
		final ArrayList<Data> reverseList = new ArrayList<>(reverseDataset);

		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal allWeights = BigDecimal.ZERO;
		BigDecimal currentWeight = BigDecimal.ONE;
		for (int i = 0; i < days; i++) {

			final BigDecimal valueOfDay = BigDecimal.valueOf(reverseList.get(i).getValue());
			sum = sum.add(valueOfDay.multiply(currentWeight));
			LOG.debug("EMA: " + valueOfDay + ", w: " + currentWeight);
			allWeights = allWeights.add(currentWeight);
			currentWeight = currentWeight.multiply(multiplicant);
		}
		return sum.divide(allWeights, DEFAULT_MATH_CONTEXT);
	}

}
