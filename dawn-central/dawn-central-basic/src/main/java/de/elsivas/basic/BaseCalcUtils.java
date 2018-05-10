package de.elsivas.basic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

// TODO nullsafe
public class BaseCalcUtils {
	public static MathContext MC = new MathContext(7, RoundingMode.HALF_UP);

	public static boolean isBigger(BigDecimal b1, BigDecimal b2) {
		return b1.compareTo(b2) > 0;
	}

	public static BigDecimal differenceAbs(BigDecimal b1, BigDecimal b2) {
		return sub(b1, b2).abs();
	}

	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		return b1.add(b2, MC);
	}

	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		return b1.subtract(b2, MC);
	}

	public static BigDecimal div(BigDecimal b1, double d1) {
		return div(b1, BigDecimal.valueOf(d1));
	}

	public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
		return b1.divide(b2, MC);
	}

	public static BigDecimal mult(BigDecimal b1, BigDecimal b2) {
		return b1.multiply(b2, MC);
	}

	public static BigDecimal mult(BigDecimal b, double d) {
		return mult(b, BigDecimal.valueOf(d));
	}
}
