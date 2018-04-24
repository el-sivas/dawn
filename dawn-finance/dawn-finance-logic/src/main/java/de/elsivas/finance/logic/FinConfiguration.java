package de.elsivas.finance.logic;

import java.util.List;

public interface FinConfiguration {
	
	List<String> getConfig();
	
	default String getConfigFileName() {
		return null;
	}

}
