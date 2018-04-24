package de.elsivas.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class ESClassesUtils {

	public static Collection<Class> findSubclassesOf(Class parent) throws ClassNotFoundException {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(parent));

		final String name = parent.getName();
		final String[] split = name.split("\\.");
		final String prefix = split[0];
		
		final List<Class> classes = new ArrayList<>();
		// scan in org.example.package
		Set<BeanDefinition> components = provider.findCandidateComponents(prefix);
		for (BeanDefinition component : components) {
			Class clazz = Class.forName(component.getBeanClassName());
			System.out.println(clazz);
			classes.add(clazz);
		}
		return classes;
	}
}
