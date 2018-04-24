package de.elsivas.finance.model;

import java.math.BigDecimal;

import de.elsivas.finance.data.FinChartData;

public interface FinShareValue {
	
	FinChart getChart();
	
	BigDecimal getExpectedEarnings();
	
	BigDecimal getCurrentStockPrice();
	
	BigDecimal getBookValuePerShare();
	
	BigDecimal getRevenue();
	
	BigDecimal getCashflowPerShare();
	
	BigDecimal getEquity();
	
	BigDecimal getOverallCapital();

}
