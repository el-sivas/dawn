package de.elsivas.webapp.base;

public class GuiMessage implements Comparable<GuiMessage> {

	private long created = System.currentTimeMillis();

	private long createdNanos = System.nanoTime();

	private Severity severity;

	private String message;

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public enum Severity {
		ERROR,

		WARN,

		INFO
	}

	@Override
	public int compareTo(GuiMessage o) {
		final int s = Integer.valueOf(severity.ordinal()).compareTo(Integer.valueOf(o.severity.ordinal()));
		if (s != 0) {
			return s;
		}

		final int compareTo = Long.valueOf(created).compareTo(Long.valueOf(o.created));
		if (compareTo != 0) {
			return compareTo;
		}
		return Long.valueOf(createdNanos).compareTo(Long.valueOf(o.createdNanos));
	}
}
