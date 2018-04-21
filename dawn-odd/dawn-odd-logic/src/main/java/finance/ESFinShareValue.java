package finance;

import java.math.BigDecimal;

import finance.io.ChartData;

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
