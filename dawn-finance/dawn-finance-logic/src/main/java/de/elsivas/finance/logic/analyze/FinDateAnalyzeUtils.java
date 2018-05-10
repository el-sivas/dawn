package de.elsivas.finance.logic.analyze;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.calc.FinBaseCalcUtils;

public class FinDateAnalyzeUtils {

	private final static ShareValuePeriodFileDao DAO = ShareValuePeriodFileDao.instance();

	public static FinDateAnalyze analyze(FinProperties properties, LocalDate date, Protocolant protocolant) {
		final String workdir = properties.getWorkdir();
		final Set<ShareValuePeriod> db = DAO.findAllFromDatabase(workdir);

		return new FinDateAnalyze() {

			@Override
			public BigDecimal getSMA(int days) {
				return FinBaseCalcUtils.calcSMA(db, date, date.minusDays(days));
			}

			@Override
			public BigDecimal getWMA(int days) {
				return FinBaseCalcUtils.calcWMA(db, date, date.minusDays(days));
			}

			@Override
			public BigDecimal getPrice() {
				final List<ShareValuePeriod> list = new ArrayList<>(db);
				Collections.reverse(list);
				return list.get(0).getLast();
			}
		};

	}

	public static Options getOptions() {
		final Options options = new Options();
		options.addOption(new Option(FinProperties.ARG_VALUE, true, "value"));
		options.addOption(new Option(FinProperties.ARG_WORKDIR, true, "workdir"));

		return options;
	}

}
