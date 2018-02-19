package de.elsivas.odd.standalone.taskmanager;

public class TMTaskProperties {
	
	private String key;
	
	private String value;
	
	public static TMTaskProperties create(final String key, final String value) {
		final TMTaskProperties t = new TMTaskProperties();
		t.setKey(key);
		t.setValue(value);
		return t;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
