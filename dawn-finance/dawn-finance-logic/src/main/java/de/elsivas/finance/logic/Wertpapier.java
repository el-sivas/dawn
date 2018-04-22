package de.elsivas.finance.logic;

public enum Wertpapier {

	DAX("846900", "DE0008469008");

	private String wkn;

	private String isin;

	Wertpapier(String wkn, String isin) {
		this.wkn = wkn;
		this.isin = isin;
	}

	public String getWkn() {
		return wkn;
	}

	public String getIsin() {
		return isin;
	}

}
