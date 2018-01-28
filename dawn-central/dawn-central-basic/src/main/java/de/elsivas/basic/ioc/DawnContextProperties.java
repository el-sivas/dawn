package de.elsivas.basic.ioc;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface DawnContextProperties {
	
	String getContextXml();
	
	default List<String> getManagedClassNames() {
		return getManagedEntities().stream().map(e -> e.getName()).collect(Collectors.toList());
	}
	
	default Collection<Class> getManagedEntities() {
		return Collections.emptyList();
	}

}
