package de.elsivas.basic;

public class SleepUtils {
	
	public static void sleepFor(int sleeptime) {
		try {
			Thread.sleep(sleeptime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
