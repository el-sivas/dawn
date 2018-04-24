package de.elsivas.finance.logic;

import java.util.Arrays;
import java.util.List;

import de.elsivas.basic.EsRuntimeException;

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

	public static Wertpapier of(String wertpapierSearchFor) {
		final List<Wertpapier> asList = Arrays.asList(Wertpapier.values());
		for (Wertpapier wertpapier : asList) {
			if (wertpapier.getWkn().equals(wertpapierSearchFor)) {
				return wertpapier;
			}
			if (wertpapier.getIsin().equals(wertpapierSearchFor)) {
				return wertpapier;
			}
			if (wertpapier.toString().equals(wertpapierSearchFor.toUpperCase())) {
				return wertpapier;
			}
		}
		throw new EsRuntimeException("not supported: " + wertpapierSearchFor);

	}

}
