package de.elsivas.odd;

import java.time.LocalTime;

import org.junit.Ignore;
import org.junit.Test;

import de.elsivas.odd.logic.clock.bin.BinTime;

public class BinTimeTest {
	
	@Ignore
	@Test
	public void testContinuous() throws InterruptedException {
		while(true) {
			test();
			Thread.sleep(3 * 1000);
		}
	}
	
	@Test
	public void test() {
		print(BinTime.now());
	}
	
	@Test
	public void test2() {
		print(BinTime.of(LocalTime.of(3, 0)));
	}
	
	private void print(Object o) {
		System.out.println(o);
	}

}
