package de.elsivas.finance.logic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import de.elsivas.finance.model.FinShareValue;


public class FinFundamentalCalcUtils {

	private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(7, RoundingMode.HALF_UP);

	/**
	 * Eigenkaptialquote
	 */
	public BigDecimal calcEKQ(FinShareValue value) {
		return value.getEquity().divide(value.getOverallCapital(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Gesamtkapitalrendite
	 */
	public BigDecimal calcGKR(FinShareValue value) {
		return null;
	}

	/**
	 * Kurs-Gewinn-Verhältnis
	 */
	public BigDecimal calcKGV(FinShareValue value) {
		return value.getCurrentStockPrice().divide(value.getExpectedEarnings(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Kurs-Cashflow-Verhältnis
	 */
	public BigDecimal calcKCV(FinShareValue value) {
		return value.getCurrentStockPrice().divide(value.getCashflowPerShare(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Kurs-Buchwert-Verhältnis
	 */
	public BigDecimal calcKBV(FinShareValue value) {
		return value.getCurrentStockPrice().divide(value.getBookValuePerShare(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Kurs-Umsatz-Verhältni
	 */
	public BigDecimal calcKUV(FinShareValue value) {
		return value.getCurrentStockPrice().divide(value.getRevenue(), DEFAULT_MATH_CONTEXT);
	}

}
