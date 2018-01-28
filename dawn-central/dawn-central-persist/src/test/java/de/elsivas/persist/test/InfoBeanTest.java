package de.elsivas.persist.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import de.elsivas.persist.Dao;
import de.elsivas.persist.common.info.InfoBean;
import de.elsivas.persist.common.info.InfoDaoImpl;

public class InfoBeanTest {
	
	private ApplicationContext context = new AnnotationConfigApplicationContext();
	
	private Dao<InfoBean> infoDao;
	
	@Before
	public void init() {
		
		
		infoDao = new InfoDaoImpl();
	}
	
	@Test
	public void test() {
		final InfoBean info = new InfoBean();
		info.setMessage("Test!");
		infoDao.save(info);
		
	}

}
