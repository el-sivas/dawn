package de.elsivas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;

public class CurrentDataHolderImpl implements CurrentDataHolderControllable {

	private static CurrentDataHolderImpl instance;

	private Collection<ShareValuePeriod> data = new ArrayList<>();

	public static CurrentDataHolder instance() {
		if (instance == null) {
			instance = new CurrentDataHolderImpl();
		}
		return instance;
	}

	public static CurrentDataHolderControllable instanceControllable() {
		return instance;
	}

	private CurrentDataHolderImpl() {
		final String workdir = System.getProperty("slag.workdir");
		FinanceWebappConfig.set("workdir", workdir);
	}

	@Override
	public Collection<ShareValuePeriod> getData() {
		if (data.isEmpty()) {
			load();
		}
		return data;
	}

	public void load() {
		final long start = System.currentTimeMillis();
		data.clear();
		final ShareValuePeriodFileDao dao = ShareValuePeriodFileDao.instance();
		final Set<ShareValuePeriod> all = dao.findAllFromDatabase(FinanceWebappConfig.getValue("workdir"));
		data.addAll(all);

		final StringBuilder sb = new StringBuilder();
		sb.append("data loaded, took: (ms) ");
		sb.append(System.currentTimeMillis() - start);
		AppLog.append(sb.toString());
	}

	@Override
	public void clear() {
		data.clear();
	}

}
