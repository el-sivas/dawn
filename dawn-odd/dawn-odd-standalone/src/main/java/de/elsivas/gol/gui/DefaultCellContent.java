package de.elsivas.gol.gui;

import java.awt.Color;
import java.awt.Component;

import org.apache.commons.lang3.StringUtils;

public class DefaultCellContent implements CellContent {
	
	private String value;
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean isContented() {
		return !StringUtils.isEmpty(value);
	}

	@Override
	public int getContentSize() {
		return value == null ? 0 : value.length();
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public void format(Component c) {
		c.setBackground(isContented() ? Color.green : Color.WHITE);
	}

	@Override
	public String value() {
		return toString();
	}

}
