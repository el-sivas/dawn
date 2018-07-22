package de.elsivas.finance.logic;

import de.elsivas.finance.data.model.Wertpapier;

public interface FinDownloadLinkBuilder {

	String buildDownloadLink(Wertpapier wp);

	String buildDownloadLink(String isin);

}
