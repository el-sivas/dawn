package de.elsivas.finance.logic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import de.elsivas.finance.model.ESFinShareValue;


public class ESFinanceFundamentalCalcUtils {

	private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(7, RoundingMode.HALF_UP);

	/**
	 * Eigenkaptialquote
	 */
	public BigDecimal calcEKQ(ESFinShareValue value) {
		return value.eigenkapital().divide(value.gesamtkapital(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Gesamtkapitalrendite
	 */
	public BigDecimal calcGKR(ESFinShareValue value) {
		return null;
	}

	/**
	 * Kurs-Gewinn-Verhältnis
	 */
	public BigDecimal calcKGV(ESFinShareValue value) {
		return value.currentStockPrice().divide(value.expectedEarnings(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Kurs-Cashflow-Verhältnis
	 */
	public BigDecimal calcKCV(ESFinShareValue value) {
		return value.currentStockPrice().divide(value.cashflowProAktie(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Kurs-Buchwert-Verhältnis
	 */
	public BigDecimal calcKBV(ESFinShareValue value) {
		return value.currentStockPrice().divide(value.bookValuePerShare(), DEFAULT_MATH_CONTEXT);
	}

	/**
	 * Kurs-Umsatz-Verhältni
	 */
	public BigDecimal calcKUV(ESFinShareValue value) {
		return value.currentStockPrice().divide(value.umsatz(), DEFAULT_MATH_CONTEXT);
	}

}
