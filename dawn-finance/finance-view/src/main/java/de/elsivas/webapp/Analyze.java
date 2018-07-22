package de.elsivas.webapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.elsivas.finance.data.model.KeyPerformanceIndicator;
import de.elsivas.webapp.base.MessageCache;

public class Analyze implements Comparable<Analyze> {

	private MessageCache messageCache = MessageCache.getInstance();

	private Long id = System.currentTimeMillis();

	private KeyPerformanceIndicator kpi;

	private BigDecimal result;

	private String parameters;

	public KeyPerformanceIndicator getKpi() {
		return kpi;
	}

	public void setKpi(KeyPerformanceIndicator kpi) {
		this.kpi = kpi;
	}

	@Override
	public int compareTo(Analyze o) {
		return this.id.compareTo(o.id);
	}

	public Map<String, String> getMappedParameters() {
		final Map<String, String> map = new HashMap<>();
		Arrays.asList(parameters.split(";")).forEach(e -> map.put(e.split("=")[0], e.split("=")[1]));
		return map;
	}

	public void validateParameters(final Collection<String> errorMessages, final boolean addMessages) {
		final List<String> asList = Arrays.asList(parameters.split(";"));
		for (String string : asList) {
			final String[] split = string.split("=");
			if (split.length != 2) {
				errorMessages.add("not a valid parameter: '" + string + "'");
			}
		}
		if (errorMessages.isEmpty()) {
			return;
		}
		if (!addMessages) {
			return;
		}
		errorMessages.forEach(e -> messageCache.addError(e));
	}

	public boolean isValidParameters(final boolean addMessages) {
		final Collection<String> errorMessages = new ArrayList<String>();
		validateParameters(errorMessages, addMessages);
		return errorMessages.isEmpty();
	}

	public boolean isValidParameters() {
		return isValidParameters(false);
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}
}
