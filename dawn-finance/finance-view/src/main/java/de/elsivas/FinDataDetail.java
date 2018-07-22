package de.elsivas;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.elsivas.OutputControllerUtils.FinDataDetailImpl;

public interface FinDataDetail {
	
	BigDecimal getWma4to38();
	
	BigDecimal getWma13to38();
	
	String getSignal();

	LocalDate getDate();

	FinDataDetailImpl getNext();
	
	int getDaysAgo();

	BigDecimal getWma38to200();

}
