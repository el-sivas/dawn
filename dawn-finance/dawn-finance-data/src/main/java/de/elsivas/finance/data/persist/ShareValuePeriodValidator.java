package de.elsivas.finance.data.persist;

import de.elsivas.finance.data.model.ShareValuePeriod;

public class ShareValuePeriodValidator {

	public static boolean isValid(ShareValuePeriod svp) {
		if (svp.getDate() == null) {
			return false;
		}
		return true;
	}

}
