package de.elsivas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.BaseCalcUtils;
import de.elsivas.basic.DateUtils;
import de.elsivas.basic.EsRuntimeException;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.logic.calc.FinBaseCalcUtils;

@ManagedBean
@SessionScoped
public class StartController extends AbstractController implements Serializable {

	private static final String NEWLINE_HTML = "<br/>";

	private static final Log LOG = LogFactory.getLog(StartController.class);

	private static final String BEREICH_OUTPUT = "output";

	private static final String BEREICH_BASIC = "basic";

	private static final String BEREICH_ADVANCED = "avanced";

	private static final String WERT_COLOR = "color";

	private static final String WERT_VALUE = "value";

	private static final String WERT_TOOLTIP = "tooltip";

	private static final String GREEN = "#0f0";

	private static final String YELLOW_GREEN = "#8f0";

	private static final String YELLOW = "#ff0";

	private static final String ORANGE = "#f80";

	private static final String RED = "#f00";

	private static final long serialVersionUID = 1L;

	private CurrentDataHolder dataHolder;

	private final Map<String, String> results = new HashMap<>();

	@Override
	protected void postConstruct() {
		dataHolder = CurrentDataHolderImpl.instance();
	}

	public Collection<FinDataDetail> getAnalyzedData() {
		final Collection<FinDataDetail> createDetail = OutputControllerUtils.createDetail(dataHolder,
				OutputControllerUtils.upTo(38));
		return createDetail.stream().filter(e -> !StringUtils.isBlank(e.getSignal())).collect(Collectors.toList());
	}

	public String wma(int i, int j) {
		final Collection<ShareValuePeriod> data = dataHolder.getData();
		if (data.isEmpty()) {
			return null;
		}
		final BigDecimal wma1 = FinBaseCalcUtils.calcWMA(data, LocalDate.now().minusDays(i), LocalDate.now());
		final BigDecimal wma2 = FinBaseCalcUtils.calcWMA(dataHolder.getData(), LocalDate.now().minusDays(j),
				LocalDate.now());
		if (BaseCalcUtils.isBigger(wma1, wma2)) {
			return GREEN;
		}
		return RED;
	}

	private Integer determineDay(int index) {
		final String string = FinanceWebappConfig.getValue("dashboardDays");
		if (StringUtils.isBlank(string)) {
			return null;
		}
		final String[] split = string.split(",");
		if (split.length - 1 < index) {
			return null;
		}
		return Integer.valueOf(split[index]);
	}

	public String getColor(int index) {
		final BigDecimal value = (BigDecimal) getValue(index);
		if (value == null) {
			return null;
		}

		return calcColor(value);
	}

	public double valueInternal(int tMinusDays) {
		return ((BigDecimal) value(tMinusDays)).doubleValue();
	}

	public String tooltip(int i) {
		return getTooltip(i);
	}

	public String getTooltip(int i) {
		final StringBuilder sb = new StringBuilder();
		sb.append("index: " + i + NEWLINE_HTML);
		sb.append("day: " + determineDay(i) + NEWLINE_HTML);
		final ShareValuePeriod determinePeriod = determinePeriod(determineDay(i));
		if (determinePeriod == null) {
			sb.append("period null");
			return sb.toString();
		}
		final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		sb.append("period: " + sdf.format(determinePeriod.getDate()) + NEWLINE_HTML);
		sb.append("sma4: " + getResult(generateId(determinePeriod, BEREICH_BASIC, "sma4")) + NEWLINE_HTML);
		sb.append("sma13: " + getResult(generateId(determinePeriod, BEREICH_BASIC, "sma13")) + NEWLINE_HTML);
		sb.append("sma38: " + getResult(generateId(determinePeriod, BEREICH_BASIC, "sma38")) + NEWLINE_HTML);
		sb.append("sma200: " + getResult(generateId(determinePeriod, BEREICH_BASIC, "sma200")) + NEWLINE_HTML);

		final String analyzeConfigPattern = FinanceWebappConfig.getValue("analyze");
		if (determinePeriod != null) {
			if (!StringUtils.isEmpty(analyzeConfigPattern)) {
				final String[] analyzes = analyzeConfigPattern.split(";");
				for (String analyze : analyzes) {
					sb.append(analyze + ": " + getResult(generateId(determinePeriod, BEREICH_ADVANCED, analyze))
							+ NEWLINE_HTML);
				}
			}
		}
		return sb.toString();

	}

	public Object getValue(int index) {
		final Integer dermineDay = determineDay(index);
		if (dermineDay == null) {
			return BigDecimal.ZERO;
		}

		final ShareValuePeriod determinePeriod = determinePeriod(determineDay(index));
		if (determinePeriod == null) {
			return null;
		}
		final BigDecimal value = bigDecimalFromResults((generateId(determinePeriod, BEREICH_OUTPUT, WERT_VALUE)));
		if (value == null) {
			return null;
		}
		return value.setScale(2, RoundingMode.HALF_UP);

	}

	public void reanalyze() {
		final long start = System.currentTimeMillis();
		clearResults();
		final Collection<Integer> indexes = getIndex();

		for (final Integer index : indexes) {
			final Integer determineDay = determineDay(index);
			LOG.info("determined day for index " + index + ": " + determineDay);
			if (determineDay == null) {
				final String s = "no determined day for index: " + index;
				LOG.error(s);
				continue;
			}
			final ShareValuePeriod period = determinePeriod(determineDay);
			if (period == null) {
				throw new EsRuntimeException("no period for determined day: " + determineDay);
			}
			analyzeBasics(period);
			analyzeAdvanced(period);
			createTooltip(period, index, determineDay);
			calcColors(period);
		}

		logResults();
		LOG.info(results.keySet().size() + " values calulated");
		AppLog.append("reanalyze took (ms): " + (System.currentTimeMillis() - start) + "; datasets: "
				+ results.keySet().size());
	}

	public Collection<Integer> getIndex() {
		final String value = FinanceWebappConfig.getValue("dashboardDays");
		if (StringUtils.isBlank(value)) {
			return Collections.singletonList(0);
		}
		final String[] split = value.split(",");
		final Collection<Integer> c = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			c.add(i);
		}
		return c;
	}

	private void calcColors(final ShareValuePeriod determinePeriod) {
		final BigDecimal value = bigDecimalFromResults(generateId(determinePeriod, BEREICH_OUTPUT, WERT_VALUE));
		final String color;
		color = calcColor(value);
		putResult(color, generateId(determinePeriod, BEREICH_OUTPUT, WERT_COLOR));
	}

	private String calcColor(final BigDecimal value) {
		final String color;
		if (value == null) {
			return null;
		}
		if (BaseCalcUtils.isBigger(value, BigDecimal.valueOf(0.67))) {
			color = GREEN;
		} else if (BaseCalcUtils.isBigger(value, BigDecimal.valueOf(0.32))) {
			color = YELLOW_GREEN;
		} else if (BaseCalcUtils.isBigger(BigDecimal.valueOf(-0.67), value)) {
			color = RED;
		} else if (BaseCalcUtils.isBigger(BigDecimal.valueOf(-0.32), value)) {
			color = ORANGE;
		} else {
			color = YELLOW;
		}
		return color;
	}

	private void logResults() {
		results.keySet().forEach(e -> LOG.info(e + ": " + results.get(e)));
	}

	private void clearResults() {
		results.clear();
	}

	private void createTooltip(final ShareValuePeriod determinePeriod, final Integer index,
			final Integer determineDay) {
		final StringBuilder sb = new StringBuilder();
		sb.append(new SimpleDateFormat("dd.MM.yyyy").format(determinePeriod.getDate()) + NEWLINE_HTML);
		sb.append("last: " + determinePeriod.getLast() + NEWLINE_HTML);
		sb.append("index:" + index + NEWLINE_HTML);
		sb.append("determined day:" + determineDay + NEWLINE_HTML);
		putResult(sb.toString(), generateId(determinePeriod, BEREICH_OUTPUT, WERT_TOOLTIP));
	}

	private String getResult(String resultId) {
		if (StringUtils.isBlank(resultId)) {
			throw new EsRuntimeException("no resultId");
		}
		final String result = results.get(resultId);
		if (StringUtils.isBlank(result)) {
			LOG.error("no result for resultId: " + resultId);
		}
		return result;
	}

	private void analyzeAdvanced(final ShareValuePeriod determinePeriod) {
		final String value = FinanceWebappConfig.getValue("analyze");
		if (StringUtils.isEmpty(value)) {
			return;
		}
		final String[] analyzes = value.split(";");
		final Collection<BigDecimal> results = new ArrayList<BigDecimal>();

		for (String analyze : analyzes) {
			final String[] split = analyze.split(":");
			if (split.length != 2) {
				throw new EsRuntimeException("not a valid analyze string: " + analyze);
			}
			final String analyzej = split[0].toUpperCase();
			final RoundingMode rm = RoundingMode.HALF_UP;
			switch (analyzej) {
			case "SMA":
				final String analyzez = split[1];
				final String[] what = analyzez.split("-");

				final BigDecimal smaA = bigDecimalFromResults(
						generateId(determinePeriod, BEREICH_BASIC, "sma" + what[0]));

				if (smaA == null) {
					continue;
				}

				final BigDecimal smaB = bigDecimalFromResults(
						generateId(determinePeriod, BEREICH_BASIC, "sma" + what[1]));

				if (smaB == null) {
					continue;
				}

				if (BaseCalcUtils.isBigger(smaA, smaB)) {
					results.add(BigDecimal.ONE);
				} else {
					results.add(BigDecimal.valueOf(-1));
				}
				putResult(smaA.setScale(0, rm) + "-" + smaB.setScale(0, rm),
						generateId(determinePeriod, BEREICH_ADVANCED, analyze));
				break;
			case "WMA":
				final String analyzeWma = split[1];
				final String[] whatWma = analyzeWma.split("-");

				final BigDecimal wmaA = bigDecimalFromResults(
						generateId(determinePeriod, BEREICH_BASIC, "wma" + whatWma[0]));

				if (wmaA == null) {
					continue;
				}

				final BigDecimal wmaB = bigDecimalFromResults(
						generateId(determinePeriod, BEREICH_BASIC, "wma" + whatWma[1]));

				if (wmaB == null) {
					continue;
				}

				if (BaseCalcUtils.isBigger(wmaA, wmaB)) {
					results.add(BigDecimal.ONE);
				} else {
					results.add(BigDecimal.valueOf(-1));
				}
				putResult(wmaA.setScale(0, rm) + "-" + wmaB.setScale(0, rm),
						generateId(determinePeriod, BEREICH_ADVANCED, analyze));
				break;

			default:
				throw new EsRuntimeException("not supported: " + analyzej);
			}
		}

		if (results.isEmpty()) {
			LOG.warn("no value");
			return;
		}

		final BigDecimal addAll = BaseCalcUtils.addAll(results);
		final int size = results.size();
		final BigDecimal valueResult = BaseCalcUtils.div(addAll, size);
		bigDecimalToResults(valueResult, generateId(determinePeriod, BEREICH_OUTPUT, WERT_VALUE));
	}

	private void bigDecimalToResults(final BigDecimal valueResult, final String resultId) {
		putResult(String.valueOf(valueResult.doubleValue()), resultId);
	}

	private BigDecimal bigDecimalFromResults(final String resultId) {
		if (resultId == null) {
			return null;
		}
		final String string = getResult(resultId);
		if (string == null) {
			return null;
		}
		return BigDecimal.valueOf(Double.valueOf(string));
	}

	private void analyzeBasics(final ShareValuePeriod period) {
		final List<ShareValuePeriod> data = dataHolder.getDataOrderedDecreasing();
		final int indexOf = data.indexOf(period);

		LocalDate end = DateUtils.toLocalDate(period.getDate());
		for (int i = 0; i < 300; i++) {
			final int sma = i + 1;
			final int indexOfStart = indexOf + sma - 1;
			if (indexOfStart >= data.size()) {
				LOG.warn("index out ouf bound: " + indexOf + ", size:" + data.size() + ". break.");
				break;
			}
			final Date date = data.get(indexOfStart).getDate();
			final BigDecimal calcSma = FinBaseCalcUtils.calcSMA(data, period, sma);

			LOG.info("calc sma " + sma + " for " + period + " start:" + date + ", end:" + end + " sma:" + calcSma);
			putResult(String.valueOf(Double.valueOf(calcSma.doubleValue())),
					generateId(period, BEREICH_BASIC, "sma" + sma));
		}
	}

	private void putResult(final String result, final String generateId) {
		results.put(generateId, result);
	}

	private ShareValuePeriod determinePeriod(Integer index) {
		if (index == null) {
			return null;
		}
		final List<ShareValuePeriod> dataOrderedDecreasing = dataHolder.getDataOrderedDecreasing();
		if (dataOrderedDecreasing.isEmpty()) {
			return null;
		}
		return dataOrderedDecreasing.get(index);
	}

	private String generateId(ShareValuePeriod p, String bereich, String wert) {
		if (p == null) {
			throw new EsRuntimeException("period null");
		}
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return simpleDateFormat.format(p.getDate()) + "_" + bereich + "_" + wert;
	}

	@Deprecated
	public Object value(HashSet<Integer> tMinusDays) {
		return getValue((new ArrayList<Integer>(tMinusDays).get(0)));
	}

	@Deprecated
	public Object value(Integer tMinusDays) {
		return getValue(tMinusDays);
	}

	@Deprecated
	public String color(HashSet<Integer> index) {
		return getColor(new ArrayList<>(index).get(0));
	}

	@Deprecated
	public String color(int index) {
		return getColor(index);
	}

	@Deprecated
	public String tooltip(HashSet<Integer> index) {
		return getTooltip(new ArrayList<>(index).get(0));
	}
}
