package de.elsivas.gol.gui;

import java.awt.Component;

public interface CellContent {
	
	int getContentSize();
	
	boolean isContented();
	
	default void format(Component c) {
		// implement if needed
	}
}
