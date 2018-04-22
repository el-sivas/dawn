package de.elsivas.finance.model;

import java.math.BigDecimal;

import de.elsivas.finance.data.ChartData;

public interface ESFinShareValue {
	
	ChartData getChartData();
	
	BigDecimal expectedEarnings();
	
	BigDecimal currentStockPrice();
	
	BigDecimal bookValuePerShare();
	
	BigDecimal umsatz();
	
	BigDecimal cashflowProAktie();
	
	BigDecimal eigenkapital();
	
	BigDecimal gesamtkapital();

}
