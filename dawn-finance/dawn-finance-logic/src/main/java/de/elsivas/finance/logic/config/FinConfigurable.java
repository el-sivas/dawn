package de.elsivas.finance.logic.config;

import java.util.List;

import de.elsivas.finance.logic.FinDownloader;

public interface FinConfigurable {
	
	List<String> getConfig();
	
	default String getConfigFileName() {
		return null;
	}


}
