package de.elsivas.odd.standalone;

import java.util.HashMap;
import java.util.Map;

public class ValueObserver implements Spezial{

	private static Thread t;

	private static Map<String, Object> map = new HashMap<>();

	public static void observe(String s, Object o) {
		if (t == null) {
			init();
		}
		map.put(s, o);
	}

	private static void log(Object o) {
		System.out.println(o);
	}

	private static void init() {
		final Runnable r = new Runnable() {

			@Override
			public void run() {
				while (true) {
					waitShort();
					for (String key : map.keySet()) {
						Object o = map.get(key);
						log(key + ":" + o);
					}
				}
			}

			private void waitShort() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};
		t = new Thread(r);
		t.start();
	}
}
