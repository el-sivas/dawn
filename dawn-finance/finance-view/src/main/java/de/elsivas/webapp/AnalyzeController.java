package de.elsivas.webapp;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.elsivas.basic.DateUtils;
import de.elsivas.finance.data.model.KeyPerformanceIndicator;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.calc.FinBaseCalcUtils;
import de.elsivas.webapp.base.AbstractValueController;
import de.elsivas.webapp.base.Parameter;
import de.elsivas.webapp.base.ParameterCache;
import de.elsivas.webapp.base.ParameterCacheImpl;
import de.elsivas.webapp.base.StateController;
import de.elsivas.webapp.logic.ShareValuePeriodLogicService;

@ManagedBean
@SessionScoped
public class AnalyzeController extends AbstractValueController<Analyze> implements StateController {

	private ParameterCache parameterCache = ParameterCacheImpl.getInstance();

	private ShareValuePeriodLogicService shareValuePeriodLogicService = ShareValuePeriodLogicService.getInstance();

	private Wertpapier wertpapier = Wertpapier.DAX;

	private Date date;

	private final Collection<Analyze> analyzes = new TreeSet<>();

	@Override
	public String getState() {
		return parameterCache.getAvailableKeys().isEmpty() ? "not configured" : "configured";
	}

	public Collection<Date> getDates() {
		final Stream<ShareValuePeriod> stream = findAll().stream();
		final Stream<Date> map = stream.map(e -> e.getDate());
		final List<Date> c = map.collect(Collectors.toList());
		Collections.reverse(c);

		return c;
	}

	public Collection<ShareValuePeriod> findAll() {
		return shareValuePeriodLogicService.findAll(parameterCache.getStringValue(Parameter.WORKDIR));
	}

	public void calc() {
		if (date == null) {
			addError("no date set");
			return;
		}
		if (analyzes.isEmpty()) {
			addError("nothing to analyze");
			return;
		}
		final long startCalc = System.currentTimeMillis();
		final Collection<ShareValuePeriod> periods = findAll();
		for (Analyze analyze : analyzes) {
			final Map<String, String> parameters = analyze.getMappedParameters();
			final KeyPerformanceIndicator kpi = analyze.getKpi();
			switch (kpi) {
			case SMA:
				final LocalDate end = DateUtils.toLocalDate(date);
				final Integer days = Integer.valueOf(parameters.get("d"));
				final LocalDate start = FinBaseCalcUtils.determineStart(periods, end, days);
				analyze.setResult(FinBaseCalcUtils.calcSMA(periods, start, end));
				break;
			default:
				addError("no supported KPI: " + kpi);
			}
		}
		addInfo("calc took (ms): " + (System.currentTimeMillis() - startCalc));
	}

	public Collection<Wertpapier> getWertpapiere() {
		return Arrays.asList(Wertpapier.values());
	}

	public Wertpapier getWertpapier() {
		return wertpapier;
	}

	public void setWertpapier(Wertpapier wertpapier) {
		this.wertpapier = wertpapier;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collection<Analyze> getAnalyzes() {
		return analyzes;
	}

	public void create() {
		setValue(new Analyze());
		addInfo("created");
	}

	public void clear() {
		analyzes.clear();
		addInfo("cleared");
	}

	public void save() {
		final Analyze value = getValue();
		if (!value.isValidParameters(true)) {
			return;
		}
		analyzes.add(getValue());
		setValue(null);
	}

	public void discard() {
		setValue(null);
	}

	public Collection<KeyPerformanceIndicator> getKpis() {
		return Arrays.asList(KeyPerformanceIndicator.values());
	}
}
