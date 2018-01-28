package de.elsivas.persist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.BeanFactory;

public abstract class AbstractDaoImpl<E extends EntityBean> {

	private static final String SET_VALID_UNTIL = "setValidUntil";

	@PersistenceUnit
	private EntityManager entityManager;
	
	@Resource
	private BeanFactory beanFactory;
	
	@PostConstruct
	public void init() {
		entityManager = (EntityManager) beanFactory.getBean("entityManager");
	}
	
	protected abstract Class<E> beanClass();

	public void save(E entity) {
		if (entity.getId() == null) {
			entityManager.persist(entity);
			return;
		}
		entityManager.merge(entity);
	}

	public void delete(E entity) {
		try {
			setValidUntil(entity);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new PersistException("Delete failed: " + entity.toStringTechnical(), e);
		}
		save(entity);
	}

	public E loadById(final long id) {
		return entityManager.find(beanClass(), id);
	}

	private void setValidUntil(E entity) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		final Method setValidUntil = entity.getClass().getMethod(SET_VALID_UNTIL, LocalDateTime.class);
		setValidUntil.setAccessible(true);
		setValidUntil.invoke(entity, LocalDateTime.now());
		setValidUntil.setAccessible(false);

	}
}
