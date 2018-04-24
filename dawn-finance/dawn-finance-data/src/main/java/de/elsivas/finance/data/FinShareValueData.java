package de.elsivas.finance.data;

import java.math.BigDecimal;

import de.elsivas.finance.model.FinChart;
import de.elsivas.finance.model.FinShareValue;

public class FinShareValueData implements FinShareValue {

	private FinChart chart;
	private BigDecimal expectedEarnings;
	private BigDecimal currentStockPrice;
	private BigDecimal umsatz;
	private BigDecimal cashflowPerShare;
	private BigDecimal eigenkapital;
	private BigDecimal gesamtkapital;
	private BigDecimal bookValuePerShare;

	@Override
	public FinChart getChart() {
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
