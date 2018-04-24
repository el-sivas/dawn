package de.elsivas.finance;

import java.util.List;

public interface FinConfigurable {
	
	List<String> getConfig();
	
	default String getConfigFileName() {
		return null;
	}


}
