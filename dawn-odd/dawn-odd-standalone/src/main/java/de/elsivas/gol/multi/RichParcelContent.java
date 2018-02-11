package de.elsivas.gol.multi;

import java.util.HashMap;
import java.util.Map;

import de.elsivas.gol.gui.CellContent;

public class RichParcelContent implements CellContent {
	
	private Map<Rich,Integer> map = new HashMap<>();
	
	public RichParcelContent(String hex) {
		
	}

	@Override
	public int getContentSize() {
		return 2;
	}

	@Override
	public boolean isContented() {
		// TODO Auto-generated method stub
		return false;
	}

}
