package de.elsivas.basic.ioc.register;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class DawnRegisterdServiceUtils {	

	public static Map<String, BeanDefinition> findServices() {
		final SimpleBeanDefinitionRegistry reg = new SimpleBeanDefinitionRegistry();
		final ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(reg);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Repository.class));
		
		scanner.setIncludeAnnotationConfig(false);

		scanner.scan("de.elsivas");

		final String[] beanDefinitionNames = reg.getBeanDefinitionNames();

		final Map<String, BeanDefinition> map = new HashMap<>();
		
		
		
		for (String string : beanDefinitionNames) {
			final BeanDefinition beanDefinition = reg.getBeanDefinition(string);
			map.put(string, beanDefinition);
		}

		return map;

	}

}
