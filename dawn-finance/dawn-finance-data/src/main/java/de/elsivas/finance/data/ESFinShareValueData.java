package de.elsivas.finance.data;

import java.math.BigDecimal;

import de.elsivas.finance.model.Chart;
import de.elsivas.finance.model.ESFinShareValue;

public class ESFinShareValueData implements ESFinShareValue {

	private Chart chart;
	private BigDecimal expectedEarnings;
	private BigDecimal currentStockPrice;
	private BigDecimal umsatz;
	private BigDecimal cashflowPerShare;
	private BigDecimal eigenkapital;
	private BigDecimal gesamtkapital;
	private BigDecimal bookValuePerShare;

	@Override
	public Chart getChart() {
		return chart;
	}

	@Override
	public BigDecimal getExpectedEarnings() {
		return expectedEarnings;
	}

	@Override
	public BigDecimal getCurrentStockPrice() {
		return currentStockPrice;
	}

	@Override
	public BigDecimal getBookValuePerShare() {
		return bookValuePerShare;
	}

	@Override
	public BigDecimal getRevenue() {
		return umsatz;
	}

	@Override
	public BigDecimal getCashflowPerShare() {
		return cashflowPerShare;
	}

	@Override
	public BigDecimal getEquity() {
		return eigenkapital;
	}

	@Override
	public BigDecimal getOverallCapital() {
		return gesamtkapital;
	}

}
