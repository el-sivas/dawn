package de.elsivas.odd.calendar;

import org.junit.Test;

import de.elsivas.basic.ioc.DawnContextInitializer;

public class OddContextTest {
	
	@Test
	public void test() {
		DawnContextInitializer.init("test-context.xml");
	}

}
