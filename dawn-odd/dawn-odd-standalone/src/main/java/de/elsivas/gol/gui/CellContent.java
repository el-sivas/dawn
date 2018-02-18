package de.elsivas.gol.gui;

import java.awt.Component;

public interface CellContent {
	
	int getContentSize();
	
	boolean isContented();
	
	String value();
	
	default void format(Component c) {
		// implement if needed
	}
}
