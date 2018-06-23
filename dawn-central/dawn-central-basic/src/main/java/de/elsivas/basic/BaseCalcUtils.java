package de.elsivas.basic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO nullsafe
public class BaseCalcUtils {
	public static MathContext MC = new MathContext(7, RoundingMode.HALF_UP);

	public static boolean isBigger(BigDecimal b1, BigDecimal b2) {
		final List<BigDecimal> calcValues = notNullCalcValues(b1, b2);
		return calcValues.get(0).compareTo(calcValues.get(1)) > 0;
	}

	private static List<BigDecimal> notNullCalcValues(BigDecimal b1, BigDecimal b2) {
		final List<BigDecimal> c = new ArrayList<>();
		c.add(b1 != null ? b1 : BigDecimal.ZERO);
		c.add(b2 != null ? b2 : BigDecimal.ZERO);
		return c;
	}

	public static BigDecimal differenceAbs(BigDecimal b1, BigDecimal b2) {
		return sub(b1, b2).abs();
	}

	public static BigDecimal addAll(Collection<BigDecimal> b) {
		return addAll(b.toArray(new BigDecimal[0]));
	}

	public static BigDecimal addAll(BigDecimal... b) {
		BigDecimal result = BigDecimal.ZERO;
		for (final BigDecimal add : b) {
			result = add(result, add);
		}
		return result;
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
		try {
			return b1.divide(b2, MC);
		} catch (final ArithmeticException e) {
			throw new EsRuntimeException("error divide:" + b1 + ", " + b2, e);
		}
	}

	public static BigDecimal mult(BigDecimal b1, BigDecimal b2) {
		return b1.multiply(b2, MC);
	}

	public static boolean isSimilar(BigDecimal b1, BigDecimal b2, double tolerance) {
		final BigDecimal upperBoarder = b1.multiply(BigDecimal.valueOf(1 + tolerance));
		if (isBigger(b2, upperBoarder)) {
			return false;
		}
		final BigDecimal lowerBoarder = b1.multiply(BigDecimal.valueOf(1 - tolerance));
		if (isBigger(lowerBoarder, b2)) {
			return false;
		}
		return true;
	}

	public static BigDecimal mult(BigDecimal b, double d) {
		return mult(b, BigDecimal.valueOf(d));
	}

	public static int addAll(int... ints) {
		int i = 0;
		for (int j = 0; j < ints.length; j++) {
			i = i + ints[j];
		}
		return i;
	}
}
