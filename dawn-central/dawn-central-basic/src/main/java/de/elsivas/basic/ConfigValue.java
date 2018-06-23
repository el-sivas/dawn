package de.elsivas.basic;

public class ConfigValue {

	private final String value;

	private ConfigValue(String value) {
		this.value = value;
	}

	public static ConfigValue create(String value) {
		return new ConfigValue(value);
	}

	public String asString() {
		return value;
	}

	public Long asLong() {
		return Long.valueOf(value);
	}

	@Override
	public String toString() {
		return asString();
	}
}
