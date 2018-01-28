package de.elsivas.basic.ioc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import de.elsivas.basic.ioc.register.DawnRegisterdServiceUtils;

public class DawnContextInitializer {

	private static final Log LOG = LogFactory.getLog(DawnContextInitializer.class);

	private static DawnContextInitializer instance;

	private ConfigurableListableBeanFactory beanFactory;

	private ApplicationContext appCtx;

	private StaticApplicationContext staticCtx;

	private AnnotationConfigApplicationContext annotatedCtx;

	private DawnContextInitializer() {
		
	}

	public static BeanFactory getBeanFactory() {
		return instance.beanFactory;
	}

	public static void close() {
		((ConfigurableApplicationContext) instance.appCtx).close();
		((ConfigurableApplicationContext) instance.staticCtx).close();
		((ConfigurableApplicationContext) instance.annotatedCtx).close();
	}
	
	public static void init(final DawnContextProperties ctxProperties) {
		instance = new DawnContextInitializer();
		instance.init0(ctxProperties);		
	}
	
	@Deprecated
	/**
	 * use init(DawnContextProperties) instead
	 */
	public static void init(final String contextXml) {
		init(new DawnContextProperties() {
			
			@Override
			public String getContextXml() {
				return contextXml;
			}
		});
	}

	private void init0(final DawnContextProperties ctxProperties) {
		initContexts(ctxProperties.getContextXml());

		initBeanFactory();

		registerBeans();

		final EntityManagerFactory emf = createEmf(ctxProperties);

		final EntityManager em = emf.createEntityManager();
		
		beanFactory.registerSingleton("entityManager", em);

		annotatedCtx.refresh();
	}

	private EntityManagerFactory createEmf(DawnContextProperties ctxProperties) {
		final PersistenceUnitInfo info = archiverPersistenceUnitInfo(ctxProperties);
		final Map<String, Object> properties = properties();

		return new PersistenceProvider().createContainerEntityManagerFactory(info, properties);

	}

	private HashMap<String, Object> properties() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(PersistenceUnitProperties.JDBC_URL, "jdbc:mysql://uranus.fritz.box:3306/test");
		map.put(PersistenceUnitProperties.JDBC_USER,"test");
		map.put(PersistenceUnitProperties.JDBC_PASSWORD, "test");

		return map;
	}

	private static PersistenceUnitInfo archiverPersistenceUnitInfo(final DawnContextProperties ctxProperties) {
		return new PersistenceUnitInfo() {
			@Override
			public String getPersistenceUnitName() {
				return "ApplicationPersistenceUnit";
			}

			@Override
			public String getPersistenceProviderClassName() {
				return "org.eclipse.persistence.jpa.PersistenceProvider";
			}

			@Override
			public PersistenceUnitTransactionType getTransactionType() {
				return PersistenceUnitTransactionType.RESOURCE_LOCAL;
			}

			@Override
			public DataSource getJtaDataSource() {
				return null;
			}

			@Override
			public DataSource getNonJtaDataSource() {
				return null;
			}

			@Override
			public List<String> getMappingFileNames() {
				return ctxProperties.getManagedClassNames();
			}

			@Override
			public List<URL> getJarFileUrls() {
				try {
					return Collections.list(this.getClass().getClassLoader().getResources(""));
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}

			@Override
			public URL getPersistenceUnitRootUrl() {
				try {
					return new URL("file://localhost/home/sebastian/dev/");
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public List<String> getManagedClassNames() {
				return ctxProperties.getManagedClassNames();
			}

			@Override
			public boolean excludeUnlistedClasses() {
				return false;
			}

			@Override
			public SharedCacheMode getSharedCacheMode() {
				return null;
			}

			@Override
			public ValidationMode getValidationMode() {
				return null;
			}

			@Override
			public Properties getProperties() {
				return new Properties();
			}

			@Override
			public String getPersistenceXMLSchemaVersion() {
				return null;
			}

			@Override
			public ClassLoader getClassLoader() {
				return this.getClassLoader();
			}

			@Override
			public void addTransformer(ClassTransformer transformer) {

			}

			@Override
			public ClassLoader getNewTempClassLoader() {
				return null;
			}
		};
	}

	private void registerBeans() {
		final Map<String, BeanDefinition> registeredServices = DawnRegisterdServiceUtils.findServices();

		for (final String beanName : registeredServices.keySet()) {
			final BeanDefinition beanDefinition = registeredServices.get(beanName);
			staticCtx.getDefaultListableBeanFactory().registerBeanDefinition(beanName, beanDefinition);
			LOG.info("Register Bean: " + beanDefinition.getBeanClassName());
		}
	}

	private void initBeanFactory() {
		final DefaultListableBeanFactory defaultListableBeanFactory = staticCtx.getDefaultListableBeanFactory();
		beanFactory = defaultListableBeanFactory;
		defaultListableBeanFactory.registerSingleton("beanFactory", beanFactory);

	}

	private void initContexts(final String contextXml) {
		appCtx = new ClassPathXmlApplicationContext(contextXml);
		staticCtx = new StaticApplicationContext(appCtx);
		annotatedCtx = new AnnotationConfigApplicationContext(staticCtx.getDefaultListableBeanFactory());
	}
}
