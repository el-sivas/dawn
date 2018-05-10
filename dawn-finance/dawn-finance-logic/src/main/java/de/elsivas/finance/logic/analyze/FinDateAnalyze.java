package de.elsivas.finance.logic.analyze;

import java.math.BigDecimal;

public interface FinDateAnalyze {
	
	BigDecimal getPrice();
	
	BigDecimal getSMA(int days);

	BigDecimal getWMA(int days);

}
