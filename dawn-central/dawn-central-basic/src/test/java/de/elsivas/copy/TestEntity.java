package de.elsivas.copy;

import java.math.BigDecimal;

import org.apache.commons.lang3.BooleanUtils;

public class TestEntity {

	private String stringValue;

	private int intValue;

	private BigDecimal bigDecimalValue;

	private Boolean booleanValue;

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public BigDecimal getBigDecimalValue() {
		return bigDecimalValue;
	}

	public void setBigDecimalValue(BigDecimal bigDecimalValue) {
		this.bigDecimalValue = bigDecimalValue;
	}

	public boolean isBooleanValue() {
		return BooleanUtils.isTrue(booleanValue);
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

}
