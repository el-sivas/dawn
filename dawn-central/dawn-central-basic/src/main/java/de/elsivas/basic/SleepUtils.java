package de.elsivas.basic;

public class SleepUtils {
	
	public static void sleepFor(int sleeptimeInMs) {
		try {
			Thread.sleep(sleeptimeInMs);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
