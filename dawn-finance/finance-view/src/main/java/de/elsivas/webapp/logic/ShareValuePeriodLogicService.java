package de.elsivas.webapp.logic;

import java.util.Collection;
import java.util.Collections;

import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;
import de.elsivas.webapp.base.LogicService;

public class ShareValuePeriodLogicService implements LogicService<ShareValuePeriod> {

	private static ShareValuePeriodLogicService instance;

	public static ShareValuePeriodLogicService getInstance() {
		synchronized (ShareValuePeriodLogicService.class) {
			if (instance == null) {
				instance = new ShareValuePeriodLogicService();
			}
		}
		return instance;
	}

	@Override
	public Collection<ShareValuePeriod> findAll() {
		return Collections.emptyList();
	}

	@Override
	public Collection<ShareValuePeriod> findAll(String workdir) {
		final ShareValuePeriodFileDao dao = ShareValuePeriodFileDao.instance();
		return dao.findAllFromDatabase(workdir);
	}

	@Override
	public void save(ShareValuePeriod t) {
		// TODO Auto-generated method stub

	}

}
