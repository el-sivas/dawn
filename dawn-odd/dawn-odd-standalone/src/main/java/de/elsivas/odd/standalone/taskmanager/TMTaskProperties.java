package de.elsivas.odd.standalone.taskmanager;

public class TMTaskProperties {
	
	private String key;
	
	private String value;
	
	public static TMTaskProperties create(String key, String value) {
		TMTaskProperties t = new TMTaskProperties();
		t.setKey(key);
		t.setValue(value);
		return t;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
