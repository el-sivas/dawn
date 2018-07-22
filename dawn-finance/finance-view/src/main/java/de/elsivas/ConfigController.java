package de.elsivas;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//@ManagedBean
//@SessionScoped
public class ConfigController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected void postConstruct() throws Exception {
		FinanceWebappConfig.set("dashboardDays", "0,1,2,3,5,8,13,21,34,55");
		FinanceWebappConfig.set("analyze", "sma:4-38;sma:13-38;sma:38-200");
	}

	public String getWorkdir() {
		return FinanceWebappConfig.getValue("workdir");
	}

	public void setWorkdir(String s) {
		FinanceWebappConfig.set("workdir", s);
	}

	public void setDashboardDays(String s) {
		FinanceWebappConfig.set("dashboardDays", s);
	}

	public String getDashboardDays() {
		return FinanceWebappConfig.getValue("dashboardDays");
	}

	public void setAnalyze(String s) {
		FinanceWebappConfig.set("analyze", s);
	}

	public String getAnalyze() {
		return FinanceWebappConfig.getValue("analyze");
	}

	public String getLog() {
		return AppLog.getLog();
	}

	public String getSystemInfo() {
		final StringBuilder sb = new StringBuilder();
		final Map<String, String> getenv = System.getenv();
		final Set<String> keySet = getenv.keySet();
		for (String string : keySet) {
			sb.append(string + "<br/>");
		}
		return sb.toString();

	}

}
