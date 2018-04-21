package finance.deprecated;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

public final class EsFinanceParameterHolder {

	private static final EsFinanceParameterHolder HOLDER = new EsFinanceParameterHolder();

	private Properties properties = new Properties();

	private EsFinanceParameterHolder() {

	}

	public static EsFinanceParameterHolder getInstance() {
		return HOLDER;
	}

	public void init(Properties properties) {
		if (!properties.isEmpty()) {
			throw new RuntimeException("already initialized");
		}
		this.properties.putAll(properties);
	}

	public Map<Date,Double> getHistory() {
		return (Map<Date,Double>)(get("HISTORY", Map.class));
	}

	private Object get(String property,Class c) {
		return c.cast(properties.getProperty(property));
	}

}
