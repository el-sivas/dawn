
import java.sql.SQLException;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import de.elsivas.basic.ioc.DawnContextInitializer;
import de.elsivas.basic.ioc.register.DawnRegisterdServiceUtils;

public class ContextTest {
	
	@Test
	public void test3() {
		DawnContextInitializer.init("test-context.xml");
		
		final BeanFactory beanFactory = DawnContextInitializer.getBeanFactory();
		
		
	}
	
	@Test
	public void test2() {
		final Map<String, BeanDefinition> findServices = DawnRegisterdServiceUtils.findServices();
	}

	@Test
	public void test() {
		try {
			testInternal();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private void testInternal() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		final Enumeration<Driver> drivers = DriverManager.getDrivers();
//		DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test-context.xml");
		StaticApplicationContext s = new StaticApplicationContext(context);
		final DefaultListableBeanFactory defaultListableBeanFactory = s.getDefaultListableBeanFactory();
		AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(defaultListableBeanFactory);
//		a.register(TestServiceImpl.class);
//		a.register(Test2ServiceImpl.class);
//		BeanDefinition b = null;
		
		final Map<String, BeanDefinition> findServices = DawnRegisterdServiceUtils.findServices();
		for (String string : findServices.keySet()) {
			defaultListableBeanFactory.registerBeanDefinition(string, findServices.get(string));
		}
		
		a.refresh();


		 context.close();
		s.close();
		a.close();
		
		
		
		

//		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestService");
//		final EntityManager em = emf.createEntityManager();
//
//
//
//
//		
//		em.close();
//		emf.close();
	}

}
