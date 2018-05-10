package de.elsivas.finance.logic.analyze.indicators;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.analyze.Tendence;
import de.elsivas.finance.logic.analyze.TimePeriod;

public interface FinValueIndicators {

	Date getDate();
	
	Wertpapier getWertpapier();
	
	BigDecimal getPrice();

	BigDecimal getBollingerEntry();

	BigDecimal getBollingerAvg();

	BigDecimal getBollingerExit();

	BigDecimal getWMA38();

	BigDecimal getWMA200();
	
	BigDecimal getMACD();
	
	BigDecimal getMACDsignal();
	
	BigDecimal getRSI();

	BigDecimal getSSTOC();

	BigDecimal getSSTOCsignal();
	
	Map<TimePeriod,BigDecimal> getMovement();
}
