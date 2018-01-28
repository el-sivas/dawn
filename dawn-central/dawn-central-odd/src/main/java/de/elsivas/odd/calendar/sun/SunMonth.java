package de.elsivas.odd.calendar.sun;

public enum SunMonth {

	NADIR(30, "NR"),
	
	VINTRO(30, "VO"),

	LEVIGO(30, "LO"),

	PRINTEMPO(30, "PO"),

	GRANDIGI(31, "GI"),

	GRENO(30, "GO"),

	ZENITH(31, "ZH"),

	SOMERO(31, "SO"),

	RIKOLTO(30, "RO"),

	AUTUNO(30, "AO"),

	SUBIRO(31, "SO"),

	MALLUMO(30, "MO");

	private final int daysInMonth;

	private final String shortString;

	private SunMonth(final int daysInMonth, final String shortString) {
		this.daysInMonth = daysInMonth;
		this.shortString = shortString;
	}

	public String getShort() {
		return shortString;
	}

	public int getDaysInMonth() {
		return daysInMonth;
	}

}
