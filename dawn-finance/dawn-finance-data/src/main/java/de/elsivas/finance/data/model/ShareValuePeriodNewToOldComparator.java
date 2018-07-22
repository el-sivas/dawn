package de.elsivas.finance.data.model;

import java.util.Comparator;

public class ShareValuePeriodNewToOldComparator implements Comparator<ShareValuePeriod>{

	@Override
	public int compare(ShareValuePeriod arg0, ShareValuePeriod arg1) {
		return arg1.getDate().compareTo(arg0.getDate());
	}

}
