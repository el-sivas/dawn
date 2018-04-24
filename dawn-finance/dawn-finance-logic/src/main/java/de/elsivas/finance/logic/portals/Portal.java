package de.elsivas.finance.logic.portals;

public enum Portal {
	
	ONVISTA;
	
	public static Portal of(String portal) {
		return valueOf(portal.toUpperCase());
	}

}
