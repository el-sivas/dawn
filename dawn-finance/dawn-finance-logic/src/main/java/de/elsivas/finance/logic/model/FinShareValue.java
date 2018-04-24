package de.elsivas.finance.logic.model;

import java.math.BigDecimal;

public interface FinShareValue {

	BigDecimal getEquity();

	BigDecimal getOverallCapital();

	BigDecimal getCurrentStockPrice();

	BigDecimal getExpectedEarnings();

	BigDecimal getCashflowPerShare();

	BigDecimal getBookValuePerShare();

	BigDecimal getRevenue();

}
