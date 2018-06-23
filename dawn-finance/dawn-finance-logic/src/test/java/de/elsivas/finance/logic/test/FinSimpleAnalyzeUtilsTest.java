package de.elsivas.finance.logic.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import de.elsivas.basic.EsLogicException;
import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.data.persist.ShareValuePeriodFileDao;
import de.elsivas.finance.logic.analyze.Tendence;
import de.elsivas.finance.logic.analyze.TimePeriod;
import de.elsivas.finance.logic.analyze.simple.FinSimpleAnalyzeUtils;

public class FinSimpleAnalyzeUtilsTest {

	private static final Log LOG = LogFactory.getLog(FinSimpleAnalyzeUtilsTest.class);

	private Collection<ShareValuePeriod> c = new ArrayList<>();

	@Before
	public void before() {
		final ShareValuePeriodFileDao dao = ShareValuePeriodFileDao.instance();
		c = dao.findAllFromDatabase("/home/sivas/Dropbox/invest/");
	}

	@Test
	public void test() throws EsLogicException {
		final Protocolant p = Protocolant.instance();
		final LocalDate d = LocalDate.now();

		analyze(TimePeriod.WEEK, Wertpapier.DAX, p, d);
		analyze(TimePeriod.MONTH, Wertpapier.DAX, p, d);
		analyze(TimePeriod.QUARTAL_YEAR, Wertpapier.DAX, p, d);
		analyze(TimePeriod.YEAR, Wertpapier.DAX, p, d);

		System.out.println();
		System.out.println(p.toProtocol());
	}

	private void analyze(final TimePeriod timePeriod, final Wertpapier wertpapier, Protocolant p, LocalDate end) {
		Tendence calcTendence;
		try {
			calcTendence = FinSimpleAnalyzeUtils.calcTendence(c, timePeriod, wertpapier, p, end);
		} catch (EsLogicException e) {
			LOG.error(e.getMessage(), e);
			return;
		}
		print(calcTendence, timePeriod, wertpapier);

	}

	private void print(Tendence t, TimePeriod p, Wertpapier w) {
		System.out.println(w + ", " + p + ": " + t);
	}

}
