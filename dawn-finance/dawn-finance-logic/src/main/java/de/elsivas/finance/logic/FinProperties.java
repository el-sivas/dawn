package de.elsivas.finance.logic;

import java.util.Collection;

import de.elsivas.finance.logic.portals.Portal;

public interface FinProperties {

	String ARG_DOWNLOADS = "d";
	String ARG_PORTAL = "p";
	String ARG_WORKDIR = "w";
	String ARG_VALUE = "v";

	String getWorkdir();

	Portal getPortal();

	Collection<String> getDownloads();

}
