package de.elsivas.finance.logic.test.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.logic.analyze.indicators.FinValueIndicatorRsiUtils;
import de.elsivas.finance.logic.test.analyze.support.TestValuesGeneratorUtils;

public class FinValueIndicatorRsiUtilsTest {

	@Test
	public void test() {
		final Collection<ShareValuePeriod> all = TestValuesGeneratorUtils.generate();
		final ArrayList<ShareValuePeriod> arrayList = new ArrayList<>(all);
		Collections.reverse(arrayList);

		log(FinValueIndicatorRsiUtils.calcRsi(14, all, arrayList.get(0)));
	}

	private void log(Object o) {
		System.out.println(o);
	}
}
