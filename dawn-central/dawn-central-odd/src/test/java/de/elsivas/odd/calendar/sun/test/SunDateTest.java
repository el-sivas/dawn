package de.elsivas.odd.calendar.sun.test;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

import de.elsivas.odd.calendar.sun.SunDate;
import de.elsivas.odd.calendar.sun.SunDateUtils;

public class SunDateTest extends Assert {

	private static final Logger LOG = Logger.getLogger(SunDateTest.class.getName());

	@Test
	public void test2() {
		assertFalse(SunDateUtils.isSchaltJahr(SunDate.of(3)));
		assertTrue(SunDateUtils.isSchaltJahr(SunDate.of(4)));
		assertTrue(SunDateUtils.isSchaltJahr(SunDate.of(16)));
		assertFalse(SunDateUtils.isSchaltJahr(SunDate.of(100)));
		assertTrue(SunDateUtils.isSchaltJahr(SunDate.of(400)));
		assertTrue(SunDateUtils.isSchaltJahr(SunDate.of(2000)));
		assertFalse(SunDateUtils.isSchaltJahr(SunDate.of(1900)));
	}

	@Test
	public void test() {
		final SunDate now = SunDate.now();
		LOG.info(now.toString());
		final int dayOfYear = now.dayOfYear();

	}

	@Test
	public void test3() {
		int max = 200;
		for (int i = 0; i < max; i++) {
			final SunDate ofEpocDay = SunDate.ofEpocDay(i);
			final String string = ofEpocDay.toString();
			LOG.info(string + ":" + ofEpocDay.localDateValue().toString());
		}
	}

}
