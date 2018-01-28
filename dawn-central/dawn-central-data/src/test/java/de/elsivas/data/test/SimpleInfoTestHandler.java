package de.elsivas.data.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;

import de.elsivas.basic.ioc.DawnContextInitializer;
import de.elsivas.basic.ioc.DawnContextProperties;
import de.elsivas.data.info.InfoBean;
import de.elsivas.data.info.InfoDaoImpl;

public class SimpleInfoTestHandler {

	public static void main(String[] args) {

		DawnContextInitializer.init(ctxProperties());

		final BeanFactory beanFactory = DawnContextInitializer.getBeanFactory();

		final InfoDaoImpl dao = beanFactory.getBean(InfoDaoImpl.class);

		final InfoBean infoBean = new InfoBean();
		infoBean.setMessage("Test!");

		dao.save(infoBean);

		DawnContextInitializer.close();
	}

	private static DawnContextProperties ctxProperties() {
		return new DawnContextProperties() {
			

			@Override
			public String getContextXml() {
				return "context.xml";
			}

			@Override
			public Collection<Class> getManagedEntities() {
				final List<Class> entities = new ArrayList<>();
				entities.add(InfoBean.class);
				return entities;
			}
		};
	}

}
