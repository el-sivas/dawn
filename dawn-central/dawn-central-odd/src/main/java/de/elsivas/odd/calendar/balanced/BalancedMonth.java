package de.elsivas.odd.calendar.balanced;

public enum BalancedMonth {
	
	JANUARY(31),
	FEBRUARY(30),
	MARCH(30,true),
	APRIL(30),
	MAY(31),
	JUNE(30),
	JULY(31),
	AUGUST(31),
	SEPTEMBER(30),
	OCTOBER(31),
	NOVEMBER(30),
	DECEMBER(30);
	
	private final int daysOfMonth;
	private final boolean schaltMonat;

	private BalancedMonth(final int daysOfMonth) {
		this(daysOfMonth,false);
	}
	
	private BalancedMonth(final int daysOfMonth, final boolean schaltMonat) {
		this.daysOfMonth = daysOfMonth;
		this.schaltMonat = schaltMonat;
	}


	public int getDaysOfMonth() {
		return daysOfMonth;
	}

	public boolean isSchaltMonat() {
		return schaltMonat;
	}
}
