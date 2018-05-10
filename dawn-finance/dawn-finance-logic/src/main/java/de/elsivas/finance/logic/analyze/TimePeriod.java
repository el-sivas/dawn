package de.elsivas.finance.logic.analyze;

import de.elsivas.basic.EsRuntimeException;

public enum TimePeriod {

	WEEK,

	MONTH,

	QUARTAL_YEAR,

	THIRD_YEAR,

	HALF_YEAR,

	YEAR;

	public int getDays() {
		switch (this) {
		case WEEK:
			return 7;
		case MONTH:
			return 30;
		case QUARTAL_YEAR:
			return 90;
		case THIRD_YEAR:
			return 120;
		case HALF_YEAR:
			return 180;
		case YEAR:
			return 360;

		default:
			throw new EsRuntimeException("not supported: " + this);
		}
	}

}
