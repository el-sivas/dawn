package de.elsivas.odd.logic;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

@Service
public class OddServiceImpl implements OddService {

	private static final Log LOG = LogFactory.getLog(OddServiceImpl.class);

	@Resource
	private BeanFactory beanFactory;

	@Override
	public void doNothing() {
		if (beanFactory == null) {
			throw new RuntimeException("Dependency injection failed");
		}

		final double sleepSeconds = Math.random() * 5;
		LOG.warn("I do nothing for: " + sleepSeconds + " seconds");
		try {
			Thread.sleep((long) (sleepSeconds * 1000));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		LOG.warn("I do nothing done!");
	}
}
