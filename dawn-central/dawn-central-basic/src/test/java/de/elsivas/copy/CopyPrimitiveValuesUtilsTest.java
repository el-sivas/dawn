package de.elsivas.copy;

import java.math.BigDecimal;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.elsivas.basic.CopyPrimitiveValuesUtils;
import de.elsivas.basic.EsLogicException;

public class CopyPrimitiveValuesUtilsTest {

	private final TestEntity source = new TestEntity();

	@Before
	public void init() {
		Configurator.setLevel("de.elsivas", Level.DEBUG);
		source.setBigDecimalValue(BigDecimal.valueOf(7));
		source.setIntValue(6);
		source.setBooleanValue(true);
		source.setStringValue("test");
	}

	@Test
	public void test() throws EsLogicException {
		CopyPrimitiveValuesUtils.copy(source, new TestEntity());
	}

	@Test
	public void test2() throws EsLogicException {
		CopyPrimitiveValuesUtils.copy(source, new TestEntity2());
	}

	@Test
	public void test3() throws EsLogicException {
		CopyPrimitiveValuesUtils.copy(source, new TestEntity(), true);
	}

	@Test
	public void test4() {
		Throwable t = null;
		try {
			CopyPrimitiveValuesUtils.copy(source, new TestEntity2(), true);
		} catch (final EsLogicException e) {
			t = e;
		}
		Assert.assertNotNull(t);
	}

}
